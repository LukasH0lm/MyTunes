package com.monkeygang.MyTunes.Application.ControlObjects;


import java.util.List;

public interface PlaylistDao {

    public List getAllPlaylists();

    public void updatePlaylists();

    public void deleteSong();

    public void addSong();


}
