package com.monkeygang.MyTunes.Application.ControlObjects;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import javafx.collections.FXCollections;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.sql.*;



public class SongDaoImpl implements SongDao{


    private Connection con; // forbindelsen til databasen
    public SongDaoImpl()  {
        try{
            // Bemærk: selve connection-strengen skal tilpasses Jeres connection settings..

            jdbc:sqlserver://$AZ_DATABASE_NAME.database.windows.net:1433;database=demo;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;

            con = DriverManager.getConnection("jdbc:sqlserver://h0lm.database.windows.net\\1433;database=MyTunes;userName=Sune;password=lukasersej123!");
            //con = DriverManager.getConnection("jdbc:sqlserver://EASV-THA-Q418\\TH:1433;database=LibDB;integratedSecurity=true");
        } catch (SQLException e){
            System.err.println("can not create connection");
        }

        System.out.println("connected to the database... ");


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
    public void addSong() {

    }
}
