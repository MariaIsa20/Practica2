package com.isabel.apppractica2.model;

/**
 * Created by Isabel on 29/04/2018.
 */

public class Usuarios {
    String id;
    String correo;

    public Usuarios() {
    }

    public Usuarios(String id, String correo) {
        this.id = id;
        this.correo = correo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
