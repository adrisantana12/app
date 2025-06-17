package com.virtualpharmacy.dao;

import com.virtualpharmacy.model.Rol;
import com.virtualpharmacy.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RolDAO {

    public List<Rol> listarTodos() throws SQLException {
        List<Rol> roles = new ArrayList<>();
        String sql = "SELECT * FROM roles ORDER BY nivel_acceso";

        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                roles.add(extraerRolDeResultSet(rs));
            }
        }
        return roles;
    }

    public Rol obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM roles WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extraerRolDeResultSet(rs);
                }
            }
        }
        return null;
    }

    public void crear(Rol rol) throws SQLException {
        String sql = "INSERT INTO roles (nombre, descripcion, nivel_acceso, esta_activo) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            establecerParametrosRol(stmt, rol);
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    rol.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public void actualizar(Rol rol) throws SQLException {
        String sql = "UPDATE roles SET nombre = ?, descripcion = ?, nivel_acceso = ?, esta_activo = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            establecerParametrosRol(stmt, rol);
            stmt.setInt(5, rol.getId());
            stmt.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM roles WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private Rol extraerRolDeResultSet(ResultSet rs) throws SQLException {
        Rol rol = new Rol();
        rol.setId(rs.getInt("id"));
        rol.setNombre(rs.getString("nombre"));
        rol.setDescripcion(rs.getString("descripcion"));
        rol.setNivelAcceso(rs.getInt("nivel_acceso"));
        rol.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
        rol.setFechaModificacion(rs.getTimestamp("fecha_modificacion"));
        rol.setEstaActivo(rs.getBoolean("esta_activo"));
        return rol;
    }

    private void establecerParametrosRol(PreparedStatement stmt, Rol rol) throws SQLException {
        stmt.setString(1, rol.getNombre());
        stmt.setString(2, rol.getDescripcion());
        stmt.setInt(3, rol.getNivelAcceso());
        stmt.setBoolean(4, rol.isEstaActivo());
    }
}
