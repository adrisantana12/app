package com.virtualpharmacy.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Utilidad para manejar operaciones de seguridad como encriptación y validación
 * de contraseñas.
 */
public class SecurityUtil {
    private static final Logger LOGGER = Logger.getLogger(SecurityUtil.class.getName());
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final String SESSION_USER_KEY = "usuario_actual";
    private static final String SESSION_ROLE_KEY = "rol_actual";
    private static final int MAX_INTENTOS_LOGIN = 3;
    private static final String SESSION_INTENTOS_KEY = "intentos_login";
    private static final String SALT = "VirtualPharmacy2025";

    private SecurityUtil() {
        // Constructor privado para evitar instanciación
    }

    /**
     * Encripta una contraseña usando SHA-256 con salt
     * 
     * @param password la contraseña a encriptar
     * @return la contraseña encriptada
     */
    public static String encryptPassword(String password) {
        try {
            // Generar salt aleatorio
            byte[] salt = new byte[16];
            SECURE_RANDOM.nextBytes(salt);

            // Crear hash con SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes());

            // Combinar salt y hash
            byte[] combined = new byte[salt.length + hashedPassword.length];
            System.arraycopy(salt, 0, combined, 0, salt.length);
            System.arraycopy(hashedPassword, 0, combined, salt.length, hashedPassword.length);

            // Convertir a String en base64
            return Base64.getEncoder().encodeToString(combined);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.log(Level.SEVERE, "Error al encriptar la contraseña", e);
            throw new SecurityException("Error al encriptar la contraseña", e);
        }
    }

    /**
     * Verifica si una contraseña coincide con su hash
     * 
     * @param password   la contraseña a verificar
     * @param storedHash el hash de la contraseña almacenada
     * @return true si la contraseña coincide, false en caso contrario
     */
    public static boolean verifyPassword(String password, String storedHash) {
        try {
            // Decodificar el hash almacenado
            byte[] combined = Base64.getDecoder().decode(storedHash);

            // Extraer salt y hash
            byte[] salt = new byte[16];
            byte[] hash = new byte[combined.length - 16];
            System.arraycopy(combined, 0, salt, 0, 16);
            System.arraycopy(combined, 16, hash, 0, hash.length);

            // Crear nuevo hash con la contraseña proporcionada
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] newHash = md.digest(password.getBytes());

            // Comparar hashes
            return MessageDigest.isEqual(hash, newHash);
        } catch (NoSuchAlgorithmException | IllegalArgumentException e) {
            LOGGER.log(Level.SEVERE, "Error al verificar la contraseña", e);
            return false;
        }
    }

    /**
     * Genera un token seguro aleatorio
     * 
     * @param bytes el número de bytes aleatorios a usar
     * @return el token generado en formato Base64
     */
    public static String generateSecureToken(int bytes) {
        byte[] token = new byte[bytes];
        SECURE_RANDOM.nextBytes(token);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(token);
    }

    /**
     * Valida la fortaleza de una contraseña
     * 
     * @param password la contraseña a validar
     * @return null si la contraseña es válida, o un mensaje de error si no lo es
     */
    public static String validatePasswordStrength(String password) {
        if (password == null || password.length() < 8) {
            return "La contraseña debe tener al menos 8 caracteres";
        }
        if (!password.matches(".*[A-Z].*")) {
            return "La contraseña debe contener al menos una letra mayúscula";
        }
        if (!password.matches(".*[a-z].*")) {
            return "La contraseña debe contener al menos una letra minúscula";
        }
        if (!password.matches(".*\\d.*")) {
            return "La contraseña debe contener al menos un número";
        }
        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*")) {
            return "La contraseña debe contener al menos un carácter especial";
        }
        return null;
    }

    /**
     * Establece el usuario en la sesión
     * 
     * @param session la sesión HTTP
     * @param usuario el nombre de usuario
     * @param rol     el rol del usuario
     */
    public static void setSessionUser(HttpSession session, String usuario, String rol) {
        session.setAttribute(SESSION_USER_KEY, usuario);
        session.setAttribute(SESSION_ROLE_KEY, rol);
        session.setAttribute(SESSION_INTENTOS_KEY, 0);
        session.setMaxInactiveInterval(30 * 60); // 30 minutos de timeout
    }

    /**
     * Obtiene el usuario actual de la sesión
     * 
     * @param request la petición HTTP
     * @return el nombre de usuario o null si no hay sesión
     */
    public static String getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null ? (String) session.getAttribute(SESSION_USER_KEY) : null;
    }

    /**
     * Obtiene el rol actual de la sesión
     * 
     * @param request la petición HTTP
     * @return el rol del usuario o null si no hay sesión
     */
    public static String getCurrentRole(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null ? (String) session.getAttribute(SESSION_ROLE_KEY) : null;
    }

    /**
     * Incrementa el contador de intentos fallidos de login
     * 
     * @param session la sesión HTTP
     * @return true si se ha excedido el máximo de intentos
     */
    public static boolean incrementarIntentosFallidos(HttpSession session) {
        Integer intentos = (Integer) session.getAttribute(SESSION_INTENTOS_KEY);
        if (intentos == null) {
            intentos = 0;
        }
        intentos++;
        session.setAttribute(SESSION_INTENTOS_KEY, intentos);
        return intentos >= MAX_INTENTOS_LOGIN;
    }

    /**
     * Invalida la sesión actual
     * 
     * @param request la petición HTTP
     */
    public static void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    /**
     * Verifica si una ruta está permitida para el rol actual
     * 
     * @param request la petición HTTP
     * @param ruta    la ruta a verificar
     * @return true si la ruta está permitida, false en caso contrario
     */
    public static boolean isRouteAllowed(HttpServletRequest request, String ruta) {
        String rolActual = getCurrentRole(request);
        if (rolActual == null) {
            return false;
        }

        // Rutas públicas (accesibles sin autenticación)
        if (ruta.startsWith("/login") || ruta.startsWith("/resources/") || ruta.equals("/")) {
            return true;
        }

        // Rutas por rol
        switch (rolActual.toUpperCase()) {
            case "ADMINISTRADOR":
                return true; // El administrador tiene acceso a todo
            case "FARMACEUTICO":
                return ruta.startsWith("/inventario") ||
                        ruta.startsWith("/usuarios/perfil");
            case "AUXILIAR":
                return ruta.startsWith("/inventario/consultar") ||
                        ruta.startsWith("/usuarios/perfil");
            default:
                return false;
        }
    }

    /**
     * Hashea una contraseña usando SHA-256 y un salt estático
     * 
     * @param password la contraseña a hashear
     * @return el hash de la contraseña
     */
    public static String hashPassword(String password) {
        try {
            // Combinar contraseña con salt
            String passwordWithSalt = password + SALT;

            // Crear hash SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(passwordWithSalt.getBytes(StandardCharsets.UTF_8));

            // Convertir a hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al hashear la contraseña", e);
        }
    }
}
