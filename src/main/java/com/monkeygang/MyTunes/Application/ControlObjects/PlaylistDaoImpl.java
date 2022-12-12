package com.monkeygang.MyTunes.Application.ControlObjects;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlaylistDaoImpl implements PlaylistDao {

    private static Connection con;

    public PlaylistDaoImpl() {
        try {
            con = DriverManager.getConnection("jdbc:sqlserver://h0lm.database.windows.net:1433;database=MyTunes;user=Sune@h0lm;password=lukasersej123!;encrypt=true;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void addPlaylist(Playlist playlist) throws SQLException {

        PreparedStatement ps = con.prepareStatement("SELECT * FROM Playlists;");
        ResultSet rs = ps.executeQuery();

        boolean isDuplicate = false;

        while (rs.next()) {
            if (Objects.equals(playlist.getName(), rs.getString("PlaylistName"))) {
                isDuplicate = true;
                System.out.println(playlist.getName() + " is already in database");
                break;
            }
        }


        if (!isDuplicate) {

            try {

                boolean isAtEnd = false;
                int currentID = 100;
                int previousID = 99;

                ResultSet rs2 = ps.executeQuery();
                while (rs2.next()) {
                    if (rs2.getInt("SongID") != currentID) {
                        break;
                    }
                    currentID++;
                }

                //virker kun for en sang lige nu
                //spørgsmål: hvordan tilføjer man flere foreign keys til en entry

                PreparedStatement ps2 = con.prepareStatement("INSERT INTO Playlists VALUES (?,?,?);");

                ps2.setInt(1, currentID);
                ps2.setString(2, playlist.getName());
                ps2.setInt(3, 101);



                ps2.executeUpdate();
                System.out.println(playlist.getName() + " has been added to database");
                System.out.println();


            } catch (SQLException e) {

                System.err.println(e.getErrorCode() + " : " + e.getMessage());
                for (StackTraceElement stack : e.getStackTrace()) {
                    //System.out.println(stack);
                }


            }
        }
    }


    @Override
    public void deletePlayList(Playlist playlist) throws SQLException {

        PreparedStatement ps = con.prepareStatement("SELECT * FROM Songs;");
        ResultSet rs = ps.executeQuery();

        String SQLSongTitle = "'%s'".formatted(playlist.getName());

        PreparedStatement ps2 = con.prepareStatement("DELETE FROM Songs WHERE SongTitle=" + SQLSongTitle + ";" );

    }

    @Override
    public List getAllPlaylists() {
        List<Playlist> playlists = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Playlists;");
            ResultSet rs = ps.executeQuery();

            Playlist playlist;
            while (rs.next()) {
                String id = rs.getString(1);
                String name = rs.getString(2);



                playlist = new Playlist(Integer.parseInt(id), name);
                playlists.add(playlist);

                System.out.println(playlists);
            }

        } catch (SQLException e) {
            System.err.println("can not access records");
        }
        return playlists;
    }

    @Override
    public void updatePlaylists() {

    }

    @Override
    public void deleteSongFromPlaylist(Playlist playlist, Song song) {

    }

    @Override
    public void addSongToPlaylist(Playlist playlist, Song song) throws SQLException {



        playlist.addSong(song);

        int songID = SongDaoImpl.getSongID(song);
        int playlistID = PlaylistDaoImpl.getPlaylistID(playlist);

        PreparedStatement ps = con.prepareStatement("UPDATE Playlists set SongID="+ songID +" WHERE PlaylistID="+ playlistID +";");
        ResultSet rs = ps.executeQuery();




    }

    private static int getPlaylistID(Playlist playlist) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM Playlists;");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            if (Objects.equals(playlist.getName(), rs.getString("PlaylistName"))) {
                return rs.getInt("PlaylistID");
            }
        }
        return -1;
    }
}
