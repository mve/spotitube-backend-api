import oose.dea.mikevanegmond.spotitube_backend_api.dao.IPlaylistDAO;
import oose.dea.mikevanegmond.spotitube_backend_api.dao.ITrackDAO;
import oose.dea.mikevanegmond.spotitube_backend_api.dao.IUserDAO;
import oose.dea.mikevanegmond.spotitube_backend_api.domain.Playlist;
import oose.dea.mikevanegmond.spotitube_backend_api.domain.Track;
import oose.dea.mikevanegmond.spotitube_backend_api.domain.User;
import oose.dea.mikevanegmond.spotitube_backend_api.service.PlaylistResource;
import oose.dea.mikevanegmond.spotitube_backend_api.service.TrackResource;
import oose.dea.mikevanegmond.spotitube_backend_api.service.dto.AddTrackDTO;
import oose.dea.mikevanegmond.spotitube_backend_api.service.dto.CreatePlaylistDTO;
import oose.dea.mikevanegmond.spotitube_backend_api.service.dto.EditPlaylistDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class TrackResourceTest {
    private TrackResource trackResource;

    private User user;

    @BeforeEach
    public void setup() {
        trackResource = new TrackResource();

        user = new User();
        user.setId(0);
        user.setUsername("username");
        user.setPassword("password");
    }

    public Playlist getFakePlaylist() {
        Playlist playlist = new Playlist();
        playlist.setId(0);
        playlist.setName("name");
        playlist.setOwner(false);
        playlist.setTracks(new ArrayList<Track>());

        return playlist;
    }

    public Track getFakeTrack() {
        Track track = new Track();
        track.setId(0);
        track.setTitle("Title");
        track.setPerformer("Performer");
        track.setDuration(10);
        track.setAlbum("Album");
        track.setPlaycount(10);
        track.setPublicationDate("1-1-2021");
        track.setDescription("Description");
        track.setOfflineAvailable(true);

        return track;
    }

    @Test
    public void getTracksNotFromPlaylistTest() {
        // Arrange
        String token = "123";
        ArrayList<Track> tracks = new ArrayList<>();
        tracks.add(getFakeTrack());

        ITrackDAO trackDAOMock = mock(ITrackDAO.class);

        this.trackResource.setITrackDAO(trackDAOMock);

        when(trackDAOMock.getTracksFromPlaylist(1)).thenReturn(tracks);

        // Act
        Response response = trackResource.getTracksNotFromPlaylist(token, 1);

        // Assert
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }
}
