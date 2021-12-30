package me.jvegaf.musikbox.context.tagger;

public final class SearchRequest {
    private static final String QUERY_ARG_DELIMITER = "+";
    private final String title;
    private String artist;

    private SearchRequest(String title) {
        this.title = title;
    }

    private SearchRequest(String title, String artist) {
        this.title = title;
        this.artist = artist;
    }

    public static SearchRequest createSimpleRequest(String title) {
        return new SearchRequest(title);
    }

    public static SearchRequest createCompleteRequest(String title, String artist) {
        return new SearchRequest(title, artist);
    }

    public String RequestQuery() {
        String SEARCH_TYPE = "&type=tracks";
        String query = sanitize(title) + SEARCH_TYPE;
        String ARTIST_FLAG = "&artist_name=";
        if (artist != null && !artist.isEmpty())
            query += ARTIST_FLAG + sanitize(artist);
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
