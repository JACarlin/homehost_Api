/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.frankcasa.services.controller;

import org.frankcasa.services.model.Reservacion;
import org.frankcasa.services.model.Departamento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.frankcasa.services.model.Imagen;

public class ControllerReservacion {
    private ConnectionMysql connectionMysql;

    public ControllerReservacion() {
        this.connectionMysql = new ConnectionMysql();
    }

    public boolean crearReservacion(Reservacion reservacion) {
        Connection conn = connectionMysql.open();
        String sql = "INSERT INTO Reservacion (FechaInicio, FechaFin, Costo, Estado, CodigoEntrada, UsuarioID, DepartamentoID) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDate(1, new java.sql.Date(reservacion.getFechaInicio().getTime()));
            stmt.setDate(2, new java.sql.Date(reservacion.getFechaFin().getTime()));
            stmt.setDouble(3, reservacion.getCosto());
            stmt.setString(4, reservacion.getEstado());
            stmt.setString(5, reservacion.getCodigoEntrada());
            stmt.setInt(6, reservacion.getUsuarioID());
            stmt.setInt(7, reservacion.getDepartamentoID());
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            connectionMysql.close();
        }
    }

    public List<Reservacion> obtenerReservacionesPorUsuario(int usuarioID) {
        Connection conn = connectionMysql.open();
        String sql = "SELECT * FROM Reservacion WHERE UsuarioID = ?";
        List<Reservacion> reservaciones = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, usuarioID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Reservacion reservacion = new Reservacion();
                reservacion.setId(rs.getInt("ID"));
                reservacion.setFechaInicio(rs.getDate("FechaInicio"));
                reservacion.setFechaFin(rs.getDate("FechaFin"));
                reservacion.setCosto(rs.getDouble("Costo"));
                reservacion.setEstado(rs.getString("Estado"));
                reservacion.setCodigoEntrada(rs.getString("CodigoEntrada"));
                reservacion.setUsuarioID(rs.getInt("UsuarioID"));
                reservacion.setDepartamentoID(rs.getInt("DepartamentoID"));
                reservacion.setDepartamento(obtenerDepartamentoPorId(rs.getInt("DepartamentoID")));
                reservaciones.add(reservacion);
            }
            return reservaciones;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            connectionMysql.close();
        }
    }

    private Departamento obtenerDepartamentoPorId(int departamentoID) {
        Connection conn = connectionMysql.open();
        String sql = "SELECT * FROM Departamento WHERE ID = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, departamentoID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
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
                return departamento;
            }
            return null;
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
}

