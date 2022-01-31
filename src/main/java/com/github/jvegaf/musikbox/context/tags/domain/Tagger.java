package com.github.jvegaf.musikbox.context.tags.domain;

import com.github.jvegaf.musikbox.context.tags.application.TagResponse;

public interface Tagger {

    TagResponse search(String title, String artist, Integer duration);
}
