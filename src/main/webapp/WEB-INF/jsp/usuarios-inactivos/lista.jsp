<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/core"
prefix="c" %> <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Usuarios Inactivos - Virtual Pharmacy</title>
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
      rel="stylesheet"
    />
    <link
      rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css"
    />
  </head>
  <body>
    <div class="container mt-4">
      <div class="row mb-4">
        <div class="col-md-8">
          <h2>Usuarios Inactivos</h2>
        </div>
        <div class="col-md-4 text-end">
          <a
            href="${pageContext.request.contextPath}/usuarios-inactivos/nuevo"
            class="btn btn-primary"
          >
            <i class="bi bi-plus-circle"></i> Registrar Usuario Inactivo
          </a>
        </div>
      </div>

      <c:if test="${error != null}">
        <div class="alert alert-danger" role="alert">${error}</div>
      </c:if>

      <div class="card">
        <div class="card-body">
          <div class="table-responsive">
            <table class="table table-striped">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Usuario</th>
                  <th>Razón</th>
                  <th>Descripción</th>
                  <th>Acciones</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach var="inactivo" items="${inactivos}">
                  <tr>
                    <td>${inactivo.id}</td>
                    <td>${inactivo.nombreCompletoUsuario}</td>
                    <td>${inactivo.razon}</td>
                    <td>${inactivo.descripcion}</td>
                    <td>
                      <div class="btn-group" role="group">
                        <a
                          href="${pageContext.request.contextPath}/usuarios-inactivos/editar?id=${inactivo.id}"
                          class="btn btn-sm btn-warning"
                        >
                          <i class="bi bi-pencil"></i> Editar
                        </a>
                        <a
                          href="${pageContext.request.contextPath}/usuarios-inactivos/eliminar?id=${inactivo.id}"
                          class="btn btn-sm btn-danger"
                          onclick="return confirm('¿Está seguro de que desea eliminar este registro?')"
                        >
                          <i class="bi bi-trash"></i> Eliminar
                        </a>
                        <a
                          href="${pageContext.request.contextPath}/usuarios-inactivos/usuario?id=${inactivo.idUsuario}"
                          class="btn btn-sm btn-info"
                        >
                          <i class="bi bi-person"></i> Ver Usuario
                        </a>
                      </div>
                    </td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
  </body>
</html>
