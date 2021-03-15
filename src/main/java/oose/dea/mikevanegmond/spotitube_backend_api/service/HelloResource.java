package oose.dea.mikevanegmond.spotitube_backend_api.service;

import javax.ws.rs.*;

@Path("/hello-world")
public class HelloResource {

    @GET
    @Produces("text/plain")
    public String hello() {
        return "Hello, World!";
    }

}