package com.virtualpharmacy.servlet;

import com.virtualpharmacy.dao.UsuarioDAO;
import com.virtualpharmacy.model.Usuario;

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

@WebServlet("/usuarios/*")
public class UsuarioServlet extends HttpServlet {
    private UsuarioDAO usuarioDAO;

    @Override
    public void init() throws ServletException {
        usuarioDAO = new UsuarioDAO();
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
                    eliminarUsuario(request, response);
                    break;
                default:
                    listarUsuarios(request, response);
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
                    crearUsuario(request, response);
                    break;
                case "/actualizar":
                    actualizarUsuario(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/usuarios");
                    break;
            }
        } catch (SQLException | ParseException ex) {
            throw new ServletException(ex);
        }
    }

    private void listarUsuarios(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        List<Usuario> usuarios = usuarioDAO.listarTodos();
        request.setAttribute("usuarios", usuarios);
        request.getRequestDispatcher("/WEB-INF/jsp/usuario/lista.jsp").forward(request, response);
    }

    private void mostrarFormularioNuevo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/usuario/formulario.jsp").forward(request, response);
    }

    private void mostrarFormularioEditar(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Usuario usuario = usuarioDAO.obtenerPorId(id);
        request.setAttribute("usuario", usuario);
        request.getRequestDispatcher("/WEB-INF/jsp/usuario/formulario.jsp").forward(request, response);
    }

    private void crearUsuario(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ParseException {
        Usuario usuario = extraerUsuarioDeRequest(request);
        usuarioDAO.crear(usuario);
        response.sendRedirect(request.getContextPath() + "/usuarios");
    }

    private void actualizarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ParseException {
        Usuario usuario = extraerUsuarioDeRequest(request);
        usuario.setId(Integer.parseInt(request.getParameter("id")));
        usuarioDAO.actualizar(usuario);
        response.sendRedirect(request.getContextPath() + "/usuarios");
    }

    private void eliminarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        usuarioDAO.eliminar(id);
        response.sendRedirect(request.getContextPath() + "/usuarios");
    }

    private Usuario extraerUsuarioDeRequest(HttpServletRequest request) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Usuario usuario = new Usuario();
        usuario.setIdRol(Integer.parseInt(request.getParameter("idRol")));
        usuario.setFechaIngreso(new Date());
        usuario.setNombre(request.getParameter("nombre"));
        usuario.setApellido(request.getParameter("apellido"));
        usuario.setTipoDocumento(request.getParameter("tipoDocumento"));
        usuario.setNumeroDocumento(Integer.parseInt(request.getParameter("numeroDocumento")));
        usuario.setFechaNacimiento(dateFormat.parse(request.getParameter("fechaNacimiento")));
        usuario.setCorreo(request.getParameter("correo"));
        usuario.setNumeroTelefono(request.getParameter("numeroTelefono"));
        usuario.setDireccionResidencia(request.getParameter("direccionResidencia"));
        usuario.setMunicipioResidencia(request.getParameter("municipioResidencia"));
        usuario.setDepartamentoResidencia(request.getParameter("departamentoResidencia"));
        usuario.setGenero(request.getParameter("genero"));
        usuario.setTipoRh(request.getParameter("tipoRh"));

        return usuario;
    }
}
