package com.manuel.rosaurazapatainv.Modelos;

import java.io.Serializable;

public class ActivoFijo implements Serializable {
    private String id, numero, clave, descripcion, cantidad, precio, total, estado;

    public ActivoFijo(String id, String numero, String clave, String descripcion, String cantidad, String precio, String total, String estado) {
        this.id = id;
        this.numero = numero;
        this.clave = clave;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precio = precio;
        this.total = total;
        this.estado = estado;
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

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
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

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
