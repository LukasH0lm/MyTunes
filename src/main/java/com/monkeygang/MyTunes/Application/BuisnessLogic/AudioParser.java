package com.monkeygang.MyTunes.Application.BuisnessLogic;

import com.monkeygang.MyTunes.Application.ControlObjects.Song;

import java.io.*;
import java.util.Arrays;
import java.util.Objects;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.ID3v23Tag;
import com.mpatric.mp3agic.UnsupportedTagException;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class AudioParser {

    static int rollingId = 0;

    public static Song parseMp3(File song) throws InvalidDataException, UnsupportedTagException, IOException {

        //current implementation only works for tracks, albums don't work

        Mp3File mp3file = new Mp3File(song.getPath());
        if (mp3file.hasId3v1Tag()) {
            System.out.println("file has id3v1Tag");

        }

        if (mp3file.hasCustomTag()) {
            System.out.println("file has custom tag");

        }


        if (mp3file.hasId3v2Tag()) {

            ID3v2 id3v2Tag = mp3file.getId3v2Tag();


            byte[] albumImageData = id3v2Tag.getAlbumImage();
            rollingId++;
            Image albumCover;

            if (albumImageData != null) {
                System.out.println("Have album image data, length: " + albumImageData.length + " bytes");
                System.out.println("Album image mime type: " + id3v2Tag.getAlbumImageMimeType());
                ByteArrayInputStream bis = new ByteArrayInputStream(albumImageData);

                albumCover = new Image(bis);

            }else{
                albumCover = null;
            }
            rollingId++;
            System.out.println("Parsed Song : " + id3v2Tag.getTitle() + id3v2Tag.getArtist() + id3v2Tag.getGenreDescription());
            // converts the title so that Song.title can be relied upon in plating the file
            String songTitle = song.getName().substring(0, song.getName().length() - 4);
            return new Song(rollingId, songTitle, id3v2Tag.getArtist(), id3v2Tag.getAlbum(), id3v2Tag.getGenreDescription(),albumCover);


        }
        return null;

    }


    public static void ByteArrayToImage(String args[]) throws Exception {
        BufferedImage bImage = ImageIO.read(new File("sample.jpg"));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImage, "jpg", bos);
        byte[] data = bos.toByteArray();
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        BufferedImage bImage2 = ImageIO.read(bis);
        ImageIO.write(bImage2, "jpg", new File("output.jpg"));
        System.out.println("image created");
    }
}


