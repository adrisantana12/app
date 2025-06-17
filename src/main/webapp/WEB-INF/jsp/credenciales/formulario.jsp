<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${credencial == null ? 'Nueva' : 'Editar'} Credencial - Virtual Pharmacy</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css">
</head>
<body>
    <div class="container mt-4">
        <div class="row">
            <div class="col-md-8 offset-md-2">
                <div class="card">
                    <div class="card-header">
                        <h2 class="mb-0">${credencial == null ? 'Nueva' : 'Editar'} Credencial</h2>
                    </div>
                    <div class="card-body">
                        <c:if test="${error != null}">
                            <div class="alert alert-danger" role="alert">
                                ${error}
                            </div>
                        </c:if>

                        <form action="${pageContext.request.contextPath}/credenciales/${credencial == null ? 'crear' : 'actualizar'}" 
                              method="post" class="needs-validation" novalidate>
                            
                            <c:if test="${credencial != null}">
                                <input type="hidden" name="id" value="${credencial.id}">
                            </c:if>
                            
                            <div class="mb-3">
                                <label for="idUsuario" class="form-label">Usuario</label>
                                <select class="form-select" id="idUsuario" name="idUsuario" required>
                                    <option value="">Seleccione un usuario</option>
                                    <c:forEach var="usuario" items="${usuarios}">
                                        <option value="${usuario.id}" 
                                                ${credencial.idUsuario == usuario.id ? 'selected' : ''}>
                                            ${usuario.nombre} ${usuario.apellido}
                                        </option>
                                    </c:forEach>
                                </select>
                                <div class="invalid-feedback">
                                    Por favor seleccione un usuario.
                                </div>
                            </div>
                            
                            <div class="mb-3">
                                <label for="usuario" class="form-label">Nombre de Usuario</label>
                                <input type="text" class="form-control" id="usuario" name="usuario" 
                                       value="${credencial.usuario}" required>
                                <div class="invalid-feedback">
                                    El nombre de usuario es obligatorio.
                                </div>
                            </div>
                            
                            <div class="mb-3">
                                <label for="contrasenia" class="form-label">
                                    ${credencial == null ? 'Contraseña' : 'Nueva Contraseña'}</label>
                                <input type="password" class="form-control" id="contrasenia" name="contrasenia"
                                       ${credencial == null ? 'required' : ''}>
                                <div class="invalid-feedback">
                                    ${credencial == null ? 'La contraseña es obligatoria.' : 'Deje en blanco para mantener la contraseña actual.'}
                                </div>
                                <c:if test="${credencial != null}">
                                    <small class="form-text text-muted">
                                        Deje en blanco para mantener la contraseña actual.
                                    </small>
                                </c:if>
                            </div>
                            
                            <div class="mb-3">
                                <label for="confirmarContrasenia" class="form-label">
                                    ${credencial == null ? 'Confirmar Contraseña' : 'Confirmar Nueva Contraseña'}</label>
                                <input type="password" class="form-control" id="confirmarContrasenia" 
                                       ${credencial == null ? 'required' : ''}>
                                <div class="invalid-feedback">
                                    Las contraseñas no coinciden.
                                </div>
                            </div>
                            
                            <div class="mb-3">
                                <button type="submit" class="btn btn-primary">
                                    <i class="bi bi-check-circle"></i> Guardar
                                </button>
                                <a href="${pageContext.request.contextPath}/credenciales" class="btn btn-secondary">
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
            var password = document.getElementById('contrasenia')
            var confirm = document.getElementById('confirmarContrasenia')
            
            // Validación de contraseñas
            function validatePassword() {
                if (password.value != confirm.value) {
                    confirm.setCustomValidity('Las contraseñas no coinciden')
                } else {
                    confirm.setCustomValidity('')
                }
            }
            
            password.onchange = validatePassword
            confirm.onkeyup = validatePassword
            
            // Validación del formulario
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
