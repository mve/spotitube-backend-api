package oose.dea.mikevanegmond.spotitube_backend_api.dao;

import oose.dea.mikevanegmond.spotitube_backend_api.domain.Playlist;

import java.util.ArrayList;

public interface IPlaylistDAO {
    ArrayList<Playlist> getPlaylists(int userId);
    boolean createPlaylist(String name, int owner);
    boolean editPlaylist(String name, int id, int owner);
    int getTotalDuration(ArrayList<Playlist> playlists);
    boolean delete(int id);

}
