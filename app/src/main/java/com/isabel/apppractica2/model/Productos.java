package com.isabel.apppractica2.model;

/**
 * Created by Isabel on 6/05/2018.
 */

public class Productos {
    String id;
    String nombre;
    int valor;

    public Productos() {
    }

    public Productos(String id, String nombre, int valor) {
        this.id = id;
        this.nombre = nombre;
        this.valor = valor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
}
