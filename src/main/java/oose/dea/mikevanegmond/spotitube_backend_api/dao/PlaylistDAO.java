package oose.dea.mikevanegmond.spotitube_backend_api.dao;

import oose.dea.mikevanegmond.spotitube_backend_api.domain.Playlist;
import oose.dea.mikevanegmond.spotitube_backend_api.domain.Track;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaylistDAO implements IPlaylistDAO {

    @Resource(name = "jdbc/spotitube")
    DataSource dataSource;

    /**
     * Get all playlists, set owner based on provided userId.
     * @param userId
     * @return ArrayList<Playlist>, all playlists.
     */
    @Override
    public ArrayList<Playlist> getPlaylists(int userId) {

        String sql = "select * from playlist";
        ArrayList<Playlist> playlists = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Playlist playlist = new Playlist();
                playlist.setId(resultSet.getInt("id"));
                playlist.setName(resultSet.getString("name"));
                playlist.setOwner(userId == resultSet.getInt("owner_id"));
                playlist.setTracks(new ArrayList<Track>());

                playlists.add(playlist);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return playlists;
    }

    /**
     * Create a new playlist.
     * @param name
     * @param ownerId
     * @return boolean, true if successful, false if failed.
     */
    @Override
    public boolean createPlaylist(String name, int ownerId) {
        String sql = "INSERT INTO playlist (name, owner_id) VALUES (?, ?)";
        boolean success = false;

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setInt(2, ownerId);
            success = statement.executeUpdate() > 0;

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return success;

    }

    /**
     * Edit a playlist.
     * @param name
     * @param id
     * @param ownerId
     * @return boolean, true if successful, false if failed.
     */
    @Override
    public boolean editPlaylist(String name, int id, int ownerId) {
        String sql = "UPDATE playlist SET name = ? WHERE id = ? AND owner_id = ?";
        boolean success = false;

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setInt(2, id);
            statement.setInt(3, ownerId);

            success = statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return success;
    }

    /**
     * Get the total duration of all tracks in the provided playlists.
     * @param playlists
     * @return int, total duration of all tracks in provided playlists.
     */
    @Override
    public int getTotalDuration(ArrayList<Playlist> playlists) {
        int duration = 0;

        String sql = "SELECT SUM(t.duration) as `duration` FROM track t INNER JOIN playlisttrack pt ON pt.track_id = t.id WHERE pt.playlist_id = ?";

        for (Playlist playlist : playlists) {
            try (Connection connection = dataSource.getConnection()) {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, playlist.getId());
                ResultSet result = statement.executeQuery();

                if (result.next()) {
                    duration += result.getInt("duration");
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }

        return duration;
    }

    /**
     * Delete a playlist.
     * @param id
     * @return boolean, true if successful, false if failed.
     */
    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM playlist WHERE id = ?";
        boolean success = false;

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

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
