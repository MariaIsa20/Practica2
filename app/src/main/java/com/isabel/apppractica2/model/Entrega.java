package com.isabel.apppractica2.model;

/**
 * Created by Isabel on 29/05/2018.
 */

public class Entrega {
    String id_pedido;
    String id_usuario;
    int valor;
    int cantidad;
    String id_mesa;
    String nombre;
    String estado;

    public Entrega() {
    }

    public Entrega(String id_pedido, String id_usuario, int valor, int cantidad, String id_mesa, String nombre, String estado) {
        this.id_pedido = id_pedido;
        this.id_usuario = id_usuario;
        this.valor = valor;
        this.cantidad = cantidad;
        this.id_mesa = id_mesa;
        this.nombre = nombre;
        this.estado = estado;
    }

    public String getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(String id_pedido) {
        this.id_pedido = id_pedido;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getId_mesa() {
        return id_mesa;
    }

    public void setId_mesa(String id_mesa) {
        this.id_mesa = id_mesa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
