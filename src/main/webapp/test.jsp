<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Virtual Pharmacy - Test</title>
  </head>
  <body>
    <h1>Virtual Pharmacy</h1>
    <p>
      Si puedes ver esta página, la aplicación está funcionando correctamente.
    </p>
    <p>Fecha actual: <%= new java.util.Date() %></p>

    <h2>Enlaces de prueba:</h2>
    <ul>
      <li><a href="test-tablas">Probar Tablas</a></li>
      <li><a href="roles">Gestión de Roles</a></li>
      <li><a href="usuarios">Gestión de Usuarios</a></li>
      <li><a href="credenciales">Gestión de credenciales</a></li>
    </ul>
  </body>
</html>
