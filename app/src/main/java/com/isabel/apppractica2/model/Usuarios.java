package com.isabel.apppractica2.model;

/**
 * Created by Isabel on 29/04/2018.
 */

public class Usuarios {
    String id_usuario;
    String correo;

    public Usuarios(String id_usuario, String correo) {
        this.id_usuario = id_usuario;
        this.correo = correo;
    }

    public Usuarios() {
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
