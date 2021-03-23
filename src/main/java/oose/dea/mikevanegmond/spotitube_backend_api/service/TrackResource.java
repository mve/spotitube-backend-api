package oose.dea.mikevanegmond.spotitube_backend_api.service;

import oose.dea.mikevanegmond.spotitube_backend_api.dao.ITrackDAO;
import oose.dea.mikevanegmond.spotitube_backend_api.domain.Playlist;
import oose.dea.mikevanegmond.spotitube_backend_api.domain.Track;
import oose.dea.mikevanegmond.spotitube_backend_api.domain.User;
import oose.dea.mikevanegmond.spotitube_backend_api.service.dto.PlaylistsDTO;
import oose.dea.mikevanegmond.spotitube_backend_api.service.dto.TracksDTO;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/tracks")
public class TrackResource {

    private ITrackDAO trackDAO;

    @Inject
    public void setITrackDAO(ITrackDAO trackDAO) {
        this.trackDAO = trackDAO;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTracksNotFromPlaylist(@QueryParam("token") String token, @QueryParam("forPlaylist") int forPlaylist) {

        ArrayList<Track> tracks = trackDAO.getTracksNotFromPlaylist(forPlaylist);

        TracksDTO tracksDTO = new TracksDTO();
        tracksDTO.setTracks(tracks);

        return Response.status(Response.Status.OK)
                .entity(tracksDTO)
                .build();
    }
}
