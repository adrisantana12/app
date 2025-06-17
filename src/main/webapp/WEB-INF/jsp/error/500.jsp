<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Error del Servidor - Virtual Pharmacy</title>
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
      rel="stylesheet"
    />
  </head>
  <body class="bg-light">
    <div class="container mt-5">
      <div class="row justify-content-center">
        <div class="col-md-6 text-center">
          <h1 class="display-1">500</h1>
          <h2 class="mb-4">Error del Servidor</h2>
          <p class="lead mb-4">
            Lo sentimos, ha ocurrido un error interno en el servidor.
          </p>
          <a href="${pageContext.request.contextPath}/" class="btn btn-primary">
            Volver al inicio
          </a>
        </div>
      </div>
    </div>
  </body>
</html>
