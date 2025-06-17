<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/core"
prefix="c" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Lista de Roles - Virtual Pharmacy</title>
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
      rel="stylesheet"
    />
  </head>
  <body>
    <div class="container mt-4">
      <div class="row mb-4">
        <div class="col-md-8">
          <h2>Lista de Roles</h2>
        </div>
        <div class="col-md-4 text-end">
          <a
            href="${pageContext.request.contextPath}/roles/nuevo"
            class="btn btn-primary"
          >
            <i class="bi bi-plus-circle"></i> Nuevo Rol
          </a>
        </div>
      </div>

      <c:if test="${error != null}">
        <div class="alert alert-danger" role="alert">${error}</div>
      </c:if>

      <div class="card">
        <div class="card-body">
          <table class="table table-striped">
            <thead>
              <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Acciones</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach var="rol" items="${roles}">
                <tr>
                  <td>${rol.id}</td>
                  <td>${rol.nombre}</td>
                  <td>
                    <a
                      href="${pageContext.request.contextPath}/roles/editar?id=${rol.id}"
                      class="btn btn-sm btn-warning"
                    >
                      Editar
                    </a>
                    <a
                      href="${pageContext.request.contextPath}/roles/eliminar?id=${rol.id}"
                      class="btn btn-sm btn-danger"
                      onclick="return confirm('¿Está seguro de que desea eliminar este rol?')"
                    >
                      Eliminar
                    </a>
                  </td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </div>
      </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
  </body>
</html>
