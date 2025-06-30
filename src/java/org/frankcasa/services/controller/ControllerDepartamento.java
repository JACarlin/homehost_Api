/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.frankcasa.services.controller;

import org.frankcasa.services.model.Departamento;
import org.frankcasa.services.model.Imagen;
import org.frankcasa.services.model.Reservacion;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ControllerDepartamento {
    private ConnectionMysql connectionMysql;

    public ControllerDepartamento() {
        this.connectionMysql = new ConnectionMysql();
    }

    public List<Departamento> obtenertodosdisponibles() {
        Connection conn = connectionMysql.open();
        String sql = "SELECT * FROM Departamento WHERE Disponibilidad = true";
        List<Departamento> departamentos = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
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

    public Departamento obtenerporid(int id) {
        Connection conn = connectionMysql.open();
        String sql = "SELECT * FROM Departamento WHERE ID = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
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
                departamento.setReservaciones(obtenerReservacionesPorDepartamentoId(departamento.getId()));
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

    public boolean registrarDepartamento(Departamento departamento, List<Imagen> imagenes) {
        Connection conn = connectionMysql.open();
        String sql = "INSERT INTO Departamento (Nombre, Ubicacion, Descripcion, Tarifa, CantidadPersonasMax, Disponibilidad, UsuarioID) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            conn.setAutoCommit(false);
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, departamento.getNombre());
            stmt.setString(2, departamento.getUbicacion());
            stmt.setString(3, departamento.getDescripcion());
            stmt.setDouble(4, departamento.getTarifa());
            stmt.setInt(5, departamento.getCantidadPersonasMax());
            stmt.setBoolean(6, departamento.isDisponibilidad());
            stmt.setInt(7, departamento.getUsuarioID());
            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int departamentoId = generatedKeys.getInt(1);
                    for (Imagen imagen : imagenes) {
                        registrarImagen(imagen, departamentoId, conn);
                    }
                }
            }
            conn.commit();
            return rowsInserted > 0;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            connectionMysql.close();
        }
    }

    public boolean actualizarDepartamento(Departamento departamento, List<Imagen> imagenes) {
        Connection conn = connectionMysql.open();
        String sql = "UPDATE Departamento SET Nombre = ?, Ubicacion = ?, Descripcion = ?, Tarifa = ?, CantidadPersonasMax = ?, Disponibilidad = ?, UsuarioID = ? WHERE ID = ?";
        try {
            conn.setAutoCommit(false);
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, departamento.getNombre());
            stmt.setString(2, departamento.getUbicacion());
            stmt.setString(3, departamento.getDescripcion());
            stmt.setDouble(4, departamento.getTarifa());
            stmt.setInt(5, departamento.getCantidadPersonasMax());
            stmt.setBoolean(6, departamento.isDisponibilidad());
            stmt.setInt(7, departamento.getUsuarioID());
            stmt.setInt(8, departamento.getId());
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                actualizarImagenes(imagenes, departamento.getId(), conn);
            }
            conn.commit();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
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

    private List<Reservacion> obtenerReservacionesPorDepartamentoId(int departamentoId) {
        Connection conn = connectionMysql.open();
        String sql = "SELECT * FROM Reservacion WHERE DepartamentoID = ? AND FechaInicio > ?";
        List<Reservacion> reservaciones = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, departamentoId);
            stmt.setDate(2, new java.sql.Date(new Date().getTime()));
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

    private boolean registrarImagen(Imagen imagen, int departamentoId, Connection conn) throws SQLException {
        String sql = "INSERT INTO Imagen (URLImagen, DepartamentoID) VALUES (?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, imagen.getUrlImagen());
        stmt.setInt(2, departamentoId);
        int rowsInserted = stmt.executeUpdate();
        return rowsInserted > 0;
    }

    private boolean actualizarImagenes(List<Imagen> imagenes, int departamentoId, Connection conn) throws SQLException {
        List<Imagen> imagenesExistentes = obtenerImagenesPorDepartamentoId(departamentoId);

        for (Imagen imagen : imagenes) {
            if (imagen.getId() == 0) {
                registrarImagen(imagen, departamentoId, conn);
            } else {
                boolean found = false;
                for (Imagen imgExistente : imagenesExistentes) {
                    if (imgExistente.getId() == imagen.getId()) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    registrarImagen(imagen, departamentoId, conn);
                }
            }
        }

        for (Imagen imgExistente : imagenesExistentes) {
            boolean found = false;
            for (Imagen imagen : imagenes) {
                if (imgExistente.getId() == imagen.getId()) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                eliminarImagen(imgExistente.getId(), conn);
            }
        }

        return true;
    }

  private boolean eliminarImagen(int imagenId, Connection conn) throws SQLException {
        String sql = "DELETE FROM Imagen WHERE ID = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, imagenId);
        int rowsDeleted = stmt.executeUpdate();
        return rowsDeleted > 0;
    }
}