package com.example.crudservice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListaEmpleados {

    @SerializedName("clave")
    @Expose
    private Integer  clave;

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("sueldo")
    @Expose
    private String sueldo;

    public ListaEmpleados(Integer clave, String nombre, String sueldo) {
        this.clave = clave;
        this.nombre = nombre;
        this.sueldo = sueldo;
    }

    public Integer  getclave() {
        return clave;
    }

    public String getNombre() {
        return nombre;
    }

    public String getSueldo() {
        return sueldo;
    }

    public void setclave(Integer  id) {
        this.clave = clave;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setSueldo(String sueldo) {
        this.sueldo = sueldo;
    }

    @Override
    public String toString() {
        return "Post{" +
                "clave='" + clave + '\'' +
                ", nombre='" + nombre + '\'' +
                ", sueldo=" + sueldo +
                '}';
    }
}
