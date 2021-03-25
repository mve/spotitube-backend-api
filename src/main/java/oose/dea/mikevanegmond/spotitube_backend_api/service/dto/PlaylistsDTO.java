package oose.dea.mikevanegmond.spotitube_backend_api.service.dto;

import oose.dea.mikevanegmond.spotitube_backend_api.domain.Playlist;

import java.util.ArrayList;

public class PlaylistsDTO {
    public ArrayList<Playlist> playlists;
    public int length;

    public void setPlaylists(ArrayList<Playlist> playlists) {
        this.playlists = playlists;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
