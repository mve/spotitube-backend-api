package DAO;

import oose.dea.mikevanegmond.spotitube_backend_api.dao.TrackDAO;
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

public class TrackDAOTest {
    private TrackDAO trackDAO;

    @BeforeEach
    public void setup() {
        trackDAO = new TrackDAO();
    }

    @Test
    public void getTracksFromPlaylistTest() {
        try {
            // Arrange
            String expectedSQL = "SELECT tra.*, pt.offline_available FROM track tra INNER JOIN playlisttrack pt ON pt.track_id = tra.id WHERE pt.playlist_id = ?";
            final int PLAYLIST_ID = 0;

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
            when(resultSet.getString("title")).thenReturn("Name");
            when(resultSet.getString("performer")).thenReturn("Performer");
            when(resultSet.getInt("duration")).thenReturn(10);
            when(resultSet.getString("album")).thenReturn("Album");
            when(resultSet.getInt("playcount")).thenReturn(10);
            when(resultSet.getString("publication_date")).thenReturn("1-1-2021");
            when(resultSet.getString("description")).thenReturn("Description");
            when(resultSet.getBoolean("offline_available")).thenReturn(true);

            // setup classes
            trackDAO.setDataSource(dataSource);

            // Act
            ArrayList<Track> tracks = trackDAO.getTracksFromPlaylist(PLAYLIST_ID);

            // Assert
            verify(connection).prepareStatement(expectedSQL);

            assertEquals(0, tracks.get(0).getId());
            assertEquals("Name", tracks.get(0).getTitle());
            assertEquals("Performer", tracks.get(0).getPerformer());
            assertEquals(10, tracks.get(0).getDuration());
            assertEquals("Album", tracks.get(0).getAlbum());
            assertEquals(10, tracks.get(0).getPlaycount());
            assertEquals("1-1-2021", tracks.get(0).getPublicationDate());
            assertEquals("Description", tracks.get(0).getDescription());
            assertEquals(true, tracks.get(0).isOfflineAvailable());

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void getTracksFromPlaylistExceptionTest() {
        try {
            // Arrange
            String expectedSQL = "SELECT tra.*, pt.offline_available FROM track tra INNER JOIN playlisttrack pt ON pt.track_id = tra.id WHERE pt.playlist_id = ?";
            final int PLAYLIST_ID = 0;

            // setup Mocks
            DataSource dataSource = mock(DataSource.class);
            Connection connection = mock(Connection.class);
            PreparedStatement preparedStatement = mock(PreparedStatement.class);

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenThrow(new SQLException());

            // setup classes
            trackDAO.setDataSource(dataSource);

            // Act
            ArrayList<Track> tracks = trackDAO.getTracksFromPlaylist(PLAYLIST_ID);

            // Assert
            verify(connection).prepareStatement(expectedSQL);

            assertEquals(0, tracks.size());

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void getTracksNotFromPlaylistTest() {
        try {
            // Arrange
            String expectedSQL = "SELECT *, 0 AS `available_offline` FROM track WHERE id NOT IN (SELECT track_id FROM playlisttrack WHERE playlist_id = ?);";
            final int PLAYLIST_ID = 0;

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
            when(resultSet.getString("title")).thenReturn("Name");
            when(resultSet.getString("performer")).thenReturn("Performer");
            when(resultSet.getInt("duration")).thenReturn(10);
            when(resultSet.getString("album")).thenReturn("Album");
            when(resultSet.getInt("playcount")).thenReturn(10);
            when(resultSet.getString("publication_date")).thenReturn("1-1-2021");
            when(resultSet.getString("description")).thenReturn("Description");
            when(resultSet.getBoolean("available_offline")).thenReturn(true);

            // setup classes
            trackDAO.setDataSource(dataSource);

            // Act
            ArrayList<Track> tracks = trackDAO.getTracksNotFromPlaylist(PLAYLIST_ID);

            // Assert
            verify(connection).prepareStatement(expectedSQL);

            assertEquals(0, tracks.get(0).getId());
            assertEquals("Name", tracks.get(0).getTitle());
            assertEquals("Performer", tracks.get(0).getPerformer());
            assertEquals(10, tracks.get(0).getDuration());
            assertEquals("Album", tracks.get(0).getAlbum());
            assertEquals(10, tracks.get(0).getPlaycount());
            assertEquals("1-1-2021", tracks.get(0).getPublicationDate());
            assertEquals("Description", tracks.get(0).getDescription());
            assertEquals(true, tracks.get(0).isOfflineAvailable());

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void getTracksNotFromPlaylistExceptionTest() {
        try {
            // Arrange
            String expectedSQL = "SELECT *, 0 AS `available_offline` FROM track WHERE id NOT IN (SELECT track_id FROM playlisttrack WHERE playlist_id = ?);";
            final int PLAYLIST_ID = 0;

            // setup Mocks
            DataSource dataSource = mock(DataSource.class);
            Connection connection = mock(Connection.class);
            PreparedStatement preparedStatement = mock(PreparedStatement.class);

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenThrow(new SQLException());


            // setup classes
            trackDAO.setDataSource(dataSource);

            // Act
            ArrayList<Track> tracks = trackDAO.getTracksNotFromPlaylist(PLAYLIST_ID);

            // Assert
            verify(connection).prepareStatement(expectedSQL);

            assertEquals(0, tracks.size());

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void addTrackToPlaylistSuccessTest() {
        try {
            // Arrange
            String expectedSQL = "INSERT INTO playlisttrack (track_id, playlist_id, offline_available) VALUES (?, ?, ?)";
            final int PLAYLIST_ID = 0;
            final int TRACK_ID = 0;
            final boolean IS_AVAILABLE_OFFLINE = true;

            // setup Mocks
            DataSource dataSource = mock(DataSource.class);
            Connection connection = mock(Connection.class);
            PreparedStatement preparedStatement = mock(PreparedStatement.class);

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeUpdate()).thenReturn(1);

            // setup classes
            trackDAO.setDataSource(dataSource);

            // Act
            boolean status = trackDAO.addTrackToPlaylist(PLAYLIST_ID, TRACK_ID, IS_AVAILABLE_OFFLINE);

            // Assert
            verify(connection).prepareStatement(expectedSQL);

            assertTrue(status);

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void addTrackToPlaylistFailedTest() {
        try {
            // Arrange
            String expectedSQL = "INSERT INTO playlisttrack (track_id, playlist_id, offline_available) VALUES (?, ?, ?)";
            final int PLAYLIST_ID = 0;
            final int TRACK_ID = 0;
            final boolean IS_AVAILABLE_OFFLINE = true;

            // setup Mocks
            DataSource dataSource = mock(DataSource.class);
            Connection connection = mock(Connection.class);
            PreparedStatement preparedStatement = mock(PreparedStatement.class);

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeUpdate()).thenReturn(0);

            // setup classes
            trackDAO.setDataSource(dataSource);

            // Act
            boolean status = trackDAO.addTrackToPlaylist(PLAYLIST_ID, TRACK_ID, IS_AVAILABLE_OFFLINE);

            // Assert
            verify(connection).prepareStatement(expectedSQL);

            assertFalse(status);

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void addTrackToPlaylistExceptionTest() {
        try {
            // Arrange
            String expectedSQL = "INSERT INTO playlisttrack (track_id, playlist_id, offline_available) VALUES (?, ?, ?)";
            final int PLAYLIST_ID = 0;
            final int TRACK_ID = 0;
            final boolean IS_AVAILABLE_OFFLINE = true;

            // setup Mocks
            DataSource dataSource = mock(DataSource.class);
            Connection connection = mock(Connection.class);
            PreparedStatement preparedStatement = mock(PreparedStatement.class);

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeUpdate()).thenThrow(new SQLException());

            // setup classes
            trackDAO.setDataSource(dataSource);

            // Act
            boolean status = trackDAO.addTrackToPlaylist(PLAYLIST_ID, TRACK_ID, IS_AVAILABLE_OFFLINE);

            // Assert
            verify(connection).prepareStatement(expectedSQL);

            assertFalse(status);

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void removeTrackFromPlaylistSuccessTest() {
        try {
            // Arrange
            String expectedSQL = "DELETE plt FROM playlisttrack plt JOIN playlist pl ON plt.playlist_id = pl.id WHERE plt.track_id = ? AND plt.playlist_id = ? AND pl.owner_id = ?";
            final int PLAYLIST_ID = 0;
            final int TRACK_ID = 0;
            final int OWNER_ID = 0;

            // setup Mocks
            DataSource dataSource = mock(DataSource.class);
            Connection connection = mock(Connection.class);
            PreparedStatement preparedStatement = mock(PreparedStatement.class);

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeUpdate()).thenReturn(1);

            // setup classes
            trackDAO.setDataSource(dataSource);

            // Act
            boolean status = trackDAO.removeTrackFromPlaylist(PLAYLIST_ID, TRACK_ID, OWNER_ID);

            // Assert
            verify(connection).prepareStatement(expectedSQL);

            assertTrue(status);

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void removeTrackFromPlaylistFailedTest() {
        try {
            // Arrange
            String expectedSQL = "DELETE plt FROM playlisttrack plt JOIN playlist pl ON plt.playlist_id = pl.id WHERE plt.track_id = ? AND plt.playlist_id = ? AND pl.owner_id = ?";
            final int PLAYLIST_ID = 0;
            final int TRACK_ID = 0;
            final int OWNER_ID = 0;

            // setup Mocks
            DataSource dataSource = mock(DataSource.class);
            Connection connection = mock(Connection.class);
            PreparedStatement preparedStatement = mock(PreparedStatement.class);

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeUpdate()).thenReturn(0);

            // setup classes
            trackDAO.setDataSource(dataSource);

            // Act
            boolean status = trackDAO.removeTrackFromPlaylist(PLAYLIST_ID, TRACK_ID, OWNER_ID);

            // Assert
            verify(connection).prepareStatement(expectedSQL);

            assertFalse(status);

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void removeTrackFromPlaylistExceptionTest() {
        try {
            // Arrange
            String expectedSQL = "DELETE plt FROM playlisttrack plt JOIN playlist pl ON plt.playlist_id = pl.id WHERE plt.track_id = ? AND plt.playlist_id = ? AND pl.owner_id = ?";
            final int PLAYLIST_ID = 0;
            final int TRACK_ID = 0;
            final int OWNER_ID = 0;

            // setup Mocks
            DataSource dataSource = mock(DataSource.class);
            Connection connection = mock(Connection.class);
            PreparedStatement preparedStatement = mock(PreparedStatement.class);

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeUpdate()).thenThrow(new SQLException());

            // setup classes
            trackDAO.setDataSource(dataSource);

            // Act
            boolean status = trackDAO.removeTrackFromPlaylist(PLAYLIST_ID, TRACK_ID, OWNER_ID);

            // Assert
            verify(connection).prepareStatement(expectedSQL);

            assertFalse(status);

        } catch (Exception e) {
            fail();
        }
    }
}
