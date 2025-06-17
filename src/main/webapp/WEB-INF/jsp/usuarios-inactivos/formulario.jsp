<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${inactivo == null ? 'Registrar' : 'Editar'} Usuario Inactivo - Virtual Pharmacy</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css">
</head>
<body>
    <div class="container mt-4">
        <div class="row">
            <div class="col-md-8 offset-md-2">
                <div class="card">
                    <div class="card-header">
                        <h3 class="mb-0">${inactivo == null ? 'Registrar' : 'Editar'} Usuario Inactivo</h3>
                    </div>
                    <div class="card-body">
                        <c:if test="${error != null}">
                            <div class="alert alert-danger" role="alert">
                                ${error}
                            </div>
                        </c:if>

                        <form action="${pageContext.request.contextPath}/usuarios-inactivos/${inactivo == null ? 'crear' : 'actualizar'}" 
                              method="post" class="needs-validation" novalidate>
                            
                            <c:if test="${inactivo != null}">
                                <input type="hidden" name="id" value="${inactivo.id}">
                            </c:if>
                            
                            <div class="mb-3">
                                <label for="idUsuario" class="form-label">Usuario</label>
                                <select class="form-select" id="idUsuario" name="idUsuario" required>
                                    <option value="">Seleccione un usuario</option>
                                    <c:forEach var="usuario" items="${usuarios}">
                                        <option value="${usuario.id}" 
                                                ${inactivo.idUsuario == usuario.id ? 'selected' : ''}>
                                            ${usuario.nombre} ${usuario.apellido} - ${usuario.numeroDocumento}
                                        </option>
                                    </c:forEach>
                                </select>
                                <div class="invalid-feedback">
                                    Por favor seleccione un usuario.
                                </div>
                            </div>
                            
                            <div class="mb-3">
                                <label for="razon" class="form-label">Razón de Inactivación</label>
                                <select class="form-select" id="razon" name="razon" required>
                                    <option value="">Seleccione una razón</option>
                                    <option value="Renuncia" ${inactivo.razon == 'Renuncia' ? 'selected' : ''}>Renuncia</option>
                                    <option value="Despido" ${inactivo.razon == 'Despido' ? 'selected' : ''}>Despido</option>
                                    <option value="Licencia" ${inactivo.razon == 'Licencia' ? 'selected' : ''}>Licencia</option>
                                    <option value="Otro" ${inactivo.razon == 'Otro' ? 'selected' : ''}>Otro</option>
                                </select>
                                <div class="invalid-feedback">
                                    Por favor seleccione una razón.
                                </div>
                            </div>
                            
                            <div class="mb-3">
                                <label for="descripcion" class="form-label">Descripción</label>
                                <textarea class="form-control" id="descripcion" name="descripcion" 
                                          rows="3" required>${inactivo.descripcion}</textarea>
                                <div class="invalid-feedback">
                                    Por favor ingrese una descripción.
                                </div>
                            </div>
                            
                            <div class="mb-3">
                                <button type="submit" class="btn btn-primary">
                                    <i class="bi bi-check-circle"></i> Guardar
                                </button>
                                <a href="${pageContext.request.contextPath}/usuarios-inactivos" class="btn btn-secondary">
                                    <i class="bi bi-x-circle"></i> Cancelar
                                </a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        (function () {
            'use strict'
            var forms = document.querySelectorAll('.needs-validation')
            Array.prototype.slice.call(forms)
                .forEach(function (form) {
                    form.addEventListener('submit', function (event) {
                        if (!form.checkValidity()) {
                            event.preventDefault()
                            event.stopPropagation()
                        }
                        form.classList.add('was-validated')
                    }, false)
                })
        })()
    </script>
</body>
</html>
