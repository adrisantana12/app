<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/core"
prefix="c" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Lista de Credenciales - Virtual Pharmacy</title>
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
          <h2>Lista de Credenciales</h2>
        </div>
        <div class="col-md-4 text-end">
          <a
            href="${pageContext.request.contextPath}/credenciales/nuevo"
            class="btn btn-primary"
          >
            <i class="bi bi-plus-circle"></i> Nueva Credencial
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
                <th>Usuario</th>
                <th>Nombre Completo</th>
                <th>Acciones</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach var="credencial" items="${credenciales}">
                <tr>
                  <td>${credencial.id}</td>
                  <td>${credencial.usuario}</td>
                  <td>${credencial.nombreCompletoUsuario}</td>
                  <td>
                    <a
                      href="${pageContext.request.contextPath}/credenciales/editar?id=${credencial.id}"
                      class="btn btn-sm btn-warning"
                    >
                      <i class="bi bi-pencil"></i> Editar
                    </a>
                    <a
                      href="${pageContext.request.contextPath}/credenciales/eliminar?id=${credencial.id}"
                      class="btn btn-sm btn-danger"
                      onclick="return confirm('¿Está seguro de que desea eliminar esta credencial?')"
                    >
                      <i class="bi bi-trash"></i> Eliminar
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
