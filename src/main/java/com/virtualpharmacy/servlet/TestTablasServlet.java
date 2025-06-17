package com.virtualpharmacy.servlet;

import com.virtualpharmacy.util.DatabaseConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/test-tablas")
public class TestTablasServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(TestTablasServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Mostrar el formulario de prueba
        request.getRequestDispatcher("/WEB-INF/jsp/test/test-tablas.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String tabla = request.getParameter("tabla");
        String operacion = request.getParameter("operacion");

        try {
            switch (operacion) {
                case "verificar":
                    verificarTabla(request, tabla);
                    break;
                case "contar":
                    contarRegistros(request, tabla);
                    break;
                case "listar":
                    listarRegistros(request, tabla);
                    break;
                default:
                    request.setAttribute("tipo", "warning");
                    request.setAttribute("mensaje", "Operación no válida");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al probar la tabla " + tabla, e);
            request.setAttribute("tipo", "danger");
            request.setAttribute("mensaje", "Error: " + e.getMessage());
        }

        request.getRequestDispatcher("/WEB-INF/jsp/test/test-tablas.jsp").forward(request, response);
    }

    private void verificarTabla(HttpServletRequest request, String tabla) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet tables = metaData.getTables(null, null, tabla, new String[] { "TABLE" });

            if (tables.next()) {
                // La tabla existe, obtener información de columnas
                ResultSet columns = metaData.getColumns(null, null, tabla, null);
                StringBuilder info = new StringBuilder();
                info.append("Tabla '").append(tabla).append("' existe.\n\nColumnas:\n");

                while (columns.next()) {
                    info.append("- ").append(columns.getString("COLUMN_NAME"))
                            .append(" (").append(columns.getString("TYPE_NAME")).append(")\n");
                }

                request.setAttribute("tipo", "success");
                request.setAttribute("mensaje", "La tabla existe");
                request.setAttribute("resultados", info.toString());
            } else {
                request.setAttribute("tipo", "danger");
                request.setAttribute("mensaje", "La tabla " + tabla + " no existe");
            }
        }
    }

    private void contarRegistros(HttpServletRequest request, String tabla) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT COUNT(*) as total FROM " + tabla;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    int total = rs.getInt("total");
                    request.setAttribute("tipo", "info");
                    request.setAttribute("mensaje", "La tabla " + tabla + " tiene " + total + " registros");
                    request.setAttribute("resultados", "Total de registros: " + total);
                }
            }
        }
    }

    private void listarRegistros(HttpServletRequest request, String tabla) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM " + tabla + " LIMIT 10";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnas = rsmd.getColumnCount();

                StringBuilder resultado = new StringBuilder();
                // Encabezados
                for (int i = 1; i <= columnas; i++) {
                    resultado.append(rsmd.getColumnName(i)).append("\t");
                }
                resultado.append("\n").append(String.format("%80s", "").replace(' ', '-')).append("\n");

                // Datos
                List<String[]> registros = new ArrayList<>();
                while (rs.next()) {
                    for (int i = 1; i <= columnas; i++) {
                        String valor = rs.getString(i);
                        resultado.append(valor != null ? valor : "NULL").append("\t");
                    }
                    resultado.append("\n");
                }

                request.setAttribute("tipo", "info");
                request.setAttribute("mensaje", "Primeros 10 registros de la tabla " + tabla);
                request.setAttribute("resultados", resultado.toString());
            }
        }
    }
}
