package com.example;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
 
import com.google.inject.Inject;
import com.google.inject.name.Named;

import com.google.inject.servlet.RequestScoped;
 
@Path("/")
@RequestScoped
public class GuiceResource {
 
    private String message;

    @Inject
    public GuiceResource(@Named("testString") String message) {
        this.message = message;
    }
    
    @GET
    @Produces("text/plain")
    public String getIt() {
        return message;
    }
}
