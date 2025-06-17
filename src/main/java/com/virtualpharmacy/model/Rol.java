package com.virtualpharmacy.model;

import java.sql.Timestamp;

public class Rol {
    private int id;
    private String nombre;
    private String descripcion;
    private int nivelAcceso;
    private Timestamp fechaCreacion;
    private Timestamp fechaModificacion;
    private boolean estaActivo;

    public Rol() {
    }

    public Rol(int id, String nombre, String descripcion, int nivelAcceso,
            Timestamp fechaCreacion, Timestamp fechaModificacion, boolean estaActivo) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.nivelAcceso = nivelAcceso;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
        this.estaActivo = estaActivo;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
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
}
