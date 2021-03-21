package oose.dea.mikevanegmond.spotitube_backend_api.service;

import oose.dea.mikevanegmond.spotitube_backend_api.dao.IPlaylistDAO;
import oose.dea.mikevanegmond.spotitube_backend_api.dao.ITrackDAO;
import oose.dea.mikevanegmond.spotitube_backend_api.domain.Playlist;
import oose.dea.mikevanegmond.spotitube_backend_api.domain.Track;
import oose.dea.mikevanegmond.spotitube_backend_api.service.dto.CreatePlaylistDTO;
import oose.dea.mikevanegmond.spotitube_backend_api.service.dto.EditPlaylistDTO;
import oose.dea.mikevanegmond.spotitube_backend_api.service.dto.PlaylistsDTO;
import oose.dea.mikevanegmond.spotitube_backend_api.service.dto.TracksDTO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/playlists")
public class PlaylistResource {

    private IPlaylistDAO playlistDAO;
    private ITrackDAO trackDAO;

    @Inject
    public void setIPlaylistDAO(IPlaylistDAO playlistDAO) {
        this.playlistDAO = playlistDAO;
    }

    @Inject
    public void setITrackDAO(ITrackDAO trackDAO) {
        this.trackDAO = trackDAO;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlaylists(@QueryParam("token") String token) {

        ArrayList<Playlist> playlists = playlistDAO.getPlaylists();

        PlaylistsDTO playlistsDTO = new PlaylistsDTO();
        playlistsDTO.setPlaylists(playlists);
        playlistsDTO.setLength(1);

        return Response.status(Response.Status.OK)
                .entity(playlistsDTO)
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPlaylist(CreatePlaylistDTO createPlaylistDTO, @QueryParam("token") String token) {

        // TODO get userid by token.

        playlistDAO.createPlaylist(createPlaylistDTO.getName(), 1);

        return getPlaylists(token);
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editPlaylist(@PathParam("id") int id, EditPlaylistDTO editPlaylistDTO, @QueryParam("token") String token) {

        playlistDAO.editPlaylist(editPlaylistDTO.getName(), id, editPlaylistDTO.getOwner());
        return getPlaylists(token);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePlaylist(@PathParam("id") int id, @QueryParam("token") String token) {

        playlistDAO.delete(id);

        return getPlaylists(token);
    }

    @GET
    @Path("/{id}/tracks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTracksForPlaylist(@PathParam("id") int id, @QueryParam("token") String token) {

        ArrayList<Track> tracks = trackDAO.getTracksFromPlaylist(id);
        TracksDTO tracksDTO = new TracksDTO();
        tracksDTO.setTracks(tracks);

        return Response.status(Response.Status.OK)
                .entity(tracksDTO)
                .build();
    }

}
