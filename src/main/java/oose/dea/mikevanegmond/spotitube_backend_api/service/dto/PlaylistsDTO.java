package oose.dea.mikevanegmond.spotitube_backend_api.service.dto;

import oose.dea.mikevanegmond.spotitube_backend_api.domain.Playlist;

import java.util.ArrayList;

public class PlaylistsDTO {
    ArrayList<Playlist> playlists;
    int length;

    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(ArrayList<Playlist> playlists) {
        this.playlists = playlists;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
