<%-- Generar token CSRF si no existe --%> <% if
(session.getAttribute("csrf_token") == null) { String token =
com.virtualpharmacy.util.SecurityUtil.generateSecureToken(32);
session.setAttribute("csrf_token", token); } %> <%-- Meta tags de seguridad --%>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="robots" content="noindex, nofollow" />
<meta name="referrer" content="same-origin" />

<%-- Token CSRF para formularios y peticiones AJAX --%>
<meta name="_csrf" content="${sessionScope.csrf_token}" />

<%-- Scripts de seguridad --%>
<script>
  // Configurar token CSRF para peticiones AJAX
  $(document).ready(function () {
    var token = $("meta[name='_csrf']").attr("content");

    $.ajaxSetup({
      beforeSend: function (xhr) {
        xhr.setRequestHeader("X-CSRF-TOKEN", token);
      },
    });

    // Agregar token CSRF a todos los formularios
    $("form").each(function () {
      var input = $("<input>")
        .attr("type", "hidden")
        .attr("name", "_csrf")
        .val(token);
      $(this).append(input);
    });
  });

  // Prevenir XSS en campos de formulario
  function sanitizeInput(input) {
    return input.replace(/[<>]/g, function (match) {
      return match === "<" ? "&lt;" : "&gt;";
    });
  }

  // Validar contraseñas
  function validarContrasenia(password) {
    var mensajeError = "";
    if (password.length < 8) {
      mensajeError = "La contraseña debe tener al menos 8 caracteres";
    } else if (!/[A-Z]/.test(password)) {
      mensajeError = "La contraseña debe contener al menos una letra mayúscula";
    } else if (!/[a-z]/.test(password)) {
      mensajeError = "La contraseña debe contener al menos una letra minúscula";
    } else if (!/\d/.test(password)) {
      mensajeError = "La contraseña debe contener al menos un número";
    } else if (!/[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/.test(password)) {
      mensajeError =
        "La contraseña debe contener al menos un carácter especial";
    }
    return mensajeError;
  }

  // Detectar inactividad
  var inactivityTimeout;
  function resetInactivityTimer() {
    clearTimeout(inactivityTimeout);
    inactivityTimeout = setTimeout(function () {
      window.location.href =
        "${pageContext.request.contextPath}/logout?reason=inactivity";
    }, 30 * 60 * 1000); // 30 minutos
  }
  document.addEventListener("mousemove", resetInactivityTimer);
  document.addEventListener("keypress", resetInactivityTimer);
  resetInactivityTimer();
</script>

<%-- Estilos de seguridad --%>
<style>
  /* Ocultar elementos con información sensible */
  .secure-hidden {
    -webkit-text-security: disc;
    text-security: disc;
  }

  /* Estilo para campos inválidos */
  input:invalid {
    border-color: red;
    box-shadow: 0 0 5px red;
  }

  /* Indicador de fortaleza de contraseña */
  .password-strength {
    height: 5px;
    margin-top: 5px;
    background: #ddd;
  }
  .password-strength-bar {
    height: 100%;
    width: 0;
    transition: width 0.3s ease-in-out;
  }
  .strength-weak {
    background: #ff4444;
    width: 33%;
  }
  .strength-medium {
    background: #ffbb33;
    width: 66%;
  }
  .strength-strong {
    background: #00c851;
    width: 100%;
  }
</style>
