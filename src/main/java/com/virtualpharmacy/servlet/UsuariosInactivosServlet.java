package com.virtualpharmacy.servlet;

import com.virtualpharmacy.dao.UsuariosInactivosDAO;
import com.virtualpharmacy.dao.UsuarioDAO;
import com.virtualpharmacy.model.UsuariosInactivos;
import com.virtualpharmacy.model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/usuarios-inactivos/*")
public class UsuariosInactivosServlet extends HttpServlet {
    private UsuariosInactivosDAO inactivosDAO;
    private UsuarioDAO usuarioDAO;

    @Override
    public void init() throws ServletException {
        inactivosDAO = new UsuariosInactivosDAO();
        usuarioDAO = new UsuarioDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();

        try {
            if (action == null || action.equals("/")) {
                listarInactivos(request, response);
            } else {
                switch (action) {
                    case "/nuevo":
                        mostrarFormularioNuevo(request, response);
                        break;
                    case "/editar":
                        mostrarFormularioEditar(request, response);
                        break;
                    case "/eliminar":
                        eliminarInactivo(request, response);
                        break;
                    case "/usuario":
                        listarPorUsuario(request, response);
                        break;
                    default:
                        listarInactivos(request, response);
                        break;
                }
            }
        } catch (SQLException ex) {
            request.setAttribute("error", "Error en la base de datos: " + ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("error", "Error: " + ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();

        try {
            switch (action) {
                case "/crear":
                    crearInactivo(request, response);
                    break;
                case "/actualizar":
                    actualizarInactivo(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/usuarios-inactivos");
                    break;
            }
        } catch (SQLException ex) {
            request.setAttribute("error", "Error en la base de datos: " + ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }
    }

    private void listarInactivos(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        List<UsuariosInactivos> inactivos = inactivosDAO.listarTodos();
        request.setAttribute("inactivos", inactivos);
        request.getRequestDispatcher("/WEB-INF/jsp/usuarios-inactivos/lista.jsp").forward(request, response);
    }

    private void listarPorUsuario(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int idUsuario = Integer.parseInt(request.getParameter("id"));
        List<UsuariosInactivos> inactivos = inactivosDAO.buscarPorUsuario(idUsuario);
        Usuario usuario = usuarioDAO.obtenerPorId(idUsuario);

        request.setAttribute("inactivos", inactivos);
        request.setAttribute("usuario", usuario);
        request.getRequestDispatcher("/WEB-INF/jsp/usuarios-inactivos/lista-por-usuario.jsp").forward(request,
                response);
    }

    private void mostrarFormularioNuevo(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        List<Usuario> usuarios = usuarioDAO.listarTodos();
        request.setAttribute("usuarios", usuarios);
        request.getRequestDispatcher("/WEB-INF/jsp/usuarios-inactivos/formulario.jsp").forward(request, response);
    }

    private void mostrarFormularioEditar(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        UsuariosInactivos inactivo = inactivosDAO.obtenerPorId(id);
        List<Usuario> usuarios = usuarioDAO.listarTodos();

        request.setAttribute("inactivo", inactivo);
        request.setAttribute("usuarios", usuarios);
        request.getRequestDispatcher("/WEB-INF/jsp/usuarios-inactivos/formulario.jsp").forward(request, response);
    }

    private void crearInactivo(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        // Validar campos
        String idUsuarioStr = request.getParameter("idUsuario");
        String razon = request.getParameter("razon");
        String descripcion = request.getParameter("descripcion");

        if (idUsuarioStr == null || idUsuarioStr.trim().isEmpty() ||
                razon == null || razon.trim().isEmpty() ||
                descripcion == null || descripcion.trim().isEmpty()) {

            request.setAttribute("error", "Todos los campos son obligatorios");
            mostrarFormularioNuevo(request, response);
            return;
        }

        try {
            UsuariosInactivos inactivo = new UsuariosInactivos();
            inactivo.setIdUsuario(Integer.parseInt(idUsuarioStr));
            inactivo.setRazon(razon.trim());
            inactivo.setDescripcion(descripcion.trim());

            inactivosDAO.crear(inactivo);
            response.sendRedirect(request.getContextPath() + "/usuarios-inactivos");
        } catch (NumberFormatException e) {
            request.setAttribute("error", "El ID de usuario no es válido");
            mostrarFormularioNuevo(request, response);
        }
    }

    private void actualizarInactivo(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String idUsuarioStr = request.getParameter("idUsuario");
        String razon = request.getParameter("razon");
        String descripcion = request.getParameter("descripcion");

        if (idUsuarioStr == null || idUsuarioStr.trim().isEmpty() ||
                razon == null || razon.trim().isEmpty() ||
                descripcion == null || descripcion.trim().isEmpty()) {

            request.setAttribute("error", "Todos los campos son obligatorios");
            mostrarFormularioEditar(request, response);
            return;
        }

        try {
            UsuariosInactivos inactivo = new UsuariosInactivos();
            inactivo.setId(id);
            inactivo.setIdUsuario(Integer.parseInt(idUsuarioStr));
            inactivo.setRazon(razon.trim());
            inactivo.setDescripcion(descripcion.trim());

            inactivosDAO.actualizar(inactivo);
            response.sendRedirect(request.getContextPath() + "/usuarios-inactivos");
        } catch (NumberFormatException e) {
            request.setAttribute("error", "El ID de usuario no es válido");
            mostrarFormularioEditar(request, response);
        }
    }

    private void eliminarInactivo(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        inactivosDAO.eliminar(id);
        response.sendRedirect(request.getContextPath() + "/usuarios-inactivos");
    }
}
