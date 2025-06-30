/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.frankcasa.services.rest;

import com.google.gson.Gson;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.frankcasa.services.controller.ControllerUsuario;
import org.frankcasa.services.model.Usuario;

@Path("usuario")
public class RestUsuario {
    private Gson gson = new Gson();

    @Path("login")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("user") String user, @FormParam("password") String password) {
        try {
            ControllerUsuario CU = new ControllerUsuario();
            int usuario = CU.login(user, password);
            if (usuario > -1) {
                String out = gson.toJson(usuario);
                return Response.ok(out).build();
            } else {
                return Response.status(401).entity("{\"error\":\"Usuario no encontrado\"}").build();
            }
        } catch (Exception e) {
            return Response.serverError().entity("{\"error\":\"" + e.getMessage() + "\"}").build();
        }
    }

    @Path("registrar")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrar(String jsonUsuario) {
        try {
            Usuario usuario = gson.fromJson(jsonUsuario, Usuario.class);
            ControllerUsuario CU = new ControllerUsuario();
            boolean result = CU.registrar(usuario);
            return Response.ok("{\"success\":" + result + "}").build();
        } catch (Exception e) {
            return Response.serverError().entity("{\"error\":\"" + e.getMessage() + "\"}").build();
        }
    }

    @Path("obtenerporid")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPorId(@QueryParam("id") int id) {
        try {
            ControllerUsuario CU = new ControllerUsuario();
            Usuario usuario = CU.obtenerPorId(id);
            System.out.println(usuario + "");
            if (usuario != null) {
                String out = gson.toJson(usuario);
                return Response.ok(out).build();
            } else {
                return Response.status(404).entity("{\"error\":\"Usuario no encontrado\"}").build();
            }
        } catch (Exception e) {
            return Response.serverError().entity("{\"error\":\"" + e.getMessage() + "\"}").build();
        }
    }

    @Path("actualizar")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizar(String jsonUsuario) {
        try {
            Usuario usuario = gson.fromJson(jsonUsuario, Usuario.class);
            ControllerUsuario CU = new ControllerUsuario();
            boolean result = CU.actualizar(usuario);
            return Response.ok("{\"success\":" + result + "}").build();
        } catch (Exception e) {
            return Response.serverError().entity("{\"error\":\"" + e.getMessage() + "\"}").build();
        }
    }
}

