package oose.dea.mikevanegmond.spotitube_backend_api.service.dto;

import oose.dea.mikevanegmond.spotitube_backend_api.domain.Track;

import java.util.ArrayList;

public class TracksDTO {
    public ArrayList<Track> tracks;

    public void setTracks(ArrayList<Track> tracks) {
        this.tracks = tracks;
    }
}
