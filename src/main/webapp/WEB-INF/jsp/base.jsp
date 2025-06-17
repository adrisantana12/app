<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <title>${param.title} - Virtual Pharmacy</title>
    
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.1/font/bootstrap-icons.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
    
    <!-- jQuery -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <!-- Bootstrap 5 JS Bundle -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    
    <!-- Incluir configuración de seguridad -->
    <jsp:include page="/WEB-INF/jsp/includes/security.jsp" />
    
    <!-- Área para scripts adicionales -->
    <jsp:invoke fragment="scripts"/>
</head>
<body class="bg-light">
    <!-- Barra de navegación -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/">
                <i class="bi bi-hospital"></i> Virtual Pharmacy
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <c:if test="${not empty sessionScope.usuario}">
                        <!-- Menú de Inventario -->
                        <c:if test="${sessionScope.usuario.rol.nombre != 'AUXILIAR'}">
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" id="inventarioDropdown" role="button" 
                                   data-bs-toggle="dropdown">
                                    <i class="bi bi-box-seam"></i> Inventario
                                </a>
                                <ul class="dropdown-menu">
                                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/inventario/lista">
                                        <i class="bi bi-list"></i> Ver Inventario
                                    </a></li>
                                    <c:if test="${sessionScope.usuario.rol.nombre == 'ADMINISTRADOR'}">
                                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/inventario/nuevo">
                                            <i class="bi bi-plus-circle"></i> Nuevo Producto
                                        </a></li>
                                    </c:if>
                                </ul>
                            </li>
                        </c:if>
                        
                        <!-- Menú de Usuarios -->
                        <c:if test="${sessionScope.usuario.rol.nombre == 'ADMINISTRADOR'}">
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" id="usuariosDropdown" role="button" 
                                   data-bs-toggle="dropdown">
                                    <i class="bi bi-people"></i> Usuarios
                                </a>
                                <ul class="dropdown-menu">
                                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/usuarios/lista">
                                        <i class="bi bi-list"></i> Ver Usuarios
                                    </a></li>
                                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/usuarios/nuevo">
                                        <i class="bi bi-person-plus"></i> Nuevo Usuario
                                    </a></li>
                                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/roles/lista">
                                        <i class="bi bi-shield"></i> Roles
                                    </a></li>
                                </ul>
                            </li>
                        </c:if>
                        
                        <!-- Menú de Auditoría -->
                        <c:if test="${sessionScope.usuario.rol.nombre == 'ADMINISTRADOR'}">
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/auditoria/lista">
                                    <i class="bi bi-journal-text"></i> Auditoría
                                </a>
                            </li>
                        </c:if>
                    </c:if>
                </ul>
                
                <!-- Menú de usuario -->
                <ul class="navbar-nav">
                    <c:choose>
                        <c:when test="${empty sessionScope.usuario}">
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/login">
                                    <i class="bi bi-box-arrow-in-right"></i> Iniciar Sesión
                                </a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" 
                                   data-bs-toggle="dropdown">
                                    <i class="bi bi-person-circle"></i> ${sessionScope.usuario.nombre}
                                </a>
                                <ul class="dropdown-menu dropdown-menu-end">
                                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/usuarios/perfil">
                                        <i class="bi bi-person"></i> Mi Perfil
                                    </a></li>
                                    <li><hr class="dropdown-divider"></li>
                                    <li><a class="dropdown-item text-danger" href="${pageContext.request.contextPath}/logout">
                                        <i class="bi bi-box-arrow-right"></i> Cerrar Sesión
                                    </a></li>
                                </ul>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Contenedor principal -->
    <main class="container my-4">
        <!-- Mensajes de error/éxito -->
        <c:if test="${not empty requestScope.error}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <i class="bi bi-exclamation-triangle-fill"></i> ${requestScope.error}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>
        <c:if test="${not empty requestScope.success}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <i class="bi bi-check-circle-fill"></i> ${requestScope.success}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>
        
        <!-- Título de la página -->
        <h1 class="mb-4">${param.title}</h1>
        
        <!-- Contenido específico de la página -->
        <jsp:doBody/>
    </main>

    <!-- Footer -->
    <footer class="footer mt-auto py-3 bg-dark text-white">
        <div class="container text-center">
            <span>&copy; 2025 Virtual Pharmacy - Todos los derechos reservados</span>
        </div>
    </footer>
</body>
</html>
