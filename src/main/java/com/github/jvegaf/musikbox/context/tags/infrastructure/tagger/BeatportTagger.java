package com.github.jvegaf.musikbox.context.tags.infrastructure.tagger;

import com.github.jvegaf.musikbox.context.tags.application.SearchQu;
import com.github.jvegaf.musikbox.context.tags.application.TagResponse;
import com.github.jvegaf.musikbox.context.tags.domain.Tag;
import com.github.jvegaf.musikbox.context.tags.domain.Tagger;
import com.github.jvegaf.musikbox.shared.domain.Service;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Marker;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@Log4j2
@Service
public class BeatportTagger implements Tagger {

    private       OAuthDTO token;


    public BeatportTagger() {
        this.token = getToken();
    }

    private OAuthDTO getToken() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest
                request =
                HttpRequest.newBuilder()
                           .setHeader("Accept", "application/json")
                           .uri(URI.create("https://embed.beatport.com/token"))
                           .GET()
                           .build();

        String response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                                .thenApply(HttpResponse::body)
                                .join();// return the result so we could see the result on the console

        Map<String, String> map = new Gson().fromJson(response, new TypeToken<Map<String, String>>() {
        }.getType());

        log.info("Token created");
        return new OAuthDTO(map.get("access_token"), map.get("expires_in"));
    }

    @Override
    public TagResponse search(String title, String artist, Integer duration) {
        if (!this.token.isValid()) this.token = getToken();

        SearchQu searchQu = new SearchQu(title, artist);

        String URI_BASE  = "https://api.beatport.com/v4/catalog/search/?q=";
        String urlString = URI_BASE + searchQu.query();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest
                request =
                HttpRequest.newBuilder()
                           .setHeader("Accept", "application/json")
                           .setHeader("Authorization", "Bearer " + this.token.Value())
                           .uri(URI.create(urlString))
                           .GET()
                           .build();

        String response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                                .thenApply(HttpResponse::body)
                                .join();// return the result so we could see the result on the console


        List<Tag> tags = createTagsFromResponse(response);

        if (tags.size() < 1) {
            log.info("No tags founded");
            return new TagResponse();
        }

        return new TagResponse(matchTags(tags, duration));

    }

    private List<Tag> createTagsFromResponse(String response) {
        List<Tag>        result     = new ArrayList<>();
        final JsonObject
                         rootObj    =
                JsonParser.parseString(response)
                          .getAsJsonObject();
        final JsonArray
                         jsonTracks =
                rootObj.get("tracks")
                       .getAsJsonArray();

        for (JsonElement jsonTrack : jsonTracks) {
            Tag t = getTagResult((JsonObject) jsonTrack);
            result.add(t);
        }

        return result;
    }

    private Tag matchTags(List<Tag> tags, Integer duration) {
        tags.sort(Comparator.comparing(t -> t.duration()
                                             .compareTo(duration)));
        var dif = tags.get(0).duration() - duration;
        log.debug("difference: " + dif);
        return tags.get(0);
    }

    private Tag getTagResult(JsonObject trackObj) {
        Tag tag = new Tag();

        JsonArray    jsonArtists = trackObj.getAsJsonArray("artists");
        List<String> artistsList = new ArrayList<>();
        jsonArtists.forEach(jsonArtist -> artistsList.add(jsonArtist.getAsJsonObject()
                                                                    .get("name")
                                                                    .getAsString()));
        tag.setArtists(artistsList);
        Optional<String> name    = Optional.ofNullable(trackObj.get("name")
                                                               .getAsString());
        Optional<String> mixName = Optional.ofNullable(trackObj.get("mix_name")
                                                               .getAsString());
        name.ifPresent(tag::setTitle);
        mixName.ifPresent(tag::setRemixName);
        Optional<String> album = Optional.ofNullable(trackObj.get("release")
                                                             .getAsJsonObject()
                                                             .get("name")
                                                             .getAsString());
        album.ifPresent(tag::setAlbum);
        JsonElement lengthEl = trackObj.get("length");
        if (!lengthEl.isJsonNull()) tag.setDuration(lengthEl.getAsString());
        Optional<String> genre = Optional.ofNullable(trackObj.get("genre")
                                                             .getAsJsonObject()
                                                             .get("name")
                                                             .getAsString());
        genre.ifPresent(tag::setGenre);
        JsonElement bpmEl = trackObj.get("bpm");
        if (!bpmEl.isJsonNull()) tag.setBpm(bpmEl.getAsInt());
        JsonElement keyEl = trackObj.get("key");
        if (!keyEl.isJsonNull()) {
            int
                    camelotNum =
                    keyEl.getAsJsonObject()
                         .get("camelot_number")
                         .getAsInt();
            String
                    camelotKey =
                    keyEl.getAsJsonObject()
                         .get("camelot_letter")
                         .getAsString();
            String  key        = String.format("%s%d", camelotKey, camelotNum);
            tag.setKey(key);
        }
        Optional<String> year = Optional.of(trackObj.get("publish_date")
                                                    .getAsString()
                                                    .substring(0, 4));
        year.ifPresent(tag::setYear);

        Optional<String> artworkUrl = Optional.ofNullable(trackObj.get("release")
                                                                  .getAsJsonObject()
                                                                  .get("image")
                                                                  .getAsJsonObject()
                                                                  .get("uri")
                                                                  .getAsString());

        artworkUrl.ifPresent(tag::setArtworkURL);

        return tag;
    }
}
