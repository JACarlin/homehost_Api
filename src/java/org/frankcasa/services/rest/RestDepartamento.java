/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.frankcasa.services.rest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.lang.reflect.Type;
import org.frankcasa.services.controller.ControllerDepartamento;
import org.frankcasa.services.model.Departamento;
import org.frankcasa.services.model.Imagen;


import java.util.List;

@Path("departamento")
public class RestDepartamento {
    private Gson gson = new Gson();

    @Path("obtenertodosdisponibles")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerTodosDisponibles() {
        try {
            ControllerDepartamento CD = new ControllerDepartamento();
            List<Departamento> departamentos = CD.obtenertodosdisponibles();
            String out = gson.toJson(departamentos);
            return Response.ok(out).build();
        } catch (Exception e) {
            return Response.serverError().entity("{\"error\":\"" + e.getMessage() + "\"}").build();
        }
    }

    @Path("obtenerporid")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPorId(@QueryParam("id") int id) {
        try {
            ControllerDepartamento CD = new ControllerDepartamento();
            Departamento departamento = CD.obtenerporid(id);
            if (departamento != null) {
                String out = gson.toJson(departamento);
                return Response.ok(out).build();
            } else {
                return Response.status(404).entity("{\"error\":\"Departamento no encontrado\"}").build();
            }
        } catch (Exception e) {
            return Response.serverError().entity("{\"error\":\"" + e.getMessage() + "\"}").build();
        }
    }

    @Path("registrarDepartamento")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarDepartamento(String jsonBody) {
        try {
            JsonObject jsonObject = JsonParser.parseString(jsonBody).getAsJsonObject();
            
            // Deserialize "departamento" object
            Departamento departamento = gson.fromJson(jsonObject, Departamento.class);
            
            // Extract "imagenes" array and deserialize
            Type listType = new TypeToken<List<Imagen>>(){}.getType();
            List<Imagen> imagenes = gson.fromJson(jsonObject.get("imagenes"), listType);

            ControllerDepartamento CD = new ControllerDepartamento();
            boolean result = CD.registrarDepartamento(departamento, imagenes);
            return Response.ok("{\"success\":" + result + "}").build();
        } catch (Exception e) {
            e.printStackTrace(); // Log the error for debugging
            return Response.serverError().entity("{\"error\":\"" + e.getMessage() + "\"}").build();
        }
    }

  @Path("actualizarDepartamento")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarDepartamento(String jsonBody) {
        try {
            JsonObject jsonObject = JsonParser.parseString(jsonBody).getAsJsonObject();
            
            // Deserialize "departamento" object
            Departamento departamento = gson.fromJson(jsonObject, Departamento.class);
            
            // Extract "imagenes" array and deserialize
            Type listType = new TypeToken<List<Imagen>>(){}.getType();
            List<Imagen> imagenes = gson.fromJson(jsonObject.get("imagenes"), listType);

            ControllerDepartamento CD = new ControllerDepartamento();
            boolean result = CD.actualizarDepartamento(departamento, imagenes);
            return Response.ok("{\"success\":" + result + "}").build();
        } catch (Exception e) {
            e.printStackTrace(); // Log the error for debugging
            return Response.serverError().entity("{\"error\":\"" + e.getMessage() + "\"}").build();
        }
    }
}