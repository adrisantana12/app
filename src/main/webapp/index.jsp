<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Virtual Pharmacy - Sistema de Gestión</title>
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
      rel="stylesheet"
    />
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.1/font/bootstrap-icons.css"
      rel="stylesheet"
    />
    <style>
      .feature-icon {
        font-size: 2.5rem;
        color: #0d6efd;
        margin-bottom: 1rem;
      }
    </style>
  </head>
  <body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
      <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/">
          <i class="bi bi-hospital"></i> Virtual Pharmacy
        </a>
        <button
          class="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarMain"
        >
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarMain">
          <ul class="navbar-nav">
            <li class="nav-item">
              <a
                class="nav-link"
                href="${pageContext.request.contextPath}/usuarios"
              >
                <i class="bi bi-people"></i> Usuarios
              </a>
            </li>
            <li class="nav-item">
              <a
                class="nav-link"
                href="${pageContext.request.contextPath}/inventario"
              >
                <i class="bi bi-box-seam"></i> Inventario
              </a>
            </li>
          </ul>
        </div>
      </div>
    </nav>

    <div class="container my-5">
      <div class="row text-center mb-5">
        <div class="col">
          <h1 class="display-4">Bienvenido a Virtual Pharmacy</h1>
          <p class="lead">Sistema de gestión integral para farmacias</p>
        </div>
      </div>

      <div class="row g-4 py-5">
        <div class="col-md-4">
          <div class="text-center">
            <div class="feature-icon">
              <i class="bi bi-people-fill"></i>
            </div>
            <h3>Gestión de Usuarios</h3>
            <p>
              Administre usuarios, roles y permisos del sistema de manera
              eficiente.
            </p>
            <a
              href="${pageContext.request.contextPath}/usuarios"
              class="btn btn-primary"
            >
              Ir a Usuarios
            </a>
          </div>
        </div>

        <div class="col-md-4">
          <div class="text-center">
            <div class="feature-icon">
              <i class="bi bi-box-seam"></i>
            </div>
            <h3>Control de Inventario</h3>
            <p>
              Gestione el inventario de medicamentos, stock y alertas de manera
              efectiva.
            </p>
            <a
              href="${pageContext.request.contextPath}/inventario"
              class="btn btn-primary"
            >
              Ir a Inventario
            </a>
          </div>
        </div>

        <div class="col-md-4">
          <div class="text-center">
            <div class="feature-icon">
              <i class="bi bi-graph-up"></i>
            </div>
            <h3>Reportes</h3>
            <p>
              Acceda a reportes detallados sobre el inventario y la gestión de
              la farmacia.
            </p>
            <button class="btn btn-primary" disabled>Próximamente</button>
          </div>
        </div>
      </div>
    </div>

    <footer class="bg-light py-4 mt-auto">
      <div class="container">
        <div class="text-center">
          <p class="mb-0">
            &copy; 2025 Virtual Pharmacy. Todos los derechos reservados.
          </p>
        </div>
      </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
  </body>
</html>
