/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.frankcasa.services.model;
import java.util.List;

public class Departamento {
    private int id;
    private String nombre;
    private String ubicacion;
    private String descripcion;
    private double tarifa;
    private int cantidadPersonasMax;
    private boolean disponibilidad;
    private int usuarioID;
    private List<Imagen> imagenes;
    private List<Reservacion> reservaciones;

    public Departamento() {
    }

    public Departamento(int id, String nombre, String ubicacion, String descripcion, double tarifa, int cantidadPersonasMax, boolean disponibilidad, int usuarioID, List<Imagen> imagenes, List<Reservacion> reservaciones) {
        this.id = id;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.descripcion = descripcion;
        this.tarifa = tarifa;
        this.cantidadPersonasMax = cantidadPersonasMax;
        this.disponibilidad = disponibilidad;
        this.usuarioID = usuarioID;
        this.imagenes = imagenes;
        this.reservaciones = reservaciones;
    }  

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getTarifa() {
        return tarifa;
    }

    public void setTarifa(double tarifa) {
        this.tarifa = tarifa;
    }

    public int getCantidadPersonasMax() {
        return cantidadPersonasMax;
    }

    public void setCantidadPersonasMax(int cantidadPersonasMax) {
        this.cantidadPersonasMax = cantidadPersonasMax;
    }

    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public int getUsuarioID() {
        return usuarioID;
    }

    public void setUsuarioID(int usuarioID) {
        this.usuarioID = usuarioID;
    }
    
    public List<Imagen> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<Imagen> imagenes) {
        this.imagenes = imagenes;
    }

    public List<Reservacion> getReservaciones() {
        return reservaciones;
    }

    public void setReservaciones(List<Reservacion> reservaciones) {
        this.reservaciones = reservaciones;
    }
}
