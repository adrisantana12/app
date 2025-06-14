<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/core"
prefix="c" %> <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Inventario - Virtual Pharmacy</title>
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
      rel="stylesheet"
    />
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.1/font/bootstrap-icons.css"
      rel="stylesheet"
    />
  </head>
  <body>
    <div class="container mt-4">
      <div class="row mb-3">
        <div class="col">
          <h2>Inventario de Medicamentos</h2>
        </div>
      </div>

      <div class="row mb-3">
        <div class="col-md-6">
          <a
            href="${pageContext.request.contextPath}/inventario/nuevo"
            class="btn btn-primary"
          >
            <i class="bi bi-plus-circle"></i> Nuevo Medicamento
          </a>
          <a
            href="${pageContext.request.contextPath}/inventario/bajoStock"
            class="btn btn-warning"
          >
            <i class="bi bi-exclamation-triangle"></i> Ver Bajo Stock
          </a>
        </div>
        <div class="col-md-6">
          <form
            action="${pageContext.request.contextPath}/inventario/buscar"
            method="get"
            class="d-flex"
          >
            <input
              type="text"
              name="nombre"
              class="form-control me-2"
              placeholder="Buscar medicamento..."
              value="${busqueda}"
            />
            <button type="submit" class="btn btn-outline-primary">
              <i class="bi bi-search"></i> Buscar
            </button>
          </form>
        </div>
      </div>

      <c:if test="${bajoStock}">
        <div class="alert alert-warning">
          <i class="bi bi-exclamation-triangle"></i> Mostrando medicamentos con
          stock bajo
        </div>
      </c:if>

      <div class="table-responsive">
        <table class="table table-striped table-hover">
          <thead class="table-dark">
            <tr>
              <th>ID</th>
              <th>Medicamento</th>
              <th>Presentación</th>
              <th>Stock</th>
              <th>Precio</th>
              <th>Vencimiento</th>
              <th>Estado</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="item" items="${inventario}">
              <tr>
                <td>${item.id}</td>
                <td>${item.nombreMedicamento}</td>
                <td>${item.presentacion}</td>
                <td>
                  <c:choose>
                    <c:when test="${item.stock <= item.cantidadMinAlerta}">
                      <span class="badge bg-danger">${item.stock}</span>
                    </c:when>
                    <c:otherwise>
                      <span class="badge bg-success">${item.stock}</span>
                    </c:otherwise>
                  </c:choose>
                </td>
                <td>$${item.precio}</td>
                <td>
                  <fmt:formatDate
                    value="${item.fechaVencimiento}"
                    pattern="dd/MM/yyyy"
                  />
                </td>
                <td>
                  <c:choose>
                    <c:when test="${item.estaInactivo eq 'SI'}">
                      <span class="badge bg-danger">Inactivo</span>
                    </c:when>
                    <c:otherwise>
                      <span class="badge bg-success">Activo</span>
                    </c:otherwise>
                  </c:choose>
                </td>
                <td>
                  <div class="btn-group">
                    <a
                      href="${pageContext.request.contextPath}/inventario/editar?id=${item.id}"
                      class="btn btn-warning btn-sm"
                    >
                      <i class="bi bi-pencil"></i>
                    </a>
                    <a
                      href="${pageContext.request.contextPath}/inventario/eliminar?id=${item.id}"
                      class="btn btn-danger btn-sm"
                      onclick="return confirm('¿Está seguro de eliminar este medicamento?')"
                    >
                      <i class="bi bi-trash"></i>
                    </a>
                  </div>
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
  </body>
</html>
