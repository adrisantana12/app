package com.virtualpharmacy.model;

import java.util.Date;

public class Inventario {
    private int id;
    private String nombreMedicamento;
    private String presentacion;
    private int cantidadMinAlerta;
    private int stock;
    private Date fechaIngreso;
    private Date fechaVencimiento;
    private String viaAdministracion;
    private String almacenamiento;
    private String especificaciones;
    private int precio;
    private int peso;
    private String categoria;
    private String estaInactivo;
    private String usuarioRegistra;

    public Inventario() {
    }

    public Inventario(int id, String nombreMedicamento, String presentacion, int cantidadMinAlerta,
            int stock, Date fechaIngreso, Date fechaVencimiento, String viaAdministracion,
            String almacenamiento, String especificaciones, int precio, int peso,
            String categoria, String estaInactivo, String usuarioRegistra) {
        this.id = id;
        this.nombreMedicamento = nombreMedicamento;
        this.presentacion = presentacion;
        this.cantidadMinAlerta = cantidadMinAlerta;
        this.stock = stock;
        this.fechaIngreso = fechaIngreso;
        this.fechaVencimiento = fechaVencimiento;
        this.viaAdministracion = viaAdministracion;
        this.almacenamiento = almacenamiento;
        this.especificaciones = especificaciones;
        this.precio = precio;
        this.peso = peso;
        this.categoria = categoria;
        this.estaInactivo = estaInactivo;
        this.usuarioRegistra = usuarioRegistra;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreMedicamento() {
        return nombreMedicamento;
    }

    public void setNombreMedicamento(String nombreMedicamento) {
        this.nombreMedicamento = nombreMedicamento;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public int getCantidadMinAlerta() {
        return cantidadMinAlerta;
    }

    public void setCantidadMinAlerta(int cantidadMinAlerta) {
        this.cantidadMinAlerta = cantidadMinAlerta;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getViaAdministracion() {
        return viaAdministracion;
    }

    public void setViaAdministracion(String viaAdministracion) {
        this.viaAdministracion = viaAdministracion;
    }

    public String getAlmacenamiento() {
        return almacenamiento;
    }

    public void setAlmacenamiento(String almacenamiento) {
        this.almacenamiento = almacenamiento;
    }

    public String getEspecificaciones() {
        return especificaciones;
    }

    public void setEspecificaciones(String especificaciones) {
        this.especificaciones = especificaciones;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getEstaInactivo() {
        return estaInactivo;
    }

    public void setEstaInactivo(String estaInactivo) {
        this.estaInactivo = estaInactivo;
    }

    public String getUsuarioRegistra() {
        return usuarioRegistra;
    }

    public void setUsuarioRegistra(String usuarioRegistra) {
        this.usuarioRegistra = usuarioRegistra;
    }
}
