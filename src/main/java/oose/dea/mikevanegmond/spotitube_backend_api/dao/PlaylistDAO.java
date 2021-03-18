package oose.dea.mikevanegmond.spotitube_backend_api.dao;

import oose.dea.mikevanegmond.spotitube_backend_api.domain.Playlist;

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
    public ArrayList<Playlist> getPlaylists() {

        String sql = "select * from playlist";

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            ArrayList<Playlist> playlists = new ArrayList<>();

            while (resultSet.next()){
                Playlist playlist = new Playlist();
                playlist.setId(resultSet.getInt("id"));
                playlist.setName(resultSet.getString("name"));
                playlist.setOwnerId(resultSet.getInt("owner_id"));

                playlists.add(playlist);
            }

            return playlists;

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
