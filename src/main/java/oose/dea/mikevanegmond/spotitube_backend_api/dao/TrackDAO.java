package oose.dea.mikevanegmond.spotitube_backend_api.dao;

import oose.dea.mikevanegmond.spotitube_backend_api.domain.Track;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TrackDAO implements ITrackDAO {

    @Resource(name = "jdbc/spotitube")
    DataSource dataSource;

    @Override
    public ArrayList<Track> getTracksFromPlaylist(int playlistId) {

        String sql = "SELECT tra.*, pt.offline_available FROM track tra INNER JOIN playlisttrack pt ON pt.track_id = tra.id WHERE pt.playlist_id = ?";
        ArrayList<Track> tracks = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, playlistId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Track track = new Track();
                track.setId(resultSet.getInt("id"));
                track.setTitle(resultSet.getString("title"));
                track.setPerformer(resultSet.getString("performer"));
                track.setDuration(resultSet.getInt("duration"));
                track.setAlbum(resultSet.getString("album"));
                track.setPlaycount(resultSet.getInt("playcount"));
                track.setPublicationDate(resultSet.getString("publication_date"));
                track.setDescription(resultSet.getString("description"));
                track.setOfflineAvailable(resultSet.getBoolean("offline_available"));

                tracks.add(track);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return tracks;
    }

    @Override
    public ArrayList<Track> getTracksNotFromPlaylist(int playlistId) {

        String sql = "SELECT *, 0 AS `available_offline` FROM track WHERE id NOT IN (SELECT track_id FROM playlisttrack WHERE playlist_id = ?);";
        ArrayList<Track> tracks = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, playlistId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Track track = new Track();
                track.setId(resultSet.getInt("id"));
                track.setTitle(resultSet.getString("title"));
                track.setPerformer(resultSet.getString("performer"));
                track.setDuration(resultSet.getInt("duration"));
                track.setAlbum(resultSet.getString("album"));
                track.setPlaycount(resultSet.getInt("playcount"));
                track.setPublicationDate(resultSet.getString("publication_date"));
                track.setDescription(resultSet.getString("description"));
                track.setOfflineAvailable(resultSet.getBoolean("available_offline"));

                tracks.add(track);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return tracks;
    }

    @Override
    public boolean addTrackToPlaylist(int playlistId, int trackId, boolean isAvailableOffline) {
        String sql = "INSERT INTO playlisttrack (track_id, playlist_id, offline_available) VALUES (?, ?, ?)";
        boolean success = false;

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, trackId);
            statement.setInt(2, playlistId);
            statement.setBoolean(3, isAvailableOffline);

            success = statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return success;
    }

    @Override
    public boolean removeTrackFromPlaylist(int playlistId, int trackId, int ownerId) {
        String sql = "DELETE plt FROM playlisttrack plt JOIN playlist pl ON plt.playlist_id = pl.id WHERE plt.track_id = ? AND plt.playlist_id = ? AND pl.owner_id = ?";
        boolean success = false;

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, trackId);
            statement.setInt(2, playlistId);
            statement.setInt(3, ownerId);

            success = statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return success;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

}
