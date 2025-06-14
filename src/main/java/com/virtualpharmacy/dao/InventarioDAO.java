package com.virtualpharmacy.dao;

import com.virtualpharmacy.model.Inventario;
import com.virtualpharmacy.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventarioDAO {

    public List<Inventario> listarTodos() throws SQLException {
        List<Inventario> inventario = new ArrayList<>();
        String sql = "SELECT * FROM inventario";

        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                inventario.add(extraerInventarioDeResultSet(rs));
            }
        }
        return inventario;
    }

    public Inventario obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM inventario WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extraerInventarioDeResultSet(rs);
                }
            }
        }
        return null;
    }

    public void crear(Inventario inventario) throws SQLException {
        String sql = "INSERT INTO inventario (nombre_medicamento, presentacion, cantidad_min_alerta, " +
                "stock, fecha_ingreso, fecha_vencimiento, via_administracion, almacenamiento, " +
                "especificaciones, precio, peso, categoria, esta_inactivo, usuario_registra) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            establecerParametrosInventario(stmt, inventario);

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    inventario.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public void actualizar(Inventario inventario) throws SQLException {
        String sql = "UPDATE inventario SET nombre_medicamento=?, presentacion=?, cantidad_min_alerta=?, " +
                "stock=?, fecha_ingreso=?, fecha_vencimiento=?, via_administracion=?, " +
                "almacenamiento=?, especificaciones=?, precio=?, peso=?, categoria=?, " +
                "esta_inactivo=?, usuario_registra=? WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            establecerParametrosInventario(stmt, inventario);
            stmt.setInt(15, inventario.getId());

            stmt.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM inventario WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Inventario> buscarPorNombre(String nombre) throws SQLException {
        List<Inventario> inventario = new ArrayList<>();
        String sql = "SELECT * FROM inventario WHERE nombre_medicamento LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + nombre + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    inventario.add(extraerInventarioDeResultSet(rs));
                }
            }
        }
        return inventario;
    }

    public List<Inventario> obtenerPorBajoStock() throws SQLException {
        List<Inventario> inventario = new ArrayList<>();
        String sql = "SELECT * FROM inventario WHERE stock <= cantidad_min_alerta";

        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                inventario.add(extraerInventarioDeResultSet(rs));
            }
        }
        return inventario;
    }

    private Inventario extraerInventarioDeResultSet(ResultSet rs) throws SQLException {
        Inventario inventario = new Inventario();
        inventario.setId(rs.getInt("id"));
        inventario.setNombreMedicamento(rs.getString("nombre_medicamento"));
        inventario.setPresentacion(rs.getString("presentacion"));
        inventario.setCantidadMinAlerta(rs.getInt("cantidad_min_alerta"));
        inventario.setStock(rs.getInt("stock"));
        inventario.setFechaIngreso(rs.getDate("fecha_ingreso"));
        inventario.setFechaVencimiento(rs.getDate("fecha_vencimiento"));
        inventario.setViaAdministracion(rs.getString("via_administracion"));
        inventario.setAlmacenamiento(rs.getString("almacenamiento"));
        inventario.setEspecificaciones(rs.getString("especificaciones"));
        inventario.setPrecio(rs.getInt("precio"));
        inventario.setPeso(rs.getInt("peso"));
        inventario.setCategoria(rs.getString("categoria"));
        inventario.setEstaInactivo(rs.getString("esta_inactivo"));
        inventario.setUsuarioRegistra(rs.getString("usuario_registra"));
        return inventario;
    }

    private void establecerParametrosInventario(PreparedStatement stmt, Inventario inventario) throws SQLException {
        stmt.setString(1, inventario.getNombreMedicamento());
        stmt.setString(2, inventario.getPresentacion());
        stmt.setInt(3, inventario.getCantidadMinAlerta());
        stmt.setInt(4, inventario.getStock());
        stmt.setDate(5, new java.sql.Date(inventario.getFechaIngreso().getTime()));
        stmt.setDate(6, new java.sql.Date(inventario.getFechaVencimiento().getTime()));
        stmt.setString(7, inventario.getViaAdministracion());
        stmt.setString(8, inventario.getAlmacenamiento());
        stmt.setString(9, inventario.getEspecificaciones());
        stmt.setInt(10, inventario.getPrecio());
        stmt.setInt(11, inventario.getPeso());
        stmt.setString(12, inventario.getCategoria());
        stmt.setString(13, inventario.getEstaInactivo());
        stmt.setString(14, inventario.getUsuarioRegistra());
    }
}
