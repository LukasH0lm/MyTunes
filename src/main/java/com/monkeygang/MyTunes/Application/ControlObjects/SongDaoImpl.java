package com.monkeygang.MyTunes.Application.ControlObjects;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.Objects;


public class SongDaoImpl implements SongDao {


    //MyTunesController controller = new MyTunesController();


    private static Connection con; // forbindelsen til databasen

    public SongDaoImpl() throws SQLException {
        try {
            con = DriverManager.getConnection("jdbc:sqlserver://h0lm.database.windows.net:1433;database=MyTunes;user=Sune@h0lm;password=lukasersej123!;encrypt=true;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");
        } catch (SQLException e) {
            System.err.println("can not create connection");
            System.out.println(e.getMessage());
        }

        System.out.println("----------------------------");
        System.out.println(" connected to the database!");
        System.out.println("----------------------------");

        PreparedStatement ps = con.prepareStatement("SELECT * FROM Songs;");
        ResultSet rs = ps.executeQuery();
        System.out.println("Songs in database:");
        while (rs.next()) {
            System.out.println(rs.getInt("SongID") + " - " + rs.getString("SongTitle"));
        }


    }


    @Override
    public List<Song> getAllSongs() {
        List<Song> songs = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Songs;");
            ResultSet rs = ps.executeQuery();

            Song song;
            while (rs.next()) {
                String id = rs.getString(1);
                String title = rs.getString(2);
                String artist = rs.getString(3);
                String album = rs.getString(4);
                String genre = rs.getString(5);


                song = new Song(Integer.parseInt(id), title, artist, album, genre);
                songs.add(song);

                System.out.println(songs);
            }

        } catch (SQLException e) {
            System.err.println("can not access records");
        }
        return songs;
    }

    public static int getSongID(Song song) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM Songs;");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            if (Objects.equals(song.getTitle(), rs.getString("SongTitle")) && (Objects.equals(song.getArtist(), rs.getString("ArtistName")))) {
                return rs.getInt("SongID");
            }
        }
        return -1;
    }

    @Override
    public void updateSongs() {

    }

    @Override
    public void updateSongs(Song[] songs) {


    }

    @Override
    public void deleteSong(Song song) throws SQLException {
        //implementation currently deletes all songs by the same name
        PreparedStatement ps = con.prepareStatement("SELECT * FROM Songs;");
        ResultSet rs = ps.executeQuery();

        String SQLSongTitle = "'%s'".formatted(song.title);

        PreparedStatement ps2 = con.prepareStatement("DELETE FROM Songs WHERE SongTitle=" + SQLSongTitle + ";" );


    }

    @Override
    public void addSong(Song song) throws SQLException {

        PreparedStatement ps = con.prepareStatement("SELECT * FROM Songs;");
        ResultSet rs = ps.executeQuery();

        boolean isduplicate = false;

        while (rs.next()) {
            if (Objects.equals(song.getTitle(), rs.getString("SongTitle")) && (Objects.equals(song.getArtist(), rs.getString("ArtistName")))) {
                isduplicate = true;
                System.out.println(song.getTitle() + " is already in database");
                break;
            }
        }


        if (!isduplicate) {

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


                PreparedStatement ps2 = con.prepareStatement("INSERT INTO Songs VALUES (?,?,?,?,?);");

                ps2.setInt(1, currentID);
                ps2.setString(2, song.getTitle());
                ps2.setString(3, song.getArtist());
                ps2.setString(4, song.getAlbum());
                ps2.setString(5, song.getGenre());


                ps2.executeUpdate();
                System.out.println(song.getTitle() + " has been added to database");
                System.out.println();


            } catch (SQLException e) {

                System.err.println(e.getErrorCode() + " : " + e.getMessage());
                for (StackTraceElement stack : e.getStackTrace()) {
                    //System.out.println(stack);
                }


            }
        }
    }
}
