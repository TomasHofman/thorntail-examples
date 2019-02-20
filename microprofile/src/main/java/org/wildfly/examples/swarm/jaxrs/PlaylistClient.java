package org.wildfly.examples.swarm.jaxrs;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/playlist")
@Consumes("application/json")
public interface PlaylistClient {

    @GET
    List<String> getSongs();

}
