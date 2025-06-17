package com.virtualpharmacy.servlet;

import com.virtualpharmacy.dao.RolDAO;
import com.virtualpharmacy.model.Rol;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/roles/*")
public class RolesServlet extends HttpServlet {
    private RolDAO rolDAO;

    @Override
    public void init() throws ServletException {
        rolDAO = new RolDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null) {
            action = "/";
        }

        try {
            switch (action) {
                case "/nuevo":
                    mostrarFormularioNuevo(request, response);
                    break;
                case "/editar":
                    mostrarFormularioEditar(request, response);
                    break;
                case "/eliminar":
                    eliminarRol(request, response);
                    break;
                default:
                    listarRoles(request, response);
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
        if (action == null) {
            action = "/";
        }

        try {
            switch (action) {
                case "/crear":
                    crearRol(request, response);
                    break;
                case "/actualizar":
                    actualizarRol(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/roles");
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listarRoles(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        List<Rol> roles = rolDAO.listarTodos();
        request.setAttribute("roles", roles);
        request.getRequestDispatcher("/WEB-INF/jsp/roles/lista.jsp").forward(request, response);
    }

    private void mostrarFormularioNuevo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/roles/formulario.jsp").forward(request, response);
    }

    private void mostrarFormularioEditar(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Rol rol = rolDAO.obtenerPorId(id);
        request.setAttribute("rol", rol);
        request.getRequestDispatcher("/WEB-INF/jsp/roles/formulario.jsp").forward(request, response);
    }

    private void crearRol(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        Rol rol = extraerRolDeRequest(request);
        rolDAO.crear(rol);
        response.sendRedirect(request.getContextPath() + "/roles");
    }

    private void actualizarRol(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        Rol rol = extraerRolDeRequest(request);
        rol.setId(Integer.parseInt(request.getParameter("id")));
        rolDAO.actualizar(rol);
        response.sendRedirect(request.getContextPath() + "/roles");
    }

    private void eliminarRol(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        rolDAO.eliminar(id);
        response.sendRedirect(request.getContextPath() + "/roles");
    }

    private Rol extraerRolDeRequest(HttpServletRequest request) {
        Rol rol = new Rol();
        rol.setNombre(request.getParameter("nombre"));
        rol.setDescripcion(request.getParameter("descripcion"));
        rol.setNivelAcceso(Integer.parseInt(request.getParameter("nivelAcceso")));
        rol.setEstaActivo(request.getParameter("estaActivo") != null);
        return rol;
    }
}
