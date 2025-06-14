<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${inventario == null ? 'Nuevo' : 'Editar'} Medicamento - Virtual Pharmacy</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.1/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <div class="row mb-3">
            <div class="col">
                <h2><i class="bi bi-capsule"></i> ${inventario == null ? 'Nuevo' : 'Editar'} Medicamento</h2>
            </div>
        </div>

        <form action="${pageContext.request.contextPath}/inventario/${inventario == null ? 'crear' : 'actualizar'}" 
              method="post" class="needs-validation" novalidate>
            
            <c:if test="${inventario != null}">
                <input type="hidden" name="id" value="${inventario.id}">
            </c:if>
            
            <div class="row mb-3">
                <div class="col-md-6">
                    <label class="form-label">Nombre del Medicamento</label>
                    <input type="text" name="nombreMedicamento" value="${inventario.nombreMedicamento}" 
                           class="form-control" required>
                </div>
                <div class="col-md-6">
                    <label class="form-label">Presentación</label>
                    <input type="text" name="presentacion" value="${inventario.presentacion}" 
                           class="form-control" required>
                </div>
            </div>
            
            <div class="row mb-3">
                <div class="col-md-4">
                    <label class="form-label">Stock Mínimo de Alerta</label>
                    <input type="number" name="cantidadMinAlerta" value="${inventario.cantidadMinAlerta}" 
                           class="form-control" required min="0">
                </div>
                <div class="col-md-4">
                    <label class="form-label">Stock Actual</label>
                    <input type="number" name="stock" value="${inventario.stock}" 
                           class="form-control" required min="0">
                </div>
                <div class="col-md-4">
                    <label class="form-label">Fecha de Vencimiento</label>
                    <input type="date" name="fechaVencimiento" 
                           value="<fmt:formatDate value='${inventario.fechaVencimiento}' pattern='yyyy-MM-dd'/>" 
                           class="form-control" required>
                </div>
            </div>
            
            <div class="row mb-3">
                <div class="col-md-6">
                    <label class="form-label">Vía de Administración</label>
                    <select name="viaAdministracion" class="form-select" required>
                        <option value="">Seleccione...</option>
                        <option value="Oral" ${inventario.viaAdministracion == 'Oral' ? 'selected' : ''}>Oral</option>
                        <option value="Intravenosa" ${inventario.viaAdministracion == 'Intravenosa' ? 'selected' : ''}>Intravenosa</option>
                        <option value="Intramuscular" ${inventario.viaAdministracion == 'Intramuscular' ? 'selected' : ''}>Intramuscular</option>
                        <option value="Tópica" ${inventario.viaAdministracion == 'Tópica' ? 'selected' : ''}>Tópica</option>
                        <option value="Sublingual" ${inventario.viaAdministracion == 'Sublingual' ? 'selected' : ''}>Sublingual</option>
                    </select>
                </div>
                <div class="col-md-6">
                    <label class="form-label">Almacenamiento</label>
                    <input type="text" name="almacenamiento" value="${inventario.almacenamiento}" 
                           class="form-control" required>
                </div>
            </div>
            
            <div class="row mb-3">
                <div class="col-md-12">
                    <label class="form-label">Especificaciones</label>
                    <textarea name="especificaciones" class="form-control" 
                              rows="3" required>${inventario.especificaciones}</textarea>
                </div>
            </div>
            
            <div class="row mb-3">
                <div class="col-md-4">
                    <label class="form-label">Precio</label>
                    <div class="input-group">
                        <span class="input-group-text">$</span>
                        <input type="number" name="precio" value="${inventario.precio}" 
                               class="form-control" required min="0">
                    </div>
                </div>
                <div class="col-md-4">
                    <label class="form-label">Peso (gramos)</label>
                    <input type="number" name="peso" value="${inventario.peso}" 
                           class="form-control" required min="0">
                </div>
                <div class="col-md-4">
                    <label class="form-label">Categoría</label>
                    <select name="categoria" class="form-select" required>
                        <option value="">Seleccione...</option>
                        <option value="Analgésico" ${inventario.categoria == 'Analgésico' ? 'selected' : ''}>Analgésico</option>
                        <option value="Antibiótico" ${inventario.categoria == 'Antibiótico' ? 'selected' : ''}>Antibiótico</option>
                        <option value="Antiinflamatorio" ${inventario.categoria == 'Antiinflamatorio' ? 'selected' : ''}>Antiinflamatorio</option>
                        <option value="Antialérgico" ${inventario.categoria == 'Antialérgico' ? 'selected' : ''}>Antialérgico</option>
                        <option value="Vitaminas" ${inventario.categoria == 'Vitaminas' ? 'selected' : ''}>Vitaminas</option>
                    </select>
                </div>
            </div>
            
            <input type="hidden" name="usuarioRegistra" value="${pageContext.request.userPrincipal.name}">
            
            <div class="mb-3">
                <button type="submit" class="btn btn-primary">
                    <i class="bi bi-save"></i> Guardar
                </button>
                <a href="${pageContext.request.contextPath}/inventario" class="btn btn-secondary">
                    <i class="bi bi-x-circle"></i> Cancelar
                </a>
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
