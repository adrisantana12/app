package com.virtualpharmacy.dao;

import com.virtualpharmacy.model.UsuariosInactivos;
import com.virtualpharmacy.model.Usuario;
import com.virtualpharmacy.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuariosInactivosDAO {
    private Connection connection;
    private UsuarioDAO usuarioDAO;

    public UsuariosInactivosDAO() {
        try {
            connection = DatabaseConnection.getConnection();
            usuarioDAO = new UsuarioDAO();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<UsuariosInactivos> listarTodos() throws SQLException {
        List<UsuariosInactivos> inactivos = new ArrayList<>();
        String sql = "SELECT ui.*, CONCAT(u.nombre, ' ', u.apellido) as nombre_completo " +
                "FROM usuarios_inactivos ui " +
                "INNER JOIN usuarios u ON ui.id_usuario = u.id " +
                "ORDER BY ui.id DESC";

        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                UsuariosInactivos inactivo = mapearResultSet(rs);
                inactivos.add(inactivo);
            }
        }
        return inactivos;
    }

    public UsuariosInactivos obtenerPorId(int id) throws SQLException {
        String sql = "SELECT ui.*, CONCAT(u.nombre, ' ', u.apellido) as nombre_completo " +
                "FROM usuarios_inactivos ui " +
                "INNER JOIN usuarios u ON ui.id_usuario = u.id " +
                "WHERE ui.id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearResultSet(rs);
                }
            }
        }
        return null;
    }

    public List<UsuariosInactivos> buscarPorUsuario(int idUsuario) throws SQLException {
        List<UsuariosInactivos> inactivos = new ArrayList<>();
        String sql = "SELECT ui.*, CONCAT(u.nombre, ' ', u.apellido) as nombre_completo " +
                "FROM usuarios_inactivos ui " +
                "INNER JOIN usuarios u ON ui.id_usuario = u.id " +
                "WHERE ui.id_usuario = ? " +
                "ORDER BY ui.id DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    inactivos.add(mapearResultSet(rs));
                }
            }
        }
        return inactivos;
    }

    public void crear(UsuariosInactivos inactivo) throws SQLException {
        String sql = "INSERT INTO usuarios_inactivos (id_usuario, razon, descripcion) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, inactivo.getIdUsuario());
            stmt.setString(2, inactivo.getRazon());
            stmt.setString(3, inactivo.getDescripcion());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    inactivo.setId(generatedKeys.getInt(1));
                }
            }

            // Cargar datos completos del usuario
            Usuario usuario = usuarioDAO.obtenerPorId(inactivo.getIdUsuario());
            if (usuario != null) {
                inactivo.setUsuario(usuario);
                inactivo.setNombreCompletoUsuario(usuario.getNombre() + " " + usuario.getApellido());
            }
        }
    }

    public void actualizar(UsuariosInactivos inactivo) throws SQLException {
        String sql = "UPDATE usuarios_inactivos SET id_usuario = ?, razon = ?, descripcion = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, inactivo.getIdUsuario());
            stmt.setString(2, inactivo.getRazon());
            stmt.setString(3, inactivo.getDescripcion());
            stmt.setInt(4, inactivo.getId());
            stmt.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM usuarios_inactivos WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private UsuariosInactivos mapearResultSet(ResultSet rs) throws SQLException {
        UsuariosInactivos inactivo = new UsuariosInactivos();
        inactivo.setId(rs.getInt("id"));
        inactivo.setIdUsuario(rs.getInt("id_usuario"));
        inactivo.setRazon(rs.getString("razon"));
        inactivo.setDescripcion(rs.getString("descripcion"));
        inactivo.setNombreCompletoUsuario(rs.getString("nombre_completo"));

        // Cargar datos completos del usuario
        Usuario usuario = usuarioDAO.obtenerPorId(inactivo.getIdUsuario());
        if (usuario != null) {
            inactivo.setUsuario(usuario);
        }

        return inactivo;
    }
}
