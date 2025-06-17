<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Página no encontrada - Virtual Pharmacy</title>
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
      rel="stylesheet"
    />
  </head>
  <body class="bg-light">
    <div class="container mt-5">
      <div class="row justify-content-center">
        <div class="col-md-6 text-center">
          <h1 class="display-1">404</h1>
          <h2 class="mb-4">Página no encontrada</h2>
          <p class="lead mb-4">
            Lo sentimos, la página que estás buscando no existe o ha sido
            movida.
          </p>
          <a href="${pageContext.request.contextPath}/" class="btn btn-primary">
            Volver al inicio
          </a>
        </div>
      </div>
    </div>
  </body>
</html>
