package com.monkeygang.MyTunes.Application.BuisnessLogic;
import com.monkeygang.MyTunes.Application.ControlObjects.Song;
import java.io.File;
import java.io.IOException;
import com.mpatric.mp3agic.*;

public class AudioParser {

    static int rollingId = 0;

    public static Song parseMp3(File song) throws InvalidDataException, UnsupportedTagException, IOException {

        //current implementation only works for tracks, albums don't work

        Mp3File mp3file = new Mp3File(song.getPath());
        if (mp3file.hasId3v2Tag()) {
            ID3v2 id3v2Tag = mp3file.getId3v2Tag();


            byte[] albumImageData = id3v2Tag.getAlbumImage();

            if (albumImageData != null) {
                System.out.println("Have album image data, length: " + albumImageData.length + " bytes");
                System.out.println("Album image mime type: " + id3v2Tag.getAlbumImageMimeType());
            }
            rollingId++;
            return new Song(rollingId,id3v2Tag.getTitle(),id3v2Tag.getArtist(), id3v2Tag.getGenre());

        }
        return new Song(-1,"test","test","test");

    }
}