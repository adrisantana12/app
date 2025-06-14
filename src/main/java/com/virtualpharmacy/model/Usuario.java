package com.virtualpharmacy.model;

import java.util.Date;

public class Usuario {
    private int id;
    private int idRol;
    private Date fechaIngreso;
    private String nombre;
    private String apellido;
    private String tipoDocumento;
    private int numeroDocumento;
    private Date fechaNacimiento;
    private String correo;
    private String numeroTelefono;
    private String direccionResidencia;
    private String municipioResidencia;
    private String departamentoResidencia;
    private String genero;
    private String tipoRh;

    public Usuario() {
    }

    // Constructor con todos los campos
    public Usuario(int id, int idRol, Date fechaIngreso, String nombre, String apellido,
            String tipoDocumento, int numeroDocumento, Date fechaNacimiento,
            String correo, String numeroTelefono, String direccionResidencia,
            String municipioResidencia, String departamentoResidencia,
            String genero, String tipoRh) {
        this.id = id;
        this.idRol = idRol;
        this.fechaIngreso = fechaIngreso;
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.fechaNacimiento = fechaNacimiento;
        this.correo = correo;
        this.numeroTelefono = numeroTelefono;
        this.direccionResidencia = direccionResidencia;
        this.municipioResidencia = municipioResidencia;
        this.departamentoResidencia = departamentoResidencia;
        this.genero = genero;
        this.tipoRh = tipoRh;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public int getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(int numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public String getDireccionResidencia() {
        return direccionResidencia;
    }

    public void setDireccionResidencia(String direccionResidencia) {
        this.direccionResidencia = direccionResidencia;
    }

    public String getMunicipioResidencia() {
        return municipioResidencia;
    }

    public void setMunicipioResidencia(String municipioResidencia) {
        this.municipioResidencia = municipioResidencia;
    }

    public String getDepartamentoResidencia() {
        return departamentoResidencia;
    }

    public void setDepartamentoResidencia(String departamentoResidencia) {
        this.departamentoResidencia = departamentoResidencia;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getTipoRh() {
        return tipoRh;
    }

    public void setTipoRh(String tipoRh) {
        this.tipoRh = tipoRh;
    }
}
