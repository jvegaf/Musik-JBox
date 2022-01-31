package com.github.jvegaf.musikbox.context.shared.application;

import com.github.jvegaf.musikbox.shared.domain.bus.query.Query;

public class RetrieveArtworkQuery implements Query {
    private final String trackLocation;

    public RetrieveArtworkQuery(String trackLocation) {this.trackLocation = trackLocation;}

    public String trackLocation() {return trackLocation;}
}
