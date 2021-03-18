package oose.dea.mikevanegmond.spotitube_backend_api.service;

import oose.dea.mikevanegmond.spotitube_backend_api.dao.IPlaylistDAO;
import oose.dea.mikevanegmond.spotitube_backend_api.domain.Playlist;
import oose.dea.mikevanegmond.spotitube_backend_api.service.dto.LoginDTO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/playlists")
public class PlaylistResource {

    private IPlaylistDAO playlistDAO;

    @Inject
    public void setIPlaylistDAO(IPlaylistDAO playlistDAO) {
        this.playlistDAO = playlistDAO;
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlaylists(LoginDTO loginRequest) {

        ArrayList<Playlist> playlists = playlistDAO.getPlaylists();

        System.out.println(playlists);

//        String message = "{\"token\": \"" + newUUID + "\", \"user\": \"" + user.getUsername() + "\"}";
        String message = "{\"playlists\" : [ { \"id\" : 1, \"name\" : \"Death metal\",\"owner\" : true,\"tracks\": []},],\"length\"  :123445}";

        return Response.status(Response.Status.OK)
                .entity(message)
                .build();
    }

}
