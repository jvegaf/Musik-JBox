package com.github.jvegaf.musikbox.context.tracks.domain;

import com.github.jvegaf.musikbox.shared.domain.IntValueObject;

public class TrackBpm extends IntValueObject {

    public TrackBpm(Integer value) {
        super(value);
    }

    public TrackBpm(String strValue) {
        super(Integer.getInteger(strValue));
    }

    public TrackBpm() {super(null);}
}
