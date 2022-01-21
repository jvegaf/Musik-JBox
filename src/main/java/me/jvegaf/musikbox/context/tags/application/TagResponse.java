package me.jvegaf.musikbox.context.tags.application;

import me.jvegaf.musikbox.context.tags.domain.Tag;
import me.jvegaf.musikbox.shared.domain.bus.query.Response;

public final class TagResponse implements Response {
    private final Tag tag;

    public TagResponse() {
        this.tag = null;
    }

    public TagResponse(Tag tag) {
        this.tag = tag;
    }

    public Tag tag() {return tag;}
}
