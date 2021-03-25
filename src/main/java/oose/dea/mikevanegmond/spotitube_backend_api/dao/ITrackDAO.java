package oose.dea.mikevanegmond.spotitube_backend_api.dao;

import oose.dea.mikevanegmond.spotitube_backend_api.domain.Track;

import java.util.ArrayList;

public interface ITrackDAO {
//    ArrayList<Track> getTracks();
    ArrayList<Track> getTracksFromPlaylist(int playlistId);
    ArrayList<Track> getTracksNotFromPlaylist(int playlistId);
    boolean addTrackToPlaylist(int playlistId, int trackId, boolean isAvailableOffline);
    boolean removeTrackFromPlaylist(int playlistId, int trackId, int ownerId);
}
