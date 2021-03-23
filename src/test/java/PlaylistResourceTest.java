import oose.dea.mikevanegmond.spotitube_backend_api.dao.IPlaylistDAO;
import oose.dea.mikevanegmond.spotitube_backend_api.dao.ITrackDAO;
import oose.dea.mikevanegmond.spotitube_backend_api.dao.IUserDAO;
import oose.dea.mikevanegmond.spotitube_backend_api.domain.Playlist;
import oose.dea.mikevanegmond.spotitube_backend_api.domain.Track;
import oose.dea.mikevanegmond.spotitube_backend_api.domain.User;
import oose.dea.mikevanegmond.spotitube_backend_api.service.LoginResource;
import oose.dea.mikevanegmond.spotitube_backend_api.service.PlaylistResource;
import oose.dea.mikevanegmond.spotitube_backend_api.service.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class PlaylistResourceTest {
    private PlaylistResource playlistResource;

    private User user;

    @BeforeEach
    public void setup() {
        playlistResource = new PlaylistResource();

        user = new User(0);
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
    public void getPlaylistsTest() {
        // Arrange
        String token = "123";
        ArrayList<Playlist> playlists = new ArrayList<>();
        playlists.add(getFakePlaylist());

        IUserDAO userDAOMock = mock(IUserDAO.class);
        IPlaylistDAO playlistDAOMock = mock(IPlaylistDAO.class);

        this.playlistResource.setIUserDAO(userDAOMock);
        this.playlistResource.setIPlaylistDAO(playlistDAOMock);

        when(userDAOMock.getUserByToken(token)).thenReturn(user);
        when(playlistDAOMock.getPlaylists(user.getId())).thenReturn(playlists);
        when(playlistDAOMock.getTotalDuration(playlists)).thenReturn(1);

        // Act
        Response response = playlistResource.getPlaylists(token);

        // Assert
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test
    public void createPlaylistTest() {
        // Arrange
        String token = "123";
        CreatePlaylistDTO createPlaylistDTO = new CreatePlaylistDTO();
        createPlaylistDTO.setId(0);
        createPlaylistDTO.setName("Name");
        createPlaylistDTO.setOwner(true);

        IUserDAO userDAOMock = mock(IUserDAO.class);
        IPlaylistDAO playlistDAOMock = mock(IPlaylistDAO.class);

        this.playlistResource.setIUserDAO(userDAOMock);
        this.playlistResource.setIPlaylistDAO(playlistDAOMock);

        when(userDAOMock.getUserByToken(token)).thenReturn(user);

        // Act
        Response response = playlistResource.createPlaylist(createPlaylistDTO, token);

        // Assert
        assertEquals(Response.Status.CREATED, response.getStatusInfo());
    }

    @Test
    public void editPlaylistTest() {
        // Arrange
        String token = "123";
        EditPlaylistDTO editPlaylistDTO = new EditPlaylistDTO();
        editPlaylistDTO.setId(0);
        editPlaylistDTO.setName("Name");
        editPlaylistDTO.setOwner(true);

        IUserDAO userDAOMock = mock(IUserDAO.class);
        IPlaylistDAO playlistDAOMock = mock(IPlaylistDAO.class);

        this.playlistResource.setIUserDAO(userDAOMock);
        this.playlistResource.setIPlaylistDAO(playlistDAOMock);

        when(userDAOMock.getUserByToken(token)).thenReturn(user);

        // Act
        Response response = playlistResource.editPlaylist(1, editPlaylistDTO, token);

        // Assert
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test
    public void editPlaylistNotOwnerTest() {
        // Arrange
        String token = "123";
        EditPlaylistDTO editPlaylistDTO = new EditPlaylistDTO();
        editPlaylistDTO.setId(0);
        editPlaylistDTO.setName("Name");
        editPlaylistDTO.setOwner(false);

        IUserDAO userDAOMock = mock(IUserDAO.class);
        IPlaylistDAO playlistDAOMock = mock(IPlaylistDAO.class);

        this.playlistResource.setIUserDAO(userDAOMock);
        this.playlistResource.setIPlaylistDAO(playlistDAOMock);

        when(userDAOMock.getUserByToken(token)).thenReturn(user);

        // Act
        Response response = playlistResource.editPlaylist(1, editPlaylistDTO, token);

        // Assert
        assertEquals(Response.Status.FORBIDDEN, response.getStatusInfo());
    }

    @Test
    public void deletePlaylistTest() {
        // Arrange
        String token = "123";

        IUserDAO userDAOMock = mock(IUserDAO.class);
        IPlaylistDAO playlistDAOMock = mock(IPlaylistDAO.class);

        this.playlistResource.setIUserDAO(userDAOMock);
        this.playlistResource.setIPlaylistDAO(playlistDAOMock);

        when(userDAOMock.getUserByToken(token)).thenReturn(user);

        // Act
        Response response = playlistResource.deletePlaylist(1, token);

        // Assert
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test
    public void getTracksForPlaylistTest() {
        // Arrange
        String token = "123";
        int playlistId = 1;
        ArrayList<Track> tracks = new ArrayList<Track>();
        tracks.add(getFakeTrack());

        IUserDAO userDAOMock = mock(IUserDAO.class);
        ITrackDAO trackDAOMock = mock(ITrackDAO.class);
        IPlaylistDAO playlistDAOMock = mock(IPlaylistDAO.class);

        this.playlistResource.setIUserDAO(userDAOMock);
        this.playlistResource.setITrackDAO(trackDAOMock);
        this.playlistResource.setIPlaylistDAO(playlistDAOMock);

        when(userDAOMock.getUserByToken(token)).thenReturn(user);

        // Act
        Response response = playlistResource.getTracksForPlaylist(playlistId, token);

        // Assert
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test
    public void addTrackToPlaylistTest() {
        // Arrange
        String token = "123";
        int playlistId = 1;
        int trackId = 1;

        AddTrackDTO addTrackDTO = new AddTrackDTO();
        addTrackDTO.setId(1);
        addTrackDTO.setDuration(10);
        addTrackDTO.setOfflineAvailable(true);
        addTrackDTO.setPerformer("Perfromer");
        addTrackDTO.setTitle("Title");

        ArrayList<Track> tracks = new ArrayList<Track>();
        tracks.add(getFakeTrack());

        IUserDAO userDAOMock = mock(IUserDAO.class);
        IPlaylistDAO playlistDAOMock = mock(IPlaylistDAO.class);
        ITrackDAO trackDAOMock = mock(ITrackDAO.class);

        this.playlistResource.setIUserDAO(userDAOMock);
        this.playlistResource.setIPlaylistDAO(playlistDAOMock);
        this.playlistResource.setITrackDAO(trackDAOMock);

        when(userDAOMock.getUserByToken(token)).thenReturn(user);

        // Act
        Response response = playlistResource.addTrackToPlaylist(playlistId, addTrackDTO, token);

        // Assert
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test
    public void removeTrackFromPlaylist() {
        // Arrange
        String token = "123";
        int playlistId = 1;
        int trackId = 1;
        ArrayList<Track> tracks = new ArrayList<Track>();
        tracks.add(getFakeTrack());

        IUserDAO userDAOMock = mock(IUserDAO.class);
        IPlaylistDAO playlistDAOMock = mock(IPlaylistDAO.class);
        ITrackDAO trackDAOMock = mock(ITrackDAO.class);

        this.playlistResource.setIUserDAO(userDAOMock);
        this.playlistResource.setIPlaylistDAO(playlistDAOMock);
        this.playlistResource.setITrackDAO(trackDAOMock);

        when(userDAOMock.getUserByToken(token)).thenReturn(user);

        // Act
        Response response = playlistResource.removeTrackFromPlaylist(playlistId, trackId, token);

        // Assert
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }
}
