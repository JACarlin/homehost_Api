/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.frankcasa.services.controller;

import org.frankcasa.services.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.frankcasa.services.model.Departamento;
import org.frankcasa.services.model.Imagen;

public class ControllerUsuario {
    private ConnectionMysql connectionMysql;

    public ControllerUsuario() {
        this.connectionMysql = new ConnectionMysql();
    }

    public int login(String usuario, String password) {
        Connection conn = connectionMysql.open();
        String sql = "SELECT * FROM Usuario WHERE Usuario = ? AND Password = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, usuario);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("ID");
            }
            return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            connectionMysql.close();
        }
    }

    public boolean registrar(Usuario usuario) {
        Connection conn = connectionMysql.open();
        String sql = "INSERT INTO Usuario (Usuario, Password, NombreCompleto, Correo, Telefono, Rol, URLImagen) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, usuario.getUsuario());
            stmt.setString(2, usuario.getPassword());
            stmt.setString(3, usuario.getNombreCompleto());
            stmt.setString(4, usuario.getCorreo());
            stmt.setString(5, usuario.getTelefono());
            stmt.setString(6, usuario.getRol());
            stmt.setString(7, usuario.getUrlImagen());
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            connectionMysql.close();
        }
    }

    public Usuario obtenerPorId(int id) {
        Connection conn = connectionMysql.open();
        String sql = "SELECT * FROM Usuario WHERE ID = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("ID"));
                usuario.setUsuario(rs.getString("Usuario"));
                usuario.setPassword(rs.getString("Password"));
                usuario.setNombreCompleto(rs.getString("NombreCompleto"));
                usuario.setCorreo(rs.getString("Correo"));
                usuario.setTelefono(rs.getString("Telefono"));
                usuario.setRol(rs.getString("Rol"));
                usuario.setUrlImagen(rs.getString("URLImagen"));
                usuario.setDepartamentos(obtenerDepartamentosPorUsuarioID(usuario.getId()));
                return usuario;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            connectionMysql.close();
        }
    }
    private List<Departamento> obtenerDepartamentosPorUsuarioID(int usuarioID) {
        Connection conn = connectionMysql.open();
        String sql = "SELECT * FROM Departamento WHERE usuarioID = ?";
        List<Departamento> departamentos = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, usuarioID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Departamento departamento = new Departamento();
                departamento.setId(rs.getInt("ID"));
                departamento.setNombre(rs.getString("Nombre"));
                departamento.setUbicacion(rs.getString("Ubicacion"));
                departamento.setDescripcion(rs.getString("Descripcion"));
                departamento.setTarifa(rs.getDouble("Tarifa"));
                departamento.setCantidadPersonasMax(rs.getInt("CantidadPersonasMax"));
                departamento.setDisponibilidad(rs.getBoolean("Disponibilidad"));
                departamento.setUsuarioID(rs.getInt("UsuarioID"));
                departamento.setImagenes(obtenerImagenesPorDepartamentoId(departamento.getId()));
                departamentos.add(departamento);
            }
            return departamentos;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            connectionMysql.close();
        }
    }
        private List<Imagen> obtenerImagenesPorDepartamentoId(int departamentoId) {
        Connection conn = connectionMysql.open();
        String sql = "SELECT * FROM Imagen WHERE DepartamentoID = ?";
        List<Imagen> imagenes = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, departamentoId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Imagen imagen = new Imagen();
                imagen.setId(rs.getInt("ID"));
                imagen.setUrlImagen(rs.getString("URLImagen"));
                imagen.setDepartamentoID(rs.getInt("DepartamentoID"));
                imagenes.add(imagen);
            }
            return imagenes;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            connectionMysql.close();
        }
    }

    public boolean actualizar(Usuario usuario) {
        Connection conn = connectionMysql.open();
        String sql = "UPDATE Usuario SET Usuario = ?, Password = ?, NombreCompleto = ?, Correo = ?, Telefono = ?, Rol = ?, URLImagen = ? WHERE ID = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, usuario.getUsuario());
            stmt.setString(2, usuario.getPassword());
            stmt.setString(3, usuario.getNombreCompleto());
            stmt.setString(4, usuario.getCorreo());
            stmt.setString(5, usuario.getTelefono());
            stmt.setString(6, usuario.getRol());
            stmt.setString(7, usuario.getUrlImagen());
            stmt.setInt(8, usuario.getId());
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            connectionMysql.close();
        }
    }
}

