package com.virtualpharmacy.util;

import com.virtualpharmacy.model.Auditoria;
import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuditoriaUtil {
    private static final Logger LOGGER = Logger.getLogger(AuditoriaUtil.class.getName());

    public static void registrarAuditoria(HttpServletRequest request, String tabla, String accion,
            int idRegistro, String detalles, int idUsuario) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            registrarAuditoriaConConexion(conn, tabla, accion, idRegistro, detalles, idUsuario,
                    obtenerIpCliente(request));
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al registrar auditoría", e);
        }
    }

    public static void registrarCreacion(Connection conn, String tabla, int idRegistro,
            String usuario, String descripcion) throws SQLException {
        registrarAuditoriaConConexion(conn, tabla, "CREACION", idRegistro, descripcion,
                obtenerIdUsuario(usuario), "SISTEMA");
    }

    public static void registrarModificacion(Connection conn, String tabla, int idRegistro,
            String usuario, String descripcion) throws SQLException {
        registrarAuditoriaConConexion(conn, tabla, "MODIFICACION", idRegistro, descripcion,
                obtenerIdUsuario(usuario), "SISTEMA");
    }

    public static void registrarEliminacion(Connection conn, String tabla, int idRegistro,
            String usuario, String descripcion) throws SQLException {
        registrarAuditoriaConConexion(conn, tabla, "ELIMINACION", idRegistro, descripcion,
                obtenerIdUsuario(usuario), "SISTEMA");
    }

    private static void registrarAuditoriaConConexion(Connection conn, String tabla, String accion,
            int idRegistro, String detalles, int idUsuario,
            String ipAddress) throws SQLException {
        String sql = "INSERT INTO auditoria (tabla, accion, id_registro, detalles, id_usuario, fecha_hora, ip_address) "
                +
                "VALUES (?, ?, ?, ?, ?, NOW(), ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tabla);
            stmt.setString(2, accion);
            stmt.setInt(3, idRegistro);
            stmt.setString(4, detalles);
            stmt.setInt(5, idUsuario);
            stmt.setString(6, ipAddress);

            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al registrar auditoría: " + e.getMessage(), e);
            throw e;
        }
    }

    private static String obtenerIpCliente(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }

    private static int obtenerIdUsuario(String usuario) throws SQLException {
        if (usuario == null || usuario.trim().isEmpty()) {
            return 0; // Usuario del sistema
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT id FROM usuarios WHERE usuario = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, usuario);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("id");
                    }
                    return 0; // Usuario no encontrado
                }
            }
        }
    }
}
