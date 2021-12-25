package me.jvegaf.musikbox.services.parser;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.jvegaf.musikbox.services.picture.PictureFetcher;
import me.jvegaf.musikbox.tracks.Track;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class Parser {
    public static Track parseTrack(String jsonTrack) {

        Track t = new Track();
        final JsonObject rootObj = JsonParser.parseString(jsonTrack).getAsJsonObject();

        JsonArray jsonArtists = rootObj.getAsJsonArray("artists");
        List<String> artistsList = new ArrayList<>();
        jsonArtists.forEach(jsonArtist -> artistsList.add(jsonArtist.getAsJsonObject().get("name").getAsString()) );
        t.setArtist(String.join(", ", artistsList));
        String name = rootObj.get("name").getAsString();
        String mixName = rootObj.get("mix_name").getAsString();
        if (mixName!=null && (mixName.length() > 0)) {
            name = name + " (" + mixName + ")";
        }
        t.setName(name);
        t.setAlbum(rootObj.get("release").getAsJsonObject().get("name").getAsString());
        t.setDuration(rootObj.get("length").getAsString());
        t.setGenre(rootObj.get("genre").getAsJsonObject().get("name").getAsString());
        t.setBpm(rootObj.get("bpm").getAsInt());
        int camelotNum = rootObj.get("key").getAsJsonObject().getAsJsonObject().get("camelot_number").getAsInt();
        String camelotKey = rootObj.get("key").getAsJsonObject().getAsJsonObject().get("camelot_letter").getAsString();
        t.setKey(camelotKey + camelotNum);
        t.setYear(rootObj.get("publish_date").getAsString().substring(0, 4));
        String artworkUrl = rootObj.get("release").getAsJsonObject().get("image").getAsJsonObject().get("uri").getAsString();

        try {
            byte[] fromURL = PictureFetcher.getFromURL(artworkUrl);
            t.setArtworkData(fromURL);
        } catch (IOException e) {
            System.out.println("Error fetching artwork: " + e.getMessage());
        }

        return t;
    }
}
