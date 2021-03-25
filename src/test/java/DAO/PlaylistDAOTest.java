package DAO;

import oose.dea.mikevanegmond.spotitube_backend_api.dao.PlaylistDAO;
import oose.dea.mikevanegmond.spotitube_backend_api.domain.Playlist;
import oose.dea.mikevanegmond.spotitube_backend_api.domain.Track;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public Playlist getFakePlaylist() {
        Playlist playlist = new Playlist();
        playlist.setId(0);
        playlist.setName("name");
        playlist.setOwner(false);
        playlist.setTracks(new ArrayList<Track>());

        return playlist;
    }

    @Test
    public void getPlaylistsSuccessTest() {
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
            when(resultSet.next()).thenReturn(true).thenReturn(false);
            when(resultSet.getInt("id")).thenReturn(0);
            when(resultSet.getString("name")).thenReturn("name");
            when(resultSet.getInt("owner_id")).thenReturn(0);

            // setup classes
            playlistDAO.setDataSource(dataSource);

            // Act
            ArrayList<Playlist> playlists = playlistDAO.getPlaylists(1);

            // Assert
            verify(connection).prepareStatement(expectedSQL);

            assertEquals(playlists.get(0).getId(), 0);
            assertEquals(playlists.get(0).getName(), "name");
            assertEquals(playlists.get(0).isOwner(), false);

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void getPlaylistsExceptionTest() {
        try {
            // Arrange
            String expectedSQL = "select * from playlist";
            int idToTest = 1;

            // setup Mocks
            DataSource dataSource = mock(DataSource.class);
            Connection connection = mock(Connection.class);
            PreparedStatement preparedStatement = mock(PreparedStatement.class);

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenThrow(new SQLException());

            // setup classes
            playlistDAO.setDataSource(dataSource);

            // Act
            ArrayList<Playlist> playlists = playlistDAO.getPlaylists(1);

            // Assert
            verify(connection).prepareStatement(expectedSQL);

            assertEquals(0, playlists.size());

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void createPlaylistSuccessTest() {
        try {
            // Arrange
            String expectedSQL = "INSERT INTO playlist (name, owner_id) VALUES (?, ?)";
            String playlistName = "Playlist name";
            int ownerId = 1;

            // setup Mocks
            DataSource dataSource = mock(DataSource.class);
            Connection connection = mock(Connection.class);
            PreparedStatement preparedStatement = mock(PreparedStatement.class);

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeUpdate()).thenReturn(1);

            // setup classes
            playlistDAO.setDataSource(dataSource);

            // Act
            boolean status = playlistDAO.createPlaylist(playlistName, ownerId);

            // Assert
            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).setString(1, playlistName);
            verify(preparedStatement).setInt(2, ownerId);

            assertTrue(status);

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void createPlaylistFailedTest() {
        try {
            // Arrange
            String expectedSQL = "INSERT INTO playlist (name, owner_id) VALUES (?, ?)";
            String playlistName = "Playlist name";
            int ownerId = 1;

            // setup Mocks
            DataSource dataSource = mock(DataSource.class);
            Connection connection = mock(Connection.class);
            PreparedStatement preparedStatement = mock(PreparedStatement.class);

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeUpdate()).thenReturn(0);

            // setup classes
            playlistDAO.setDataSource(dataSource);

            // Act
            boolean status = playlistDAO.createPlaylist(playlistName, ownerId);

            // Assert
            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).setString(1, playlistName);
            verify(preparedStatement).setInt(2, ownerId);

            assertFalse(status);

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void createPlaylistExceptionTest() {
        try {
            // Arrange
            String expectedSQL = "INSERT INTO playlist (name, owner_id) VALUES (?, ?)";
            String playlistName = "Playlist name";
            int ownerId = 1;

            // setup Mocks
            DataSource dataSource = mock(DataSource.class);
            Connection connection = mock(Connection.class);
            PreparedStatement preparedStatement = mock(PreparedStatement.class);

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeUpdate()).thenThrow(new SQLException());

            // setup classes
            playlistDAO.setDataSource(dataSource);

            // Act
            boolean status = playlistDAO.createPlaylist(playlistName, ownerId);

            // Assert
            assertFalse(status);

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void editPlaylistSuccessTest() {
        try {
            // Arrange
            String expectedSQL = "UPDATE playlist SET name = ? WHERE id = ? AND owner_id = ?";
            String playlistName = "Playlist name";
            int playlistId = 1;
            int ownerId = 1;

            // setup Mocks
            DataSource dataSource = mock(DataSource.class);
            Connection connection = mock(Connection.class);
            PreparedStatement preparedStatement = mock(PreparedStatement.class);

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeUpdate()).thenReturn(1);

            // setup classes
            playlistDAO.setDataSource(dataSource);

            // Act
            boolean status = playlistDAO.editPlaylist(playlistName, playlistId, ownerId);

            // Assert
            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).setString(1, playlistName);
            verify(preparedStatement).setInt(2, playlistId);
            verify(preparedStatement).setInt(3, ownerId);

            assertTrue(status);

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void editPlaylistFailedTest() {
        try {
            // Arrange
            String expectedSQL = "UPDATE playlist SET name = ? WHERE id = ? AND owner_id = ?";
            String playlistName = "Playlist name";
            int playlistId = 1;
            int ownerId = 1;

            // setup Mocks
            DataSource dataSource = mock(DataSource.class);
            Connection connection = mock(Connection.class);
            PreparedStatement preparedStatement = mock(PreparedStatement.class);

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeUpdate()).thenReturn(0);

            // setup classes
            playlistDAO.setDataSource(dataSource);

            // Act
            boolean status = playlistDAO.editPlaylist(playlistName, playlistId, ownerId);

            // Assert
            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).setString(1, playlistName);
            verify(preparedStatement).setInt(2, playlistId);
            verify(preparedStatement).setInt(3, ownerId);

            assertFalse(status);

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void editPlaylistExceptionTest() {
        try {
            // Arrange
            String expectedSQL = "UPDATE playlist SET name = ? WHERE id = ? AND owner_id = ?";
            String playlistName = "Playlist name";
            int playlistId = 1;
            int ownerId = 1;

            // setup Mocks
            DataSource dataSource = mock(DataSource.class);
            Connection connection = mock(Connection.class);
            PreparedStatement preparedStatement = mock(PreparedStatement.class);

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeUpdate()).thenThrow(new SQLException());

            // setup classes
            playlistDAO.setDataSource(dataSource);

            // Act
            boolean status = playlistDAO.editPlaylist(playlistName, playlistId, ownerId);

            // Assert
            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).setString(1, playlistName);
            verify(preparedStatement).setInt(2, playlistId);
            verify(preparedStatement).setInt(3, ownerId);

            assertFalse(status);

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void getTotalDurationTest() {
        try {
            // Arrange
            String expectedSQL = "SELECT SUM(t.duration) as `duration` FROM track t INNER JOIN playlisttrack pt ON pt.track_id = t.id WHERE pt.playlist_id = ?";
            final int PLAYLIST_ID = 0;
            final int DURATION = 10;
            ArrayList<Playlist> playlists = new ArrayList<>();
            playlists.add(getFakePlaylist());

            // setup Mocks
            DataSource dataSource = mock(DataSource.class);
            Connection connection = mock(Connection.class);
            PreparedStatement preparedStatement = mock(PreparedStatement.class);
            ResultSet resultSet = mock(ResultSet.class);

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true).thenReturn(false);
            when(resultSet.getInt("duration")).thenReturn(DURATION);

            // setup classes
            playlistDAO.setDataSource(dataSource);

            // Act
            int actualDuration = playlistDAO.getTotalDuration(playlists);

            // Assert
            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).setInt(1, PLAYLIST_ID);

            assertEquals(DURATION, actualDuration);

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void getTotalDurationExceptionTest() {
        try {
            // Arrange
            String expectedSQL = "SELECT SUM(t.duration) as `duration` FROM track t INNER JOIN playlisttrack pt ON pt.track_id = t.id WHERE pt.playlist_id = ?";
            ArrayList<Playlist> playlists = new ArrayList<>();
            playlists.add(getFakePlaylist());

            // setup Mocks
            DataSource dataSource = mock(DataSource.class);
            Connection connection = mock(Connection.class);
            PreparedStatement preparedStatement = mock(PreparedStatement.class);

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenThrow(new SQLException());

            // setup classes
            playlistDAO.setDataSource(dataSource);

            // Act
            int actualDuration = playlistDAO.getTotalDuration(playlists);

            // Assert
            verify(connection).prepareStatement(expectedSQL);

            assertEquals(0, actualDuration);

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void deleteSuccessTest() {
        try {
            // Arrange
            String expectedSQL = "DELETE FROM playlist WHERE id = ?";
            int playlistId = 1;

            // setup Mocks
            DataSource dataSource = mock(DataSource.class);
            Connection connection = mock(Connection.class);
            PreparedStatement preparedStatement = mock(PreparedStatement.class);

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeUpdate()).thenReturn(1);

            // setup classes
            playlistDAO.setDataSource(dataSource);

            // Act
            boolean status = playlistDAO.delete(playlistId);

            // Assert
            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).setInt(1, playlistId);

            assertTrue(status);

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void deleteFailedTest() {
        try {
            // Arrange
            String expectedSQL = "DELETE FROM playlist WHERE id = ?";
            int playlistId = 1;

            // setup Mocks
            DataSource dataSource = mock(DataSource.class);
            Connection connection = mock(Connection.class);
            PreparedStatement preparedStatement = mock(PreparedStatement.class);

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeUpdate()).thenReturn(0);

            // setup classes
            playlistDAO.setDataSource(dataSource);

            // Act
            boolean status = playlistDAO.delete(playlistId);

            // Assert
            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).setInt(1, playlistId);

            assertFalse(status);

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void deleteExceptionTest() {
        try {
            // Arrange
            String expectedSQL = "DELETE FROM playlist WHERE id = ?";
            int playlistId = 1;

            // setup Mocks
            DataSource dataSource = mock(DataSource.class);
            Connection connection = mock(Connection.class);
            PreparedStatement preparedStatement = mock(PreparedStatement.class);

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeUpdate()).thenThrow(new SQLException());

            // setup classes
            playlistDAO.setDataSource(dataSource);

            // Act
            boolean status = playlistDAO.delete(playlistId);

            // Assert
            verify(connection).prepareStatement(expectedSQL);

            assertFalse(status);

        } catch (Exception e) {
            fail();
        }
    }

}
