package com.virtualpharmacy.servlet;

import com.virtualpharmacy.dao.InventarioDAO;
import com.virtualpharmacy.model.Inventario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/inventario/*")
public class InventarioServlet extends HttpServlet {
    private InventarioDAO inventarioDAO;

    @Override
    public void init() throws ServletException {
        inventarioDAO = new InventarioDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();

        try {
            switch (action) {
                case "/nuevo":
                    mostrarFormularioNuevo(request, response);
                    break;
                case "/editar":
                    mostrarFormularioEditar(request, response);
                    break;
                case "/eliminar":
                    eliminarMedicamento(request, response);
                    break;
                case "/buscar":
                    buscarMedicamentos(request, response);
                    break;
                case "/bajoStock":
                    listarBajoStock(request, response);
                    break;
                default:
                    listarInventario(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();

        try {
            switch (action) {
                case "/crear":
                    crearMedicamento(request, response);
                    break;
                case "/actualizar":
                    actualizarMedicamento(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/inventario");
                    break;
            }
        } catch (SQLException | ParseException ex) {
            throw new ServletException(ex);
        }
    }

    private void listarInventario(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        List<Inventario> inventario = inventarioDAO.listarTodos();
        request.setAttribute("inventario", inventario);
        request.getRequestDispatcher("/WEB-INF/jsp/inventario/lista.jsp").forward(request, response);
    }

    private void buscarMedicamentos(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        String nombre = request.getParameter("nombre");
        List<Inventario> inventario = inventarioDAO.buscarPorNombre(nombre);
        request.setAttribute("inventario", inventario);
        request.setAttribute("busqueda", nombre);
        request.getRequestDispatcher("/WEB-INF/jsp/inventario/lista.jsp").forward(request, response);
    }

    private void listarBajoStock(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        List<Inventario> inventario = inventarioDAO.obtenerPorBajoStock();
        request.setAttribute("inventario", inventario);
        request.setAttribute("bajoStock", true);
        request.getRequestDispatcher("/WEB-INF/jsp/inventario/lista.jsp").forward(request, response);
    }

    private void mostrarFormularioNuevo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/inventario/formulario.jsp").forward(request, response);
    }

    private void mostrarFormularioEditar(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Inventario inventario = inventarioDAO.obtenerPorId(id);
        request.setAttribute("inventario", inventario);
        request.getRequestDispatcher("/WEB-INF/jsp/inventario/formulario.jsp").forward(request, response);
    }

    private void crearMedicamento(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ParseException {
        Inventario inventario = extraerInventarioDeRequest(request);
        inventarioDAO.crear(inventario);
        response.sendRedirect(request.getContextPath() + "/inventario");
    }

    private void actualizarMedicamento(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ParseException {
        Inventario inventario = extraerInventarioDeRequest(request);
        inventario.setId(Integer.parseInt(request.getParameter("id")));
        inventarioDAO.actualizar(inventario);
        response.sendRedirect(request.getContextPath() + "/inventario");
    }

    private void eliminarMedicamento(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        inventarioDAO.eliminar(id);
        response.sendRedirect(request.getContextPath() + "/inventario");
    }

    private Inventario extraerInventarioDeRequest(HttpServletRequest request) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Inventario inventario = new Inventario();
        inventario.setNombreMedicamento(request.getParameter("nombreMedicamento"));
        inventario.setPresentacion(request.getParameter("presentacion"));
        inventario.setCantidadMinAlerta(Integer.parseInt(request.getParameter("cantidadMinAlerta")));
        inventario.setStock(Integer.parseInt(request.getParameter("stock")));
        inventario.setFechaIngreso(new Date());
        inventario.setFechaVencimiento(dateFormat.parse(request.getParameter("fechaVencimiento")));
        inventario.setViaAdministracion(request.getParameter("viaAdministracion"));
        inventario.setAlmacenamiento(request.getParameter("almacenamiento"));
        inventario.setEspecificaciones(request.getParameter("especificaciones"));
        inventario.setPrecio(Integer.parseInt(request.getParameter("precio")));
        inventario.setPeso(Integer.parseInt(request.getParameter("peso")));
        inventario.setCategoria(request.getParameter("categoria"));
        inventario.setEstaInactivo("NO");
        inventario.setUsuarioRegistra(request.getParameter("usuarioRegistra"));

        return inventario;
    }
}
