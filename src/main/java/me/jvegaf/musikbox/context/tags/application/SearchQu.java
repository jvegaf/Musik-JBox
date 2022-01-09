package me.jvegaf.musikbox.context.tags.application;

public final class SearchQu {

    private static final String QUERY_ARG_DELIMITER = "+";
    private final        String title;
    private final        String artist;

    public SearchQu(String title, String artist) {
        this.title  = title;
        this.artist = artist;
    }

    public String query() {
        String SEARCH_TYPE = "&type=tracks";
        String query = sanitize(title) + SEARCH_TYPE;
        String ARTIST_FLAG = "&artist_name=";
        if (artist != null && !artist.isEmpty()) query += ARTIST_FLAG + sanitize(artist);
        return query;
    }

    public String rawQuery() {
        String query = sanitize(title);
        if (artist != null && !artist.isEmpty()) query += " " + sanitize(artist);
        return query;
    }

    private String sanitize(String s) {
        return s.replaceAll("_", " ")
                .replaceAll("-", " ")
                .replaceAll("[|]", " ")
                .replaceAll("[(]", " ")
                .replaceAll("[)]", " ")
                .replaceAll("\"", " ")
                .replaceAll(" ", " ")
                .trim()
                .replaceAll("\\s+", "+");
    }

}
