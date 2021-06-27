package com.manuel.rosaurazapatainv.Modelos;

import java.io.Serializable;

public class MaterialConsumo implements Serializable {
    private String id, numero, descripcion, cantidad;

    public MaterialConsumo(String id, String numero, String descripcion, String cantidad) {
        this.id = id;
        this.numero = numero;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }
}
