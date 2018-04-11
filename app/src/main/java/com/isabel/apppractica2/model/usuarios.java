package com.isabel.apppractica2.model;

/**
 * Created by Isabel on 9/04/2018.
 */

public class usuarios {

    String id, nombre, correo, foto;
    //hay que tener un constructor vacio porque firebase lo exige

    //click der-generate-constructor-none


    public usuarios() {
    }

       //en el orden que aparece, se van a inicializar
    public usuarios(String id, String nombre, String correo, String foto) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.foto = foto;
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
