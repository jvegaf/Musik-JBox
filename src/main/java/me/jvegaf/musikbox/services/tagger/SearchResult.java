package me.jvegaf.musikbox.services.tagger;

import java.util.List;

public class SearchResult {
    private final String id;
    private final String title;
    private final String remixed;
    private final List<String> artists;
    private final String url;

    private SearchResult(String id, String title, String remixed, List<String> artists, String url) {
        this.id = id;
        this.title = title;
        this.remixed = remixed;
        this.artists = artists;
        this.url = url;
    }

    public static SearchResult create(String id, String title, String remixed, List<String> artists, String url) {
        return new SearchResult(id, title, remixed, artists, url);
    }

    public String Id() { return this.id; }

    public String Title() {
        return this.title;
    }

    public String Remixed() {
        return this.remixed;
    }

    public List<String> Artists() {
        return this.artists;
    }

    public String Url() { return this.url; }
}
