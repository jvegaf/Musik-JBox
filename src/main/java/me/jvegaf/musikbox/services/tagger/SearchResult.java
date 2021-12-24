package me.jvegaf.musikbox.services.tagger;

import java.util.List;

public class SearchResult {
    private String id;
    private String title;
    private List<String> artists;
    private String linkURL;

    public SearchResult() {

    }

    public SearchResult(String id, String title, List<String> artists, String linkURL) {
        this.id = id;
        this.title = title;
        this.artists = artists;
        this.linkURL = linkURL;
    }

    public String Id() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtists(List<String> artists) {
        this.artists = artists;
    }

    public void setLinkURL(String linkURL) {
        this.linkURL = linkURL;
    }


    public String Title() {
        return this.title;
    }

    public List<String> Artists() {
        return this.artists;
    }

    public String Url() {
        return this.linkURL;
    }
}
