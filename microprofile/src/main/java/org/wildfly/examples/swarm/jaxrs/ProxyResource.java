package org.wildfly.examples.swarm.jaxrs;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.RestClientBuilder;

/**
 */
@Path("/")
public class ProxyResource {

    @Path("/playlist")
    @Consumes("application/json")
    public interface PlaylistClientInnerPublic {
        @GET
        List<String> getSongs();
    }

    @Path("/playlist")
    @Consumes("application/json")
    interface PlaylistClientInner {
        @GET
        List<String> getSongs();
    }

    @GET
    @Path("/proxy")
    @Produces("text/plain")
    public Response playlist() throws MalformedURLException {
        URL apiUrl = new URL("http://localhost:8080/");
        List<String> songs1;
        List<String> songs2;
        List<String> songs3 = null;
        Exception exception = null;
        String stackTrace = null;

        // works fine
        PlaylistClientInnerPublic client1 = RestClientBuilder.newBuilder()
                .baseUrl(apiUrl)
                .build(PlaylistClientInnerPublic.class);
        songs1 = client1.getSongs();

        // works fine
        PlaylistClient client2 = RestClientBuilder.newBuilder()
                .baseUrl(apiUrl)
                .build(PlaylistClient.class);
        songs2 = client2.getSongs();

        // fails
        try {
            PlaylistClientInner client3 = RestClientBuilder.newBuilder()
                    .baseUrl(apiUrl)
                    .build(PlaylistClientInner.class);
            songs3 = client3.getSongs();
        } catch (Exception e) {
            exception = e;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintWriter writer = new PrintWriter(baos, true);
            e.printStackTrace(writer);
            e.printStackTrace();
            stackTrace = baos.toString();
        }

        return Response.ok("public inner: " + songs1 + "\n"
                + "standalone interface: " + songs2 + "\n"
                + "non-public inner: " + songs3 + "\n"
                + "exception: " + exception + "\n"
                + "stack trace: " + stackTrace + "\n"
        ).build();
    }
}
