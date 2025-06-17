package com.virtualpharmacy.filter;

import com.virtualpharmacy.util.SecurityUtil;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Filtro de seguridad para controlar el acceso a rutas protegidas y prevenir
 * ataques comunes como CSRF, XSS e inyección de cabeceras.
 */
@WebFilter("/*")
public class SecurityFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(SecurityFilter.class.getName());

    private static final List<String> PUBLIC_PATHS = Arrays.asList(
            "/login",
            "/logout",
            "/assets",
            "/css",
            "/js",
            "/images",
            "/error",
            "/acceso-denegado");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info("Inicializando SecurityFilter");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        // Configurar cabeceras de seguridad
        configurarCabecerasSeguridad(res);

        String path = req.getRequestURI().substring(req.getContextPath().length());

        // Registrar intento de acceso
        LOGGER.log(Level.FINE, "Acceso a ruta: {0} desde IP: {1}",
                new Object[] { path, req.getRemoteAddr() });

        // Permitir acceso a recursos públicos
        if (isPublicResource(path)) {
            chain.doFilter(request, response);
            return;
        }

        // Verificar si el usuario está autenticado
        String usuarioActual = SecurityUtil.getCurrentUser(req);
        if (usuarioActual == null) {
            LOGGER.log(Level.INFO, "Intento de acceso no autorizado a {0} desde {1}",
                    new Object[] { path, req.getRemoteAddr() });
            guardarUrlOriginal(req);
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // Verificar token CSRF para peticiones POST, PUT, DELETE
        if (isMethodProtected(req.getMethod()) && !validarTokenCSRF(req)) {
            LOGGER.log(Level.WARNING, "Intento de CSRF detectado en {0} desde {1}",
                    new Object[] { path, req.getRemoteAddr() });
            res.sendError(HttpServletResponse.SC_FORBIDDEN, "Token CSRF inválido");
            return;
        }

        // Verificar permisos basados en el rol
        if (!SecurityUtil.isRouteAllowed(req, path)) {
            LOGGER.log(Level.WARNING, "Acceso denegado a {0} para usuario {1} desde {2}",
                    new Object[] { path, usuarioActual, req.getRemoteAddr() });
            res.sendRedirect(req.getContextPath() + "/acceso-denegado");
            return;
        }

        // Verificar tiempo de inactividad de la sesión
        if (session != null && !isSessionValid(session)) {
            LOGGER.log(Level.INFO, "Sesión expirada para usuario {0}", usuarioActual);
            SecurityUtil.logout(req);
            guardarUrlOriginal(req);
            res.sendRedirect(req.getContextPath() + "/login?expired=true");
            return;
        }

        // Actualizar última actividad
        if (session != null) {
            session.setAttribute("ultima_actividad", System.currentTimeMillis());
        }

        // Continuar con la solicitud
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        LOGGER.info("Destruyendo SecurityFilter");
    }

    private boolean isPublicResource(String path) {
        return PUBLIC_PATHS.stream().anyMatch(path::startsWith) ||
                path.matches(".*\\.(css|js|png|jpg|gif|ico|woff|woff2)$");
    }

    private void configurarCabecerasSeguridad(HttpServletResponse res) {
        res.setHeader("X-Content-Type-Options", "nosniff");
        res.setHeader("X-Frame-Options", "DENY");
        res.setHeader("X-XSS-Protection", "1; mode=block");
        res.setHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains");
        res.setHeader("Content-Security-Policy",
                "default-src 'self'; script-src 'self' 'unsafe-inline' 'unsafe-eval'; " +
                        "style-src 'self' 'unsafe-inline'; img-src 'self' data:; " +
                        "font-src 'self' data:; connect-src 'self'");
    }

    private boolean isMethodProtected(String method) {
        return "POST".equalsIgnoreCase(method) ||
                "PUT".equalsIgnoreCase(method) ||
                "DELETE".equalsIgnoreCase(method);
    }

    private boolean validarTokenCSRF(HttpServletRequest req) {
        String tokenHeader = req.getHeader("X-CSRF-TOKEN");
        String tokenSession = (String) req.getSession(false).getAttribute("csrf_token");
        return tokenSession != null && tokenSession.equals(tokenHeader);
    }

    private boolean isSessionValid(HttpSession session) {
        Long ultimaActividad = (Long) session.getAttribute("ultima_actividad");
        if (ultimaActividad == null) {
            return false;
        }
        long tiempoInactivo = System.currentTimeMillis() - ultimaActividad;
        return tiempoInactivo < (30 * 60 * 1000); // 30 minutos
    }

    private void guardarUrlOriginal(HttpServletRequest req) {
        String queryString = req.getQueryString();
        String url = req.getRequestURI() + (queryString != null ? "?" + queryString : "");
        req.getSession().setAttribute("url_anterior", url);
    }
}
