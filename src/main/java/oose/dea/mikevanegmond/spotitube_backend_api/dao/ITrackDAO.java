package oose.dea.mikevanegmond.spotitube_backend_api.dao;

import oose.dea.mikevanegmond.spotitube_backend_api.domain.Track;

import java.util.ArrayList;

public interface ITrackDAO {
    ArrayList<Track> getTracks();
    ArrayList<Track> getTracksFromPlaylist(int playlistId);
    ArrayList<Track> getTracksNotFromPlaylist(int playlistId);
    void addTrackToPlaylist(int playlistId, int trackId);
    void removeTrackFromPlaylist(int playlistId, int trackId, int ownerId);
}
