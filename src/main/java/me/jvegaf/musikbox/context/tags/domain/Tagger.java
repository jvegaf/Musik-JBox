package me.jvegaf.musikbox.context.tags.domain;

public interface Tagger {

    Tag search(String title, String artist, Integer duration);
}
