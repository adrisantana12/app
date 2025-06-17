<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Prueba de Tablas - Virtual Pharmacy</title>
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
      rel="stylesheet"
    />
  </head>
  <body>
    <div class="container mt-5">
      <h2>Prueba de Tablas de la Base de Datos</h2>

      <!-- Mensajes de resultado -->
      <c:if test="${not empty mensaje}">
        <div class="alert alert-${tipo}" role="alert">${mensaje}</div>
      </c:if>

      <!-- Formulario para probar tabla roles -->
      <div class="card mb-4">
        <div class="card-header">
          <h4>Tabla Roles</h4>
        </div>
        <div class="card-body">
          <form
            action="${pageContext.request.contextPath}/test-tablas"
            method="post"
          >
            <input type="hidden" name="tabla" value="roles" />
            <div class="mb-3">
              <label class="form-label">Operación:</label>
              <select name="operacion" class="form-select">
                <option value="verificar">Verificar Existencia</option>
                <option value="contar">Contar Registros</option>
                <option value="listar">Listar Registros</option>
              </select>
            </div>
            <button type="submit" class="btn btn-primary">Probar Roles</button>
          </form>
        </div>
      </div>

      <!-- Formulario para probar tabla credenciales -->
      <div class="card mb-4">
        <div class="card-header">
          <h4>Tabla Credenciales</h4>
        </div>
        <div class="card-body">
          <form
            action="${pageContext.request.contextPath}/test-tablas"
            method="post"
          >
            <input type="hidden" name="tabla" value="credenciales" />
            <div class="mb-3">
              <label class="form-label">Operación:</label>
              <select name="operacion" class="form-select">
                <option value="verificar">Verificar Existencia</option>
                <option value="contar">Contar Registros</option>
                <option value="listar">Listar Registros</option>
              </select>
            </div>
            <button type="submit" class="btn btn-primary">
              Probar Credenciales
            </button>
          </form>
        </div>
      </div>

      <!-- Formulario para probar tabla inventario -->
      <div class="card mb-4">
        <div class="card-header">
          <h4>Tabla Inventario</h4>
        </div>
        <div class="card-body">
          <form
            action="${pageContext.request.contextPath}/test-tablas"
            method="post"
          >
            <input type="hidden" name="tabla" value="inventario" />
            <div class="mb-3">
              <label class="form-label">Operación:</label>
              <select name="operacion" class="form-select">
                <option value="verificar">Verificar Existencia</option>
                <option value="contar">Contar Registros</option>
                <option value="listar">Listar Registros</option>
              </select>
            </div>
            <button type="submit" class="btn btn-primary">
              Probar Inventario
            </button>
          </form>
        </div>
      </div>

      <!-- Formulario para probar tabla usuarios_inactivos -->
      <div class="card mb-4">
        <div class="card-header">
          <h4>Tabla Usuarios Inactivos</h4>
        </div>
        <div class="card-body">
          <form
            action="${pageContext.request.contextPath}/test-tablas"
            method="post"
          >
            <input type="hidden" name="tabla" value="usuarios_inactivos" />
            <div class="mb-3">
              <label class="form-label">Operación:</label>
              <select name="operacion" class="form-select">
                <option value="verificar">Verificar Existencia</option>
                <option value="contar">Contar Registros</option>
                <option value="listar">Listar Registros</option>
              </select>
            </div>
            <button type="submit" class="btn btn-primary">
              Probar Usuarios Inactivos
            </button>
          </form>
        </div>
      </div>

      <!-- Resultados -->
      <c:if test="${not empty resultados}">
        <div class="card mt-4">
          <div class="card-header">
            <h4>Resultados</h4>
          </div>
          <div class="card-body">
            <pre class="bg-light p-3">${resultados}</pre>
          </div>
        </div>
      </c:if>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
  </body>
</html>
