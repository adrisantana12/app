package com.virtualpharmacy.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.Timestamp;
import java.time.Instant;

public class RolTest {
    private Rol rol;
    private final Timestamp ahora = Timestamp.from(Instant.now());

    @BeforeEach
    void setUp() {
        rol = new Rol();
    }

    @Test
    void testConstructorVacio() {
        assertNotNull(rol, "El constructor vacío debería crear una instancia");
    }

    @Test
    void testConstructorCompleto() {
        Rol rolCompleto = new Rol(1, "Admin", "Administrador del sistema", 10, ahora, ahora, true);

        assertEquals(1, rolCompleto.getId());
        assertEquals("Admin", rolCompleto.getNombre());
        assertEquals("Administrador del sistema", rolCompleto.getDescripcion());
        assertEquals(10, rolCompleto.getNivelAcceso());
        assertEquals(ahora, rolCompleto.getFechaCreacion());
        assertEquals(ahora, rolCompleto.getFechaModificacion());
        assertTrue(rolCompleto.isEstaActivo());
    }

    @Test
    void testSetNombre() {
        String nombre = "Supervisor";
        rol.setNombre(nombre);
        assertEquals(nombre, rol.getNombre());
    }

    @Test
    void testSetNombreNull() {
        assertThrows(IllegalArgumentException.class, () -> rol.setNombre(null));
    }

    @Test
    void testSetNombreVacio() {
        assertThrows(IllegalArgumentException.class, () -> rol.setNombre(""));
    }

    @Test
    void testSetNivelAcceso() {
        rol.setNivelAcceso(5);
        assertEquals(5, rol.getNivelAcceso());
    }

    @Test
    void testSetNivelAccesoInvalido() {
        assertThrows(IllegalArgumentException.class, () -> rol.setNivelAcceso(11));
        assertThrows(IllegalArgumentException.class, () -> rol.setNivelAcceso(0));
    }

    @Test
    void testSetDescripcion() {
        String descripcion = "Descripción de prueba";
        rol.setDescripcion(descripcion);
        assertEquals(descripcion, rol.getDescripcion());
    }

    @Test
    void testSetEstaActivo() {
        rol.setEstaActivo(true);
        assertTrue(rol.isEstaActivo());

        rol.setEstaActivo(false);
        assertFalse(rol.isEstaActivo());
    }

    @Test
    void testSetFechaCreacion() {
        rol.setFechaCreacion(ahora);
        assertEquals(ahora, rol.getFechaCreacion());
    }

    @Test
    void testSetFechaModificacion() {
        rol.setFechaModificacion(ahora);
        assertEquals(ahora, rol.getFechaModificacion());
    }
}
