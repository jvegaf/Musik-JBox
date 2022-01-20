package me.jvegaf.musikbox.context.shared.domain;

import me.jvegaf.musikbox.shared.domain.bus.query.Response;

public final class Artwork implements Response {

    private final byte[] data;
    private final String description;
    private final String  imageUrl;
    private final boolean linked;
    private final String  mimeType;
    private final Integer pictureType;

    public Artwork(
            byte[] data, String description, String imageUrl, boolean linked, String mimeType, Integer pictureType
    ) {
        this.data        = data;
        this.description = description;
        this.imageUrl    = imageUrl;
        this.linked      = linked;
        this.mimeType    = mimeType;
        this.pictureType = pictureType;
    }

    public Artwork() {
        this.data        = new byte[0];
        this.description = null;
        this.imageUrl    = null;
        this.linked      = false;
        this.mimeType    = null;
        this.pictureType = null;
    }

    public byte[] data() {
        return this.data;
    }

    public String description() {
        return this.description;
    }

    public String imageUrl() {
        return this.imageUrl;
    }

    public boolean isLinked() {
        return this.linked;
    }

    public String mimeType() {
        return this.mimeType;
    }

    public Integer pictureType() {
        return this.pictureType;
    }
}
