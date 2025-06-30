/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.frankcasa.services.model;

public class Imagen {
    private int id;
    private String URLImagen;
    private int departamentoID;

    public Imagen() {
    }

    public Imagen(int id, String urlImagen, int departamentoID) {
        this.id = id;
        this.URLImagen = urlImagen;
        this.departamentoID = departamentoID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrlImagen() {
        return URLImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.URLImagen = urlImagen;
    }

    public int getDepartamentoID() {
        return departamentoID;
    }

    public void setDepartamentoID(int departamentoID) {
        this.departamentoID = departamentoID;
    }
    
}
