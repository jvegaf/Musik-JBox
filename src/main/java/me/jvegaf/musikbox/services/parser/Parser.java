package me.jvegaf.musikbox.services.parser;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.jvegaf.musikbox.services.tagger.SearchResult;
import me.jvegaf.musikbox.tracks.Track;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class Parser {
    private static SearchResult getSearchResult(JsonObject trackObj) {

        SearchResult sr = new SearchResult();

        JsonArray jsonArtists = trackObj.getAsJsonArray("artists");
        List<String> artistsList = new ArrayList<>();
        jsonArtists.forEach(jsonArtist -> artistsList.add(jsonArtist.getAsJsonObject().get("name").getAsString()));
        sr.setArtists(artistsList);
        Optional<String> name = Optional.ofNullable(trackObj.get("name").getAsString());
        Optional<String> mixName = Optional.ofNullable(trackObj.get("mix_name").getAsString());
        name.ifPresent(sr::setTitle);
        mixName.ifPresent(sr::setRemixName);
        Optional<String>
                album =
                Optional.ofNullable(trackObj.get("release").getAsJsonObject().get("name").getAsString());
        album.ifPresent(sr::setAlbum);
        Optional<String> duration = Optional.ofNullable(trackObj.get("length").getAsString());
        duration.ifPresent(sr::setDuration);
        Optional<String> genre = Optional.ofNullable(trackObj.get("genre").getAsJsonObject().get("name").getAsString());
        genre.ifPresent(sr::setGenre);
        Optional<Integer> bpm = Optional.of(trackObj.get("bpm").getAsNumber().intValue());
        bpm.ifPresent(sr::setBpm);
        int camelotNum = trackObj.get("key").getAsJsonObject().get("camelot_number").getAsNumber().intValue();
        String camelotKey = trackObj.get("key").getAsJsonObject().get("camelot_letter").getAsString();
        String key = String.format("%s%d", camelotKey, camelotNum);
        sr.setKey(key);
        Optional<String> year = Optional.of(trackObj.get("publish_date").getAsString().substring(0, 4));
        year.ifPresent(sr::setYear);

        Optional<String> artworkUrl =
                Optional.ofNullable(trackObj.get("release")
                                            .getAsJsonObject()
                                            .get("image")
                                            .getAsJsonObject()
                                            .get("uri")
                                            .getAsString());

        artworkUrl.ifPresent(sr::setArtworkURL);

        return sr;
    }

    public static List<SearchResult> getResultsFromResponse(String response) {
        List<SearchResult> results = new ArrayList<>();
        final JsonObject rootObj = JsonParser.parseString(response).getAsJsonObject();
        final JsonArray jsonTracks = rootObj.get("tracks").getAsJsonArray();

        for (JsonElement jsonTrack : jsonTracks) {
            SearchResult sr = getSearchResult((JsonObject) jsonTrack);
            results.add(sr);
        }

        return results;
    }
}
