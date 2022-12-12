package com.monkeygang.MyTunes.Application.ControlObjects;

public class Song {

    int id;
    String title;
    String artist;
    String album;
    String genre;


    public Song(int id, String title, String artist, String album, String genre) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
    }


    public Song(int id, String title, String artist, String album, int genre) {

        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.genre = id3v2GenreConverter(genre);


    }

    public  String getTitle() {
        return title;
    }

    public  String getArtist() {
        return artist;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return
                title + " - " + artist;
    }



    public String id3v2GenreConverter(int genreNumber){

        String genre = "null";

        switch (genreNumber){
            case 0  -> genre = "blues";
            case 1  -> genre = "classic Rock";
            case 2  -> genre = "country";
            case 3  -> genre = "dance";
            case 4  -> genre = "disco";
            case 5  -> genre = "funk";
            case 6  -> genre = "grunge";
            case 7  -> genre = "hip-Hop";
            case 8  -> genre = "jazz";
            case 9  -> genre = "metal";
            case 10 -> genre = "New Age";
            case 11 -> genre = "Oldies";
            case 12 -> genre = "Other";
            case 13 -> genre = "Pop";
            case 14 -> genre = "R&B";
            case 15 -> genre = "Rap";
            case 16 -> genre = "Reggae";
            case 17 -> genre = "Rock";
            case 18 -> genre = "Techno";
            case 19 -> genre = "Industrial";
            case 20 -> genre = "Alternative";
            case 21 -> genre = "Ska";
            case 22 -> genre = "Death Metal";
            case 23 -> genre = "Pranks";
            case 24 -> genre = "Soundtrack";
            case 25 -> genre = "Euro-Techno";
            case 26 -> genre = "Ambient";
            case 27 -> genre = "Trip-Hop";
            case 28 -> genre = "Vocal";
            case 29 -> genre = "Jazz+Funk";
            case 30 -> genre = "Fusion";
            case 31 -> genre = "Trance";
            case 32 -> genre = "Classical";
            case 33 -> genre = "Instrumental";
            case 34 -> genre = "Acid";
            case 35 -> genre = "House";
            case 36 -> genre = "Game";
            case 37 -> genre = "Sound Clip";
            case 38 -> genre = "Gospel";
            case 39 -> genre = "Noise";
            case 40 -> genre = "AlternRock";
            case 41 -> genre = "Bass";
            case 42 -> genre = "Soul";
            case 43 -> genre = "Punk";
            case 44 -> genre = "Space";
            case 45 -> genre = "Meditative";
            case 46 -> genre = "Instrumental Pop";
            case 47 -> genre = "Instrumental Rock";
            case 48 -> genre = "Ethnic";
            case 49 -> genre = "Gothic";
            case 50 -> genre = "Darkwave";
            case 51 -> genre = "Techno-Industrial";
            case 52 -> genre = "Electronic";
            case 53 -> genre = "Pop-Folk";
            case 54 -> genre = "Eurodance";
            case 55 -> genre = "Dream";
            case 56 -> genre = "Southern Rock";
            case 57 -> genre = "Comedy";
            case 58 -> genre = "Cult";
            case 59 -> genre = "Gangsta";
            case 60 -> genre = "Top 40";
            case 61 -> genre = "Christian Rap";
            case 62 -> genre = "Pop/Funk";
            case 63 -> genre = "Jungle";
            case 64 -> genre = "Native American";
            case 65 -> genre = "Cabaret";
            case 66 -> genre = "New Wave";
            case 67 -> genre = "Psychadelic";
            case 68 -> genre = "Rave";
            case 69 -> genre = "Showtunes";
            case 70 -> genre = "Trailer";
            case 71 -> genre = "Lo-Fi";
            case 72 -> genre = "Tribal";
            case 73 -> genre = "Acid Punk";
            case 74 -> genre = "Acid Jazz";
            case 75 -> genre = "Polka";
            case 76 -> genre = "Retro";
            case 77 -> genre = "Musical";
            case 78 -> genre = "Rock & Roll";
            case 79 -> genre = "Hard Rock";


        }

        return genre;

    }

    public String getAlbum() {
        return this.album;
    }
}
