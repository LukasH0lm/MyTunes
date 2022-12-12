package com.monkeygang.MyTunes.Application.BuisnessLogic;
import com.monkeygang.MyTunes.Application.ControlObjects.Song;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Objects;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.ID3v23Tag;
import com.mpatric.mp3agic.UnsupportedTagException;

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

            if (albumImageData != null) {
                System.out.println("Have album image data, length: " + albumImageData.length + " bytes");
                System.out.println("Album image mime type: " + id3v2Tag.getAlbumImageMimeType());
            }
            rollingId++;
            System.out.println("Parsed Song : " + id3v2Tag.getTitle() + id3v2Tag.getArtist() + id3v2Tag.getGenreDescription());
            return new Song(rollingId,id3v2Tag.getTitle(),id3v2Tag.getArtist(), id3v2Tag.getAlbum(), id3v2Tag.getGenreDescription());


        }
        return new Song(-1,"test","test","test","test");

    }
}