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

    @Override
    public ArrayList<Playlist> getPlaylists(int userId) {

        String sql = "select * from playlist";

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            ArrayList<Playlist> playlists = new ArrayList<>();

            while (resultSet.next()) {
                Playlist playlist = new Playlist();
                playlist.setId(resultSet.getInt("id"));
                playlist.setName(resultSet.getString("name"));
                playlist.setOwner(userId == resultSet.getInt("owner_id"));
                playlist.setTracks(new ArrayList<Track>());

                playlists.add(playlist);
            }

            return playlists;

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    @Override
    public void createPlaylist(String name, int ownerId) {

        String sql = "INSERT INTO playlist (name, owner_id) VALUES (?, ?)";

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setInt(2, ownerId);
            statement.executeUpdate();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }

    @Override
    public void editPlaylist(String name, int id, int ownerId) {

        String sql = "UPDATE playlist SET name = ? WHERE id = ? AND owner_id = ?";

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setInt(2, id);
            statement.setInt(3, ownerId);
            statement.executeUpdate();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }

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
            }
            catch (SQLException exception) {
                exception.printStackTrace();
            }
        }

        return duration;
    }

    @Override
    public void delete(int id) {
        // TODO Error handeling inbouwen.

        String sql = "DELETE FROM playlist WHERE id = ?";

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }


    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
