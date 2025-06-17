package com.virtualpharmacy.dao;

import com.virtualpharmacy.model.Credenciales;
import com.virtualpharmacy.util.DatabaseConnection;
import com.virtualpharmacy.util.SecurityUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CredencialesDAO {
    private Connection connection;

    public CredencialesDAO() {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Credenciales> listarTodos() throws SQLException {
        List<Credenciales> credenciales = new ArrayList<>();
        String sql = "SELECT c.*, CONCAT(u.nombre, ' ', u.apellido) as nombre_completo " +
                "FROM credenciales c " +
                "INNER JOIN usuarios u ON c.id_usuario = u.id";

        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Credenciales credencial = new Credenciales();
                credencial.setId(rs.getInt("id"));
                credencial.setIdUsuario(rs.getInt("id_usuario"));
                credencial.setUsuario(rs.getString("usuario"));
                credencial.setContrasenia(rs.getString("contrasenia"));
                credencial.setNombreCompletoUsuario(rs.getString("nombre_completo"));
                credenciales.add(credencial);
            }
        }
        return credenciales;
    }

    public Credenciales obtenerPorId(int id) throws SQLException {
        String sql = "SELECT c.*, CONCAT(u.nombre, ' ', u.apellido) as nombre_completo " +
                "FROM credenciales c " +
                "INNER JOIN usuarios u ON c.id_usuario = u.id " +
                "WHERE c.id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Credenciales credencial = new Credenciales();
                    credencial.setId(rs.getInt("id"));
                    credencial.setIdUsuario(rs.getInt("id_usuario"));
                    credencial.setUsuario(rs.getString("usuario"));
                    credencial.setContrasenia(rs.getString("contrasenia"));
                    credencial.setNombreCompletoUsuario(rs.getString("nombre_completo"));
                    return credencial;
                }
            }
        }
        return null;
    }

    public void crear(Credenciales credencial) throws SQLException {
        String sql = "INSERT INTO credenciales (id_usuario, contrasenia, usuario) VALUES (?, ?, ?)";

        // Hash de la contraseña antes de guardar
        String hashedPassword = SecurityUtil.hashPassword(credencial.getContrasenia());

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, credencial.getIdUsuario());
            stmt.setString(2, hashedPassword);
            stmt.setString(3, credencial.getUsuario());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    credencial.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public void actualizar(Credenciales credencial) throws SQLException {
        String sql = "UPDATE credenciales SET id_usuario = ?, contrasenia = ?, usuario = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, credencial.getIdUsuario());
            stmt.setString(2, credencial.getContrasenia());
            stmt.setString(3, credencial.getUsuario());
            stmt.setInt(4, credencial.getId());
            stmt.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM credenciales WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Credenciales validarCredenciales(String usuario, String contrasenia) throws SQLException {
        String sql = "SELECT c.*, CONCAT(u.nombre, ' ', u.apellido) as nombre_completo " +
                "FROM credenciales c " +
                "INNER JOIN usuarios u ON c.id_usuario = u.id " +
                "WHERE c.usuario = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Credenciales credencial = new Credenciales();
                    credencial.setId(rs.getInt("id"));
                    credencial.setIdUsuario(rs.getInt("id_usuario"));
                    credencial.setUsuario(rs.getString("usuario"));
                    credencial.setContrasenia(rs.getString("contrasenia"));
                    credencial.setNombreCompletoUsuario(rs.getString("nombre_completo"));
                    return credencial;
                }
            }
        }
        return null;
    }
}
