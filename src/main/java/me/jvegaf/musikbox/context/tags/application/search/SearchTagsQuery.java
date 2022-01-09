package me.jvegaf.musikbox.context.tags.application.search;

import me.jvegaf.musikbox.shared.domain.bus.query.Query;

public final class SearchTagsQuery implements Query {

    private final String  title;
    private final String  artist;
    private final Integer duration;

    public SearchTagsQuery(String title, String artist, Integer duration) {
        this.title    = title;
        this.artist   = artist;
        this.duration = duration;
    }

    public String title() {return title;}

    public String artist() {return artist;}

    public Integer duration() {return duration;}
}
