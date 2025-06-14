<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${usuario == null ? 'Nuevo' : 'Editar'} Usuario - Virtual Pharmacy</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <h2>${usuario == null ? 'Nuevo' : 'Editar'} Usuario</h2>
        
        <form action="${pageContext.request.contextPath}/usuarios/${usuario == null ? 'crear' : 'actualizar'}" 
              method="post" class="needs-validation" novalidate>
            
            <c:if test="${usuario != null}">
                <input type="hidden" name="id" value="${usuario.id}">
            </c:if>
            
            <div class="row mb-3">
                <div class="col-md-6">
                    <label class="form-label">Rol</label>
                    <select name="idRol" class="form-select" required>
                        <option value="1">Administrador</option>
                        <option value="2">Usuario</option>
                    </select>
                </div>
            </div>
            
            <div class="row mb-3">
                <div class="col-md-6">
                    <label class="form-label">Nombre</label>
                    <input type="text" name="nombre" value="${usuario.nombre}" class="form-control" required>
                </div>
                <div class="col-md-6">
                    <label class="form-label">Apellido</label>
                    <input type="text" name="apellido" value="${usuario.apellido}" class="form-control" required>
                </div>
            </div>
            
            <div class="row mb-3">
                <div class="col-md-6">
                    <label class="form-label">Tipo de Documento</label>
                    <select name="tipoDocumento" class="form-select" required>
                        <option value="CC" ${usuario.tipoDocumento == 'CC' ? 'selected' : ''}>Cédula de Ciudadanía</option>
                        <option value="CE" ${usuario.tipoDocumento == 'CE' ? 'selected' : ''}>Cédula de Extranjería</option>
                        <option value="TI" ${usuario.tipoDocumento == 'TI' ? 'selected' : ''}>Tarjeta de Identidad</option>
                    </select>
                </div>
                <div class="col-md-6">
                    <label class="form-label">Número de Documento</label>
                    <input type="number" name="numeroDocumento" value="${usuario.numeroDocumento}" class="form-control" required>
                </div>
            </div>
            
            <div class="row mb-3">
                <div class="col-md-6">
                    <label class="form-label">Fecha de Nacimiento</label>
                    <input type="date" name="fechaNacimiento" 
                           value="<fmt:formatDate value='${usuario.fechaNacimiento}' pattern='yyyy-MM-dd'/>" 
                           class="form-control" required>
                </div>
                <div class="col-md-6">
                    <label class="form-label">Correo Electrónico</label>
                    <input type="email" name="correo" value="${usuario.correo}" class="form-control" required>
                </div>
            </div>
            
            <div class="row mb-3">
                <div class="col-md-6">
                    <label class="form-label">Teléfono</label>
                    <input type="tel" name="numeroTelefono" value="${usuario.numeroTelefono}" class="form-control" required>
                </div>
                <div class="col-md-6">
                    <label class="form-label">Dirección</label>
                    <input type="text" name="direccionResidencia" value="${usuario.direccionResidencia}" class="form-control" required>
                </div>
            </div>
            
            <div class="row mb-3">
                <div class="col-md-6">
                    <label class="form-label">Municipio</label>
                    <input type="text" name="municipioResidencia" value="${usuario.municipioResidencia}" class="form-control" required>
                </div>
                <div class="col-md-6">
                    <label class="form-label">Departamento</label>
                    <input type="text" name="departamentoResidencia" value="${usuario.departamentoResidencia}" class="form-control" required>
                </div>
            </div>
            
            <div class="row mb-3">
                <div class="col-md-6">
                    <label class="form-label">Género</label>
                    <select name="genero" class="form-select" required>
                        <option value="M" ${usuario.genero == 'M' ? 'selected' : ''}>Masculino</option>
                        <option value="F" ${usuario.genero == 'F' ? 'selected' : ''}>Femenino</option>
                        <option value="O" ${usuario.genero == 'O' ? 'selected' : ''}>Otro</option>
                    </select>
                </div>
                <div class="col-md-6">
                    <label class="form-label">Tipo de RH</label>
                    <select name="tipoRh" class="form-select" required>
                        <option value="O+" ${usuario.tipoRh == 'O+' ? 'selected' : ''}>O+</option>
                        <option value="O-" ${usuario.tipoRh == 'O-' ? 'selected' : ''}>O-</option>
                        <option value="A+" ${usuario.tipoRh == 'A+' ? 'selected' : ''}>A+</option>
                        <option value="A-" ${usuario.tipoRh == 'A-' ? 'selected' : ''}>A-</option>
                        <option value="B+" ${usuario.tipoRh == 'B+' ? 'selected' : ''}>B+</option>
                        <option value="B-" ${usuario.tipoRh == 'B-' ? 'selected' : ''}>B-</option>
                        <option value="AB+" ${usuario.tipoRh == 'AB+' ? 'selected' : ''}>AB+</option>
                        <option value="AB-" ${usuario.tipoRh == 'AB-' ? 'selected' : ''}>AB-</option>
                    </select>
                </div>
            </div>
            
            <div class="mb-3">
                <button type="submit" class="btn btn-primary">Guardar</button>
                <a href="${pageContext.request.contextPath}/usuarios" class="btn btn-secondary">Cancelar</a>
            </div>
        </form>
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
