package br.com.viniciustoni.gateway.ws.api.v1;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/gateway")
public class GatewayJersey {

    private static final Logger log = LoggerFactory.getLogger(GatewayJersey.class);

    @POST
    @Path("/event")
    @Consumes(
        { MediaType.APPLICATION_JSON })
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response eventGatewayPost(final String json) {

        log.info("######################################################");
        log.info("Event - POST");
        log.info(json);
        log.info("######################################################");

        return Response.ok("").build();
    }

    @GET
    @Path("/event")
    @Consumes(
        { MediaType.APPLICATION_JSON })
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response eventGatewayGet(final String json) {

        log.info("######################################################");
        log.info("Event - GET");
        log.info(json);
        log.info("######################################################");

        return Response.ok("").build();
    }
    
    @GET
    @Path("/ping")
    @Consumes(
        { MediaType.APPLICATION_JSON })
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response ping() {

        return Response.ok("Pong").build();
    }
}
