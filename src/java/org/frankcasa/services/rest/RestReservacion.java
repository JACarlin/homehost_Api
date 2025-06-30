/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.frankcasa.services.rest;

import com.google.gson.Gson;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.frankcasa.services.controller.ControllerReservacion;
import org.frankcasa.services.model.Reservacion;

import java.util.List;

@Path("reservacion")
public class RestReservacion {
    private Gson gson = new Gson();

    @Path("crearRegistro")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearRegistro(String jsonReservacion) {
        try {
            Reservacion reservacion = gson.fromJson(jsonReservacion, Reservacion.class);
            ControllerReservacion CR = new ControllerReservacion();
            boolean result = CR.crearReservacion(reservacion);
            return Response.ok("{\"success\":" + result + "}").build();
        } catch (Exception e) {
            return Response.serverError().entity("{\"error\":\"" + e.getMessage() + "\"}").build();
        }
    }

    @Path("obtenerReservacionesPorUsuario")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerReservacionesPorUsuario(@QueryParam("usuarioID") int usuarioID) {
        try {
            ControllerReservacion CR = new ControllerReservacion();
            List<Reservacion> reservaciones = CR.obtenerReservacionesPorUsuario(usuarioID);
            String out = gson.toJson(reservaciones);
            return Response.ok(out).build();
        } catch (Exception e) {
            return Response.serverError().entity("{\"error\":\"" + e.getMessage() + "\"}").build();
        }
    }
}

