package com.virtualpharmacy.servlet;

import com.virtualpharmacy.dao.CredencialesDAO;
import com.virtualpharmacy.dao.UsuarioDAO;
import com.virtualpharmacy.model.Credenciales;
import com.virtualpharmacy.model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/credenciales/*")
public class CredencialesServlet extends HttpServlet {
    private CredencialesDAO credencialesDAO;
    private UsuarioDAO usuarioDAO;

    @Override
    public void init() throws ServletException {
        credencialesDAO = new CredencialesDAO();
        usuarioDAO = new UsuarioDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();

        try {
            if (action == null || action.equals("/")) {
                listarCredenciales(request, response);
            } else {
                switch (action) {
                    case "/nuevo":
                        mostrarFormularioNuevo(request, response);
                        break;
                    case "/editar":
                        mostrarFormularioEditar(request, response);
                        break;
                    case "/eliminar":
                        eliminarCredencial(request, response);
                        break;
                    default:
                        listarCredenciales(request, response);
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
                    crearCredencial(request, response);
                    break;
                case "/actualizar":
                    actualizarCredencial(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/credenciales");
                    break;
            }
        } catch (SQLException ex) {
            request.setAttribute("error", "Error en la base de datos: " + ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }
    }

    private void listarCredenciales(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        List<Credenciales> credenciales = credencialesDAO.listarTodos();
        request.setAttribute("credenciales", credenciales);
        request.getRequestDispatcher("/WEB-INF/jsp/credenciales/lista.jsp").forward(request, response);
    }

    private void mostrarFormularioNuevo(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        List<Usuario> usuarios = usuarioDAO.listarTodos();
        request.setAttribute("usuarios", usuarios);
        request.getRequestDispatcher("/WEB-INF/jsp/credenciales/formulario.jsp").forward(request, response);
    }

    private void mostrarFormularioEditar(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Credenciales credencial = credencialesDAO.obtenerPorId(id);
        List<Usuario> usuarios = usuarioDAO.listarTodos();

        request.setAttribute("credencial", credencial);
        request.setAttribute("usuarios", usuarios);
        request.getRequestDispatcher("/WEB-INF/jsp/credenciales/formulario.jsp").forward(request, response);
    }

    private void crearCredencial(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        // Validar campos
        String usuario = request.getParameter("usuario");
        String contrasenia = request.getParameter("contrasenia");
        String idUsuarioStr = request.getParameter("idUsuario");

        if (usuario == null || usuario.trim().isEmpty() ||
                contrasenia == null || contrasenia.trim().isEmpty() ||
                idUsuarioStr == null || idUsuarioStr.trim().isEmpty()) {

            request.setAttribute("error", "Todos los campos son obligatorios");
            mostrarFormularioNuevo(request, response);
            return;
        }

        try {
            Credenciales credencial = new Credenciales();
            credencial.setUsuario(usuario.trim());
            credencial.setContrasenia(contrasenia);
            credencial.setIdUsuario(Integer.parseInt(idUsuarioStr));

            credencialesDAO.crear(credencial);
            response.sendRedirect(request.getContextPath() + "/credenciales");
        } catch (NumberFormatException e) {
            request.setAttribute("error", "El ID de usuario no es válido");
            mostrarFormularioNuevo(request, response);
        }
    }

    private void actualizarCredencial(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String usuario = request.getParameter("usuario");
        String contrasenia = request.getParameter("contrasenia");
        String idUsuarioStr = request.getParameter("idUsuario");

        if (usuario == null || usuario.trim().isEmpty() ||
                idUsuarioStr == null || idUsuarioStr.trim().isEmpty()) {

            request.setAttribute("error", "El usuario y el ID de usuario son obligatorios");
            mostrarFormularioEditar(request, response);
            return;
        }

        try {
            Credenciales credencial = new Credenciales();
            credencial.setId(id);
            credencial.setUsuario(usuario.trim());
            credencial.setIdUsuario(Integer.parseInt(idUsuarioStr));

            // Si se proporciona una nueva contraseña, actualizarla
            if (contrasenia != null && !contrasenia.trim().isEmpty()) {
                credencial.setContrasenia(contrasenia);
            } else {
                // Si no se proporciona contraseña, mantener la existente
                Credenciales credencialExistente = credencialesDAO.obtenerPorId(id);
                credencial.setContrasenia(credencialExistente.getContrasenia());
            }

            credencialesDAO.actualizar(credencial);
            response.sendRedirect(request.getContextPath() + "/credenciales");
        } catch (NumberFormatException e) {
            request.setAttribute("error", "El ID de usuario no es válido");
            mostrarFormularioEditar(request, response);
        }
    }

    private void eliminarCredencial(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        credencialesDAO.eliminar(id);
        response.sendRedirect(request.getContextPath() + "/credenciales");
    }
}
