package com.virtualpharmacy.model;

public class Credenciales {
    private int id;
    private int idUsuario;
    private String contrasenia;
    private String usuario;

    public Credenciales() {
    }

    public Credenciales(int id, int idUsuario, String contrasenia, String usuario) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.contrasenia = contrasenia;
        this.usuario = usuario;
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

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
