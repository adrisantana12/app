package com.virtualpharmacy.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Modelo que representa un rol en el sistema.
 * Los roles son utilizados para control de acceso y permisos.
 */
public class Roles implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String nombre;
    private String descripcion;
    private int nivelAcceso;
    private Timestamp fechaCreacion;
    private Timestamp fechaModificacion;
    private boolean estaActivo;

    /**
     * Constructor por defecto
     */
    public Roles() {
    }

    /**
     * Constructor con todos los campos
     * 
     * @param id     Identificador único del rol
     * @param nombre Nombre descriptivo del rol
     */
    public Roles(int id, String nombre) {
        this.id = id;
        setNombre(nombre);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del rol, eliminando espacios en blanco al inicio y final
     * 
     * @param nombre el nombre del rol a establecer
     */
    public void setNombre(String nombre) {
        if (nombre != null) {
            nombre = nombre.trim();
        }
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getNivelAcceso() {
        return nivelAcceso;
    }

    public void setNivelAcceso(int nivelAcceso) {
        this.nivelAcceso = nivelAcceso;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Timestamp getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Timestamp fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public boolean isEstaActivo() {
        return estaActivo;
    }

    public void setEstaActivo(boolean estaActivo) {
        this.estaActivo = estaActivo;
    }

    @Override
    public String toString() {
        return "Roles{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", nivelAcceso=" + nivelAcceso +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaModificacion=" + fechaModificacion +
                ", estaActivo=" + estaActivo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Roles roles = (Roles) o;
        return id == roles.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    /**
     * Valida si el rol es válido para ser guardado
     * 
     * @return true si el rol es válido, false en caso contrario
     */
    public boolean isValid() {
        return nombre != null &&
                nombre.trim().length() >= 3 &&
                nombre.trim().length() <= 500;
    }

    /**
     * Obtiene el mensaje de error de validación si el rol no es válido
     * 
     * @return mensaje de error o null si el rol es válido
     */
    public String getValidationError() {
        if (nombre == null || nombre.trim().isEmpty()) {
            return "El nombre del rol no puede estar vacío";
        }
        if (nombre.trim().length() < 3) {
            return "El nombre del rol debe tener al menos 3 caracteres";
        }
        if (nombre.trim().length() > 500) {
            return "El nombre del rol no puede exceder los 500 caracteres";
        }
        return null;
    }
}
