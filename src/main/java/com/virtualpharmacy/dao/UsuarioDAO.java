package com.virtualpharmacy.dao;

import com.virtualpharmacy.model.Usuario;
import com.virtualpharmacy.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public Usuario obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extraerUsuarioDeResultSet(rs);
                }
            }
        }
        return null;
    }

    public List<Usuario> listarTodos() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";

        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                usuarios.add(extraerUsuarioDeResultSet(rs));
            }
        }
        return usuarios;
    }

    public void crear(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (id_rol, fecha_ingreso, nombre, apellido, " +
                "tipo_documento, numero_documento, fecha_nacimiento, correo, " +
                "numero_telefono, direccion_residencia, municipio_residencia, " +
                "departamento_residencia, genero, tipo_rh) VALUES " +
                "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            establecerParametrosUsuario(stmt, usuario);

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    usuario.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public void actualizar(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuarios SET id_rol=?, fecha_ingreso=?, nombre=?, apellido=?, " +
                "tipo_documento=?, numero_documento=?, fecha_nacimiento=?, correo=?, " +
                "numero_telefono=?, direccion_residencia=?, municipio_residencia=?, " +
                "departamento_residencia=?, genero=?, tipo_rh=? WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            establecerParametrosUsuario(stmt, usuario);
            stmt.setInt(15, usuario.getId());

            stmt.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private Usuario extraerUsuarioDeResultSet(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getInt("id"));
        usuario.setIdRol(rs.getInt("id_rol"));
        usuario.setFechaIngreso(rs.getDate("fecha_ingreso"));
        usuario.setNombre(rs.getString("nombre"));
        usuario.setApellido(rs.getString("apellido"));
        usuario.setTipoDocumento(rs.getString("tipo_documento"));
        usuario.setNumeroDocumento(rs.getInt("numero_documento"));
        usuario.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
        usuario.setCorreo(rs.getString("correo"));
        usuario.setNumeroTelefono(rs.getString("numero_telefono"));
        usuario.setDireccionResidencia(rs.getString("direccion_residencia"));
        usuario.setMunicipioResidencia(rs.getString("municipio_residencia"));
        usuario.setDepartamentoResidencia(rs.getString("departamento_residencia"));
        usuario.setGenero(rs.getString("genero"));
        usuario.setTipoRh(rs.getString("tipo_rh"));
        return usuario;
    }

    private void establecerParametrosUsuario(PreparedStatement stmt, Usuario usuario) throws SQLException {
        stmt.setInt(1, usuario.getIdRol());
        stmt.setDate(2, new java.sql.Date(usuario.getFechaIngreso().getTime()));
        stmt.setString(3, usuario.getNombre());
        stmt.setString(4, usuario.getApellido());
        stmt.setString(5, usuario.getTipoDocumento());
        stmt.setInt(6, usuario.getNumeroDocumento());
        stmt.setDate(7, new java.sql.Date(usuario.getFechaNacimiento().getTime()));
        stmt.setString(8, usuario.getCorreo());
        stmt.setString(9, usuario.getNumeroTelefono());
        stmt.setString(10, usuario.getDireccionResidencia());
        stmt.setString(11, usuario.getMunicipioResidencia());
        stmt.setString(12, usuario.getDepartamentoResidencia());
        stmt.setString(13, usuario.getGenero());
        stmt.setString(14, usuario.getTipoRh());
    }
}
