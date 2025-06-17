package com.virtualpharmacy.model;

public class UsuariosInactivos {
    private int id;
    private int idUsuario;
    private String razon;
    private String descripcion;

    // Campos adicionales para la vista
    private String nombreCompletoUsuario;
    private Usuario usuario;

    public UsuariosInactivos() {
    }

    public UsuariosInactivos(int id, int idUsuario, String razon, String descripcion) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.razon = razon;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombreCompletoUsuario() {
        return nombreCompletoUsuario;
    }

    public void setNombreCompletoUsuario(String nombreCompletoUsuario) {
        this.nombreCompletoUsuario = nombreCompletoUsuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "UsuariosInactivos{" +
                "id=" + id +
                ", idUsuario=" + idUsuario +
                ", razon='" + razon + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", nombreCompletoUsuario='" + nombreCompletoUsuario + '\'' +
                '}';
    }
}
