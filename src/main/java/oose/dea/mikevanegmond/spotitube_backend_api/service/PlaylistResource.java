package oose.dea.mikevanegmond.spotitube_backend_api.service;

import oose.dea.mikevanegmond.spotitube_backend_api.dao.IPlaylistDAO;
import oose.dea.mikevanegmond.spotitube_backend_api.dao.ITrackDAO;
import oose.dea.mikevanegmond.spotitube_backend_api.dao.IUserDAO;
import oose.dea.mikevanegmond.spotitube_backend_api.domain.Playlist;
import oose.dea.mikevanegmond.spotitube_backend_api.domain.Track;
import oose.dea.mikevanegmond.spotitube_backend_api.domain.User;
import oose.dea.mikevanegmond.spotitube_backend_api.service.dto.*;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/playlists")
public class PlaylistResource {

    private IPlaylistDAO playlistDAO;
    private ITrackDAO trackDAO;
    private IUserDAO userDAO;

    @Inject
    public void setIPlaylistDAO(IPlaylistDAO playlistDAO) {
        this.playlistDAO = playlistDAO;
    }

    @Inject
    public void setITrackDAO(ITrackDAO trackDAO) {
        this.trackDAO = trackDAO;
    }

    @Inject
    public void setIUserDAO(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlaylists(@QueryParam("token") String token) {

        User user = userDAO.getUserByToken(token);

        ArrayList<Playlist> playlists = playlistDAO.getPlaylists(user.getId());

        PlaylistsDTO playlistsDTO = new PlaylistsDTO();
        playlistsDTO.setPlaylists(playlists);
        playlistsDTO.setLength(playlistDAO.getTotalDuration(playlists));

        return Response.status(Response.Status.OK)
                .entity(playlistsDTO)
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPlaylist(CreatePlaylistDTO createPlaylistDTO, @QueryParam("token") String token) {
        User user = userDAO.getUserByToken(token);
        playlistDAO.createPlaylist(createPlaylistDTO.getName(), user.getId());

        return getPlaylists(token);
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editPlaylist(@PathParam("id") int id, EditPlaylistDTO editPlaylistDTO, @QueryParam("token") String token) {

        if (!editPlaylistDTO.isOwner()) {
            // not the owner.
            return getPlaylists(token);
        }

        User user = userDAO.getUserByToken(token);

        playlistDAO.editPlaylist(editPlaylistDTO.getName(), id, user.getId());
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

    @POST
    @Path("/{id}/tracks")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTrackToPlaylist(@PathParam("id") int id, AddTrackDTO addTrackDTO, @QueryParam("token") String token) {

        // TODO offlineavailable moet bij koppeltabel staan.
        trackDAO.addTrackToPlaylist(id, addTrackDTO.getId());
        return getTracksForPlaylist(id, token);
    }

    @DELETE
    @Path("/{p_id}/tracks/{t_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeTrackFromPlaylist(@PathParam("p_id") int playlistId, @PathParam("t_id") int trackId, @QueryParam("token") String token) {

        User user = userDAO.getUserByToken(token);

        trackDAO.removeTrackFromPlaylist(playlistId, trackId, user.getId());
        return getTracksForPlaylist(playlistId, token);
    }

}
