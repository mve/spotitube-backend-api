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

public class TrackDAO implements ITrackDAO {

    @Resource(name = "jdbc/spotitube")
    DataSource dataSource;

    @Override
    public ArrayList<Track> getTracks() {

        // SELECT track.* FROM track INNER JOIN playlisttrack ON playlisttrack.track_id = track.id WHERE playlisttrack.playlist_id = ?

        return null;
    }

    @Override
    public ArrayList<Track> getTracksFromPlaylist(int playlistId) {

        String sql = "SELECT track.* FROM track INNER JOIN playlisttrack ON playlisttrack.track_id = track.id WHERE playlisttrack.playlist_id = ?";

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, playlistId);
            ResultSet resultSet = statement.executeQuery();

            ArrayList<Track> tracks = new ArrayList<>();

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

            return tracks;

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

}
