import oose.dea.mikevanegmond.spotitube_backend_api.dao.IPlaylistDAO;
import oose.dea.mikevanegmond.spotitube_backend_api.dao.IUserDAO;
import oose.dea.mikevanegmond.spotitube_backend_api.dao.PlaylistDAO;
import oose.dea.mikevanegmond.spotitube_backend_api.domain.Playlist;
import oose.dea.mikevanegmond.spotitube_backend_api.domain.User;
import oose.dea.mikevanegmond.spotitube_backend_api.service.PlaylistResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class PlaylistDAOTest {
    private PlaylistDAO playlistDAO;

    @BeforeEach
    public void setup() {
        playlistDAO = new PlaylistDAO();
    }

    @Test
    public void getPlaylistsTest() {
        try {
            // Arrange
            String expectedSQL = "select * from playlist";
            int idToTest = 1;

            // setup Mocks
            DataSource dataSource = mock(DataSource.class);
            Connection connection = mock(Connection.class);
            PreparedStatement preparedStatement = mock(PreparedStatement.class);
            ResultSet resultSet = mock(ResultSet.class);

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);

            // setup classes
            playlistDAO.setDataSource(dataSource);

            // Act
            ArrayList<Playlist> playlists = playlistDAO.getPlaylists(1);

            // Assert
            verify(connection).prepareStatement(expectedSQL);

            assertEquals(playlists, new ArrayList<Playlist>());

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void createPlaylistTest() {
        try {
            // Arrange
            String expectedSQL = "INSERT INTO playlist (name, owner_id) VALUES (?, ?)";
            String playlistName = "Playlist name";
            int ownerId = 1;

            // setup Mocks
            DataSource dataSource = mock(DataSource.class);
            Connection connection = mock(Connection.class);
            PreparedStatement preparedStatement = mock(PreparedStatement.class);
            ResultSet resultSet = mock(ResultSet.class);

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);

            // setup classes
            playlistDAO.setDataSource(dataSource);

            // Act
            playlistDAO.createPlaylist(playlistName, ownerId);

            // Assert
            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).setString(1,playlistName);
            verify(preparedStatement).setInt(2,ownerId);
            // TODO wat moet ik asserten in een void methode?

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void geditPlaylistTest() {
        try {
            // Arrange
            String expectedSQL = "select * from playlist";
            int idToTest = 1;

            // setup Mocks
            DataSource dataSource = mock(DataSource.class);
            Connection connection = mock(Connection.class);
            PreparedStatement preparedStatement = mock(PreparedStatement.class);
            ResultSet resultSet = mock(ResultSet.class);

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);

            // setup classes
            playlistDAO.setDataSource(dataSource);

            // Act
            ArrayList<Playlist> playlists = playlistDAO.getPlaylists(1);

            // Assert
            verify(connection).prepareStatement(expectedSQL);

            assertEquals(playlists, new ArrayList<Playlist>());

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void getTotalDurationTest() {
        try {
            // Arrange
            String expectedSQL = "select * from playlist";
            int idToTest = 1;

            // setup Mocks
            DataSource dataSource = mock(DataSource.class);
            Connection connection = mock(Connection.class);
            PreparedStatement preparedStatement = mock(PreparedStatement.class);
            ResultSet resultSet = mock(ResultSet.class);

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);

            // setup classes
            playlistDAO.setDataSource(dataSource);

            // Act
            ArrayList<Playlist> playlists = playlistDAO.getPlaylists(1);

            // Assert
            verify(connection).prepareStatement(expectedSQL);

            assertEquals(playlists, new ArrayList<Playlist>());

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void deleteTest() {
        try {
            // Arrange
            String expectedSQL = "select * from playlist";
            int idToTest = 1;

            // setup Mocks
            DataSource dataSource = mock(DataSource.class);
            Connection connection = mock(Connection.class);
            PreparedStatement preparedStatement = mock(PreparedStatement.class);
            ResultSet resultSet = mock(ResultSet.class);

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);

            // setup classes
            playlistDAO.setDataSource(dataSource);

            // Act
            ArrayList<Playlist> playlists = playlistDAO.getPlaylists(1);

            // Assert
            verify(connection).prepareStatement(expectedSQL);

            assertEquals(playlists, new ArrayList<Playlist>());

        } catch (Exception e) {
            fail();
        }
    }

}
