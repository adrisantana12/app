package com.virtualpharmacy.dao;

import com.virtualpharmacy.model.Roles;
import com.virtualpharmacy.util.AuditoriaUtil;
import com.virtualpharmacy.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RolesDAO {
    private static final Logger LOGGER = Logger.getLogger(RolesDAO.class.getName());
    private Connection connection;

    public RolesDAO() {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener la conexión a la base de datos", e);
            throw new RuntimeException("Error de conexión a la base de datos", e);
        }
    }

    public List<Roles> listarTodos() throws SQLException {
        List<Roles> roles = new ArrayList<>();
        String sql = "SELECT * FROM roles ORDER BY nombre";

        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                roles.add(mapearRol(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al listar roles", e);
            throw e;
        }
        return roles;
    }

    public Roles obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM roles WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearRol(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener rol con ID: " + id, e);
            throw e;
        }
        return null;
    }

    public void crear(Roles rol, String usuarioActual) throws SQLException {
        if (rol == null) {
            throw new IllegalArgumentException("El rol no puede ser null");
        }
        if (!rol.isValid()) {
            throw new IllegalArgumentException(rol.getValidationError());
        }

        connection.setAutoCommit(false);
        try {
            String sql = "INSERT INTO roles (nombre) VALUES (?)";

            try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, rol.getNombre());
                stmt.executeUpdate();

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        rol.setId(generatedKeys.getInt(1));
                        // Registrar en auditoría
                        AuditoriaUtil.registrarCreacion(connection, "roles", rol.getId(), usuarioActual,
                                String.format("Rol creado: %s", rol.getNombre()));
                        connection.commit();
                    }
                }
            }
        } catch (SQLException e) {
            connection.rollback();
            LOGGER.log(Level.SEVERE, "Error al crear rol: " + rol.getNombre(), e);
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void actualizar(Roles rol, String usuarioActual) throws SQLException {
        if (rol == null) {
            throw new IllegalArgumentException("El rol no puede ser null");
        }
        if (!rol.isValid()) {
            throw new IllegalArgumentException(rol.getValidationError());
        }

        Roles rolAnterior = obtenerPorId(rol.getId());
        if (rolAnterior == null) {
            throw new IllegalArgumentException("El rol a actualizar no existe");
        }

        connection.setAutoCommit(false);
        try {
            String sql = "UPDATE roles SET nombre = ? WHERE id = ?";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, rol.getNombre());
                stmt.setInt(2, rol.getId());
                int filasAfectadas = stmt.executeUpdate();

                if (filasAfectadas > 0) {
                    // Registrar en auditoría
                    AuditoriaUtil.registrarModificacion(connection, "roles", rol.getId(), usuarioActual,
                            String.format("Rol modificado - Anterior: %s, Nuevo: %s",
                                    rolAnterior.getNombre(), rol.getNombre()));
                    connection.commit();
                }
            }
        } catch (SQLException e) {
            connection.rollback();
            LOGGER.log(Level.SEVERE, "Error al actualizar rol con ID: " + rol.getId(), e);
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void eliminar(int id, String usuarioActual) throws SQLException {
        Roles rolAEliminar = obtenerPorId(id);
        if (rolAEliminar == null) {
            throw new IllegalArgumentException("El rol a eliminar no existe");
        }

        // Verificar si hay usuarios asociados
        if (tieneUsuariosAsociados(id)) {
            throw new IllegalStateException("No se puede eliminar el rol porque tiene usuarios asociados");
        }

        connection.setAutoCommit(false);
        try {
            String sql = "DELETE FROM roles WHERE id = ?";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, id);
                int filasAfectadas = stmt.executeUpdate();

                if (filasAfectadas > 0) {
                    // Registrar en auditoría
                    AuditoriaUtil.registrarEliminacion(connection, "roles", id, usuarioActual,
                            String.format("Rol eliminado: %s", rolAEliminar.getNombre()));
                    connection.commit();
                }
            }
        } catch (SQLException e) {
            connection.rollback();
            LOGGER.log(Level.SEVERE, "Error al eliminar rol con ID: " + id, e);
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public boolean existeNombre(String nombre) throws SQLException {
        String sql = "SELECT COUNT(*) FROM roles WHERE nombre = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    private boolean tieneUsuariosAsociados(int rolId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE id_rol = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, rolId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    private Roles mapearRol(ResultSet rs) throws SQLException {
        Roles rol = new Roles();
        rol.setId(rs.getInt("id"));
        rol.setNombre(rs.getString("nombre"));
        return rol;
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error al cerrar la conexión en el finalizador", e);
        } finally {
            super.finalize();
        }
    }
}
