package com.virtualpharmacy.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Modelo que representa un registro de auditoría en el sistema.
 * Almacena información sobre acciones realizadas en el sistema, incluyendo
 * quién realizó la acción, cuándo, sobre qué tabla y registro, y desde qué IP.
 */
public class Auditoria implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String tabla;
    private String accion;
    private int idRegistro;
    private String detalles;
    private int idUsuario;
    private Date fechaHora;
    private String ipAddress;

    /**
     * Constructor por defecto
     */
    public Auditoria() {
        this.fechaHora = new Date();
    }

    /**
     * Constructor con todos los campos excepto ID
     */
    public Auditoria(String tabla, String accion, int idRegistro, String detalles,
            int idUsuario, Date fechaHora, String ipAddress) {
        this.tabla = tabla;
        this.accion = accion;
        this.idRegistro = idRegistro;
        this.detalles = detalles;
        this.idUsuario = idUsuario;
        this.fechaHora = fechaHora != null ? new Date(fechaHora.getTime()) : new Date();
        this.ipAddress = ipAddress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla != null ? tabla.trim() : null;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion != null ? accion.trim().toUpperCase() : null;
    }

    public int getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(int idRegistro) {
        this.idRegistro = idRegistro;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles != null ? detalles.trim() : null;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Date getFechaHora() {
        return fechaHora != null ? new Date(fechaHora.getTime()) : null;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora != null ? new Date(fechaHora.getTime()) : null;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress != null ? ipAddress.trim() : null;
    }

    @Override
    public String toString() {
        return "Auditoria{" +
                "id=" + id +
                ", tabla='" + tabla + '\'' +
                ", accion='" + accion + '\'' +
                ", idRegistro=" + idRegistro +
                ", detalles='" + detalles + '\'' +
                ", idUsuario=" + idUsuario +
                ", fechaHora=" + fechaHora +
                ", ipAddress='" + ipAddress + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Auditoria))
            return false;
        Auditoria auditoria = (Auditoria) o;
        return id == auditoria.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    /**
     * Valida si el registro de auditoría es válido para ser guardado
     *
     * @return true si es válido, false en caso contrario
     */
    public boolean isValid() {
        return tabla != null && !tabla.trim().isEmpty() &&
                accion != null && !accion.trim().isEmpty() &&
                detalles != null && !detalles.trim().isEmpty() &&
                fechaHora != null;
    }

    /**
     * Obtiene el mensaje de error de validación si el registro no es válido
     *
     * @return mensaje de error o null si el registro es válido
     */
    public String getValidationError() {
        if (tabla == null || tabla.trim().isEmpty()) {
            return "La tabla es obligatoria";
        }
        if (accion == null || accion.trim().isEmpty()) {
            return "La acción es obligatoria";
        }
        if (detalles == null || detalles.trim().isEmpty()) {
            return "Los detalles son obligatorios";
        }
        if (fechaHora == null) {
            return "La fecha y hora son obligatorias";
        }
        return null;
    }
}
