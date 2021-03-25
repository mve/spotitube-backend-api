package oose.dea.mikevanegmond.spotitube_backend_api.service;

import oose.dea.mikevanegmond.spotitube_backend_api.dao.IPlaylistDAO;
import oose.dea.mikevanegmond.spotitube_backend_api.dao.ITrackDAO;
import oose.dea.mikevanegmond.spotitube_backend_api.dao.IUserDAO;
import oose.dea.mikevanegmond.spotitube_backend_api.domain.Playlist;
import oose.dea.mikevanegmond.spotitube_backend_api.domain.Track;
import oose.dea.mikevanegmond.spotitube_backend_api.domain.User;
import oose.dea.mikevanegmond.spotitube_backend_api.dto.*;
import oose.dea.mikevanegmond.spotitube_backend_api.exceptions.InvalidTokenException;

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

    /**
     * Get user by token then return all playlists.
     *
     * @param token
     * @return PlaylistsDTO
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlaylists(@QueryParam("token") String token) {
        try {
            User user = userDAO.getUserByToken(token);

            ArrayList<Playlist> playlists = playlistDAO.getPlaylists(user.getId());

            PlaylistsDTO playlistsDTO = new PlaylistsDTO();
            playlistsDTO.setPlaylists(playlists);
            playlistsDTO.setLength(playlistDAO.getTotalDuration(playlists));

            return Response.status(Response.Status.OK)
                    .entity(playlistsDTO)
                    .build();

        } catch (InvalidTokenException e) {
            e.printStackTrace();
            return Response.status(Response.Status.FORBIDDEN).build();
        }

    }

    /**
     * Get all playlists but allows for a custom response status. Useful to send correct status after playlist creation for example.
     *
     * @param token
     * @param status
     * @return Response with laylistsDTO
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlaylistsCustomStatus(@QueryParam("token") String token, Response.Status status) {
        try {
            User user = userDAO.getUserByToken(token);

            ArrayList<Playlist> playlists = playlistDAO.getPlaylists(user.getId());

            PlaylistsDTO playlistsDTO = new PlaylistsDTO();
            playlistsDTO.setPlaylists(playlists);
            playlistsDTO.setLength(playlistDAO.getTotalDuration(playlists));

            return Response.status(status)
                    .entity(playlistsDTO)
                    .build();

        } catch (InvalidTokenException e) {
            e.printStackTrace();
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

    /**
     * Create a new playlist.
     *
     * @param createPlaylistDTO
     * @param token
     * @return Response with all playlists with response status created.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPlaylist(CreatePlaylistDTO createPlaylistDTO, @QueryParam("token") String token) {
        try {
            User user = userDAO.getUserByToken(token);
            playlistDAO.createPlaylist(createPlaylistDTO.getName(), user.getId());
            return getPlaylistsCustomStatus(token, Response.Status.CREATED);
        } catch (InvalidTokenException e) {
            e.printStackTrace();
            return Response.status(Response.Status.FORBIDDEN).build();
        }

    }

    /**
     * Edit a playlist.
     *
     * @param id
     * @param editPlaylistDTO
     * @param token
     * @return all playlists
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editPlaylist(@PathParam("id") int id, EditPlaylistDTO editPlaylistDTO, @QueryParam("token") String token) {

        if (!editPlaylistDTO.isOwner()) {
            // not the owner.
            return getPlaylistsCustomStatus(token, Response.Status.FORBIDDEN);
        }

        try {
            User user = userDAO.getUserByToken(token);

            playlistDAO.editPlaylist(editPlaylistDTO.getName(), id, user.getId());
            return getPlaylists(token);
        } catch (InvalidTokenException e) {
            e.printStackTrace();
            return Response.status(Response.Status.FORBIDDEN).build();
        }

    }

    /**
     * Delete a playlist.
     *
     * @param id
     * @param token
     * @return All playlists.
     */
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePlaylist(@PathParam("id") int id, @QueryParam("token") String token) {

        playlistDAO.delete(id);

        return getPlaylists(token);
    }

    /**
     * Get all tracks in a playlist.
     *
     * @param id
     * @param token
     * @return A response with TracksDTO
     */
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

    /**
     * Add a track to a playlist.
     *
     * @param id
     * @param addTrackDTO
     * @param token
     * @return All tracks in a playlist.
     */
    @POST
    @Path("/{id}/tracks")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTrackToPlaylist(@PathParam("id") int id, AddTrackDTO addTrackDTO, @QueryParam("token") String token) {

        trackDAO.addTrackToPlaylist(id, addTrackDTO.getId(), addTrackDTO.isOfflineAvailable());
        return getTracksForPlaylist(id, token);
    }

    /**
     * Remove a track from a playlist.
     *
     * @param playlistId
     * @param trackId
     * @param token
     * @return All tracks in a playlist.
     */
    @DELETE
    @Path("/{p_id}/tracks/{t_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeTrackFromPlaylist(@PathParam("p_id") int playlistId, @PathParam("t_id") int trackId, @QueryParam("token") String token) {

        try {
            User user = userDAO.getUserByToken(token);

            trackDAO.removeTrackFromPlaylist(playlistId, trackId, user.getId());
            return getTracksForPlaylist(playlistId, token);
        } catch (InvalidTokenException e) {
            e.printStackTrace();
            return Response.status(Response.Status.FORBIDDEN).build();
        }


    }

}
