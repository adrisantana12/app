<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/core"
prefix="c" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Login - Virtual Pharmacy</title>
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
      rel="stylesheet"
    />
  </head>
  <body>
    <div class="container mt-5">
      <div class="row">
        <div class="col-md-6 offset-md-3">
          <div class="card">
            <div class="card-header">
              <h3 class="text-center">Virtual Pharmacy</h3>
            </div>
            <div class="card-body">
              <c:if test="${param.error != null}">
                <div class="alert alert-danger" role="alert">
                  Usuario o contraseña incorrectos
                </div>
              </c:if>
              <form action="j_security_check" method="POST">
                <div class="mb-3">
                  <label for="j_username" class="form-label">Usuario</label>
                  <input
                    type="text"
                    class="form-control"
                    id="j_username"
                    name="j_username"
                    required
                    autofocus
                  />
                </div>
                <div class="mb-3">
                  <label for="j_password" class="form-label">Contraseña</label>
                  <input
                    type="password"
                    class="form-control"
                    id="j_password"
                    name="j_password"
                    required
                  />
                </div>
                <div class="d-grid">
                  <button type="submit" class="btn btn-primary">
                    Ingresar
                  </button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>
