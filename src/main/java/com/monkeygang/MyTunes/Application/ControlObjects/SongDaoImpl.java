package com.monkeygang.MyTunes.Application.ControlObjects;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;



public class SongDaoImpl implements SongDao{


    private Connection con; // forbindelsen til databasen
    public SongDaoImpl() throws SQLException {
        try{
            // Bemærk: selve connection-strengen skal tilpasses Jeres connection settings..

            jdbc:sqlserver://$AZ_DATABASE_NAME.database.windows.net:1433;database=demo;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;




            con = DriverManager.getConnection("jdbc:sqlserver://h0lm.database.windows.net:1433;database=MyTunes;user=Sune@h0lm;password={lukasersej123!};encrypt=true;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");
            //con = DriverManager.getConnection("jdbc:sqlserver://EASV-THA-Q418\\TH:1433;database=LibDB;integratedSecurity=true");
        } catch (SQLException e){
            System.err.println("can not create connection");
            System.out.println(e.getMessage());
        }

       /* System.out.println("connected to the database... ");
        PreparedStatement ps1 = con.prepareStatement("SELECT MAX(SongID) FROM songs");



        ResultSet idgetter = ps1.executeQuery();

        int id = idgetter.getInt(0);
        System.out.println(id + 1);*/


    }



    @Override
    public List<Song> getAllSongs() {
        List<Song> songs = new ArrayList<>();
        try{
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Songs;");
            ResultSet rs = ps.executeQuery();

            Song song;
            while(rs.next()){
                String id = rs.getString(1);
                String title = rs.getString(2);
                String artist = rs.getString(3);
                String genre = rs.getString(4);



                song = new Song(Integer.parseInt(id), title, artist, genre);
                songs.add(song);

                System.out.println(songs);
            }

        } catch (SQLException e){
            System.err.println("can not access records");
        }
        return songs;
    }

    @Override
    public void updateSongs() {

    }

    @Override
    public void deleteSong() {



    }

    @Override
    public void addSong(Song song) {

        try{
            PreparedStatement ps1 = con.prepareStatement("SELECT MAX(SongID) FROM songs");

            ResultSet idgetter = ps1.executeQuery();

            int id = idgetter.getInt(0);
            System.out.println(id + 1);

            PreparedStatement ps = con.prepareStatement("INSERT INTO songs VALUES(?,?,?);");
            ps.setString(1, song.getTitle());
            ps.setString(2,  song.getArtist());
            ps.setString(3, song.getFilepath());
            ps.executeUpdate();

        } catch (SQLException e){
            System.err.println("can not insert record");
        }
    }
}
