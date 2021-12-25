package me.jvegaf.musikbox.tracks;

public record Art(byte[] data, String description, String imageUrl, boolean linked,
                  String mimeType, Integer pictureType) { }
