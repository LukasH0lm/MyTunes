package com.monkeygang.MyTunes.Application.ControlObjects;


import java.sql.SQLException;
import java.util.List;

public interface SongDao {

    List getAllSongs();

    void updateSongs();

    void updateSongs(Song[] songs);


    void deleteSong(Song song) throws SQLException;

    void addSong(Song song) throws SQLException;



}
