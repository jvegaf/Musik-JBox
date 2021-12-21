package me.jvegaf.musikbox.services.tagger;

import java.util.List;

public class SearchResult {
    private final String id;
    private final String title;
    private final String remixName;
    private final List<String> artists;
    private final String linkURL;

    public SearchResult(String id, String title, String remixName, List<String> artists, String linkURL) {
        this.id = id;
        this.title = title;
        this.remixName = remixName;
        this.artists = artists;
        this.linkURL = linkURL;
    }

    public String Id() { return this.id; }

    public String Title() {
        return this.title;
    }

    public String RemixName() {
        return this.remixName;
    }

    public List<String> Artists() {
        return this.artists;
    }

    public String Url() { return this.linkURL; }
}
