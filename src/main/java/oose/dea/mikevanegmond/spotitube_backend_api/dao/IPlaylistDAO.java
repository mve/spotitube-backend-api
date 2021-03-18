package oose.dea.mikevanegmond.spotitube_backend_api.dao;

import oose.dea.mikevanegmond.spotitube_backend_api.domain.Playlist;

import java.util.ArrayList;

public interface IPlaylistDAO {
    ArrayList<Playlist> getPlaylists();
}
