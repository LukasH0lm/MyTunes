package com.monkeygang.MyTunes.Application.ControlObjects;


import java.sql.SQLException;
import java.util.List;

public interface SongDao {

    public List getAllSongs();

    public void updateSongs();

    void updateSongs(Song[] songs);


    void deleteSong(Song song) throws SQLException;

    public void addSong(Song song) throws SQLException;



}
