package oose.dea.mikevanegmond.spotitube_backend_api.dao;

import oose.dea.mikevanegmond.spotitube_backend_api.domain.Playlist;
import oose.dea.mikevanegmond.spotitube_backend_api.domain.Track;

import java.util.ArrayList;

public interface IPlaylistDAO {
    ArrayList<Playlist> getPlaylists(int userId);
    void createPlaylist(String name, int owner);
    void editPlaylist(String name, int id, int owner);
    int getTotalDuration(ArrayList<Playlist> playlists);
    void delete(int id);

}
