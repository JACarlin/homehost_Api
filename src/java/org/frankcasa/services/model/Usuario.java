/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.frankcasa.services.model;

import java.util.List;

public class Usuario {
    private int id;
    private String usuario;
    private String password;
    private String nombreCompleto;
    private String correo;
    private String telefono;
    private String rol;
    private String URLImagen;
    private List<Departamento> departamentos;

    public Usuario() {
    }

    public Usuario(int id, String usuario, String password, String nombreCompleto, String correo, String telefono, String rol, String urlImagen, List<Departamento> departamentos) {
        this.id = id;
        this.usuario = usuario;
        this.password = password;
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.telefono = telefono;
        this.rol = rol;
        this.URLImagen = urlImagen;
        this.departamentos = departamentos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getUrlImagen() {
        return URLImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.URLImagen = urlImagen;
    }

    public List<Departamento> getDepartamentos() {
        return departamentos;
    }

    public void setDepartamentos(List<Departamento> departamentos) {
        this.departamentos = departamentos;
    }
    
}
