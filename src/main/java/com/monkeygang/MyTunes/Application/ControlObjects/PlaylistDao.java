package com.monkeygang.MyTunes.Application.ControlObjects;


import java.sql.SQLException;
import java.util.List;

public interface PlaylistDao {


    void addPlaylist(Playlist playlist) throws SQLException;


    void deletePlayList(Playlist playlist) throws SQLException;


    void editPlaylist(Playlist playlist, String newName) throws SQLException;

    List getAllPlaylists();

    void updatePlaylists();


    void deleteSongFromPlaylist(Playlist playlist, Song song) throws SQLException;

    void addSongToPlaylist(Playlist playlist, Song song) throws SQLException;


}
