package me.jvegaf.musikbox.services.tagger;

import me.jvegaf.musikbox.services.parser.TimeParser;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class SearchResult {
    private String title;
    private String remixName;
    private List<String> artists;
    private String album;
    private String genre;
    private String year;
    private Integer bpm;
    private Integer duration;
    private String key;
    private String artworkURL;

    private static final Logger LOG = Logger.getLogger(SearchResult.class);

    public SearchResult() {
        this.duration = 0;
    }


    public SearchResult(String title,
                        String remixName,
                        List<String> artists,
                        String album,
                        String genre,
                        String year,
                        Integer bpm,
                        String duration,
                        String key,
                        String artworkURL) {
        this.title = title;
        this.remixName = remixName;
        this.artists = artists;
        this.album = album;
        this.genre = genre;
        this.year = year;
        this.bpm = bpm;
        this.duration = TimeParser.parseTime(duration);
        this.key = key;
        this.artworkURL = artworkURL;
    }

    public String Title() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String RemixName() {
        return remixName;
    }

    public void setRemixName(String remixName) {
        this.remixName = remixName;
    }

    public List<String> Artists() {
        return artists;
    }

    public void setArtists(List<String> artists) {
        this.artists = artists;
    }

    public String Album() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String Genre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String Year() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Optional<Integer> Bpm() {
        return Optional.ofNullable(this.bpm);
    }

    public void setBpm(Integer bpm) {
        this.bpm = bpm;
    }

    public Integer Duration() {
        return this.duration;
    }

    public void setDuration(String duration) {
        this.duration = TimeParser.parseTime(duration);
    }

    public String Key() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String ArtworkURL() {
        return artworkURL;
    }

    public void setArtworkURL(String artworkURL) {
        this.artworkURL = artworkURL;
    }
}
