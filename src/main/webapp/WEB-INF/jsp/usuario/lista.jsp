<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/core"
prefix="c" %> <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Lista de Usuarios - Virtual Pharmacy</title>
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
      rel="stylesheet"
    />
  </head>
  <body>
    <div class="container mt-4">
      <h2>Lista de Usuarios</h2>

      <a
        href="${pageContext.request.contextPath}/usuarios/nuevo"
        class="btn btn-primary mb-3"
      >
        Nuevo Usuario
      </a>

      <table class="table table-striped">
        <thead>
          <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Apellido</th>
            <th>Documento</th>
            <th>Correo</th>
            <th>Teléfono</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="usuario" items="${usuarios}">
            <tr>
              <td>${usuario.id}</td>
              <td>${usuario.nombre}</td>
              <td>${usuario.apellido}</td>
              <td>${usuario.tipoDocumento} ${usuario.numeroDocumento}</td>
              <td>${usuario.correo}</td>
              <td>${usuario.numeroTelefono}</td>
              <td>
                <a
                  href="${pageContext.request.contextPath}/usuarios/editar?id=${usuario.id}"
                  class="btn btn-warning btn-sm"
                  >Editar</a
                >
                <a
                  href="${pageContext.request.contextPath}/usuarios/eliminar?id=${usuario.id}"
                  class="btn btn-danger btn-sm"
                  onclick="return confirm('¿Está seguro de eliminar este usuario?')"
                  >Eliminar</a
                >
              </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
  </body>
</html>
