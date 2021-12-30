package me.jvegaf.musikbox.context.tagger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import me.jvegaf.musikbox.services.parser.SearchResultCreator;
import org.apache.log4j.Logger;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;


public class BeatportTagger implements Tagger {
    public static final String URI_BASE = "https://api.beatport.com/v4/catalog/search/?q=";
    private final Logger LOG = Logger.getLogger(BeatportTagger.class);
    private OAuthDTO token;


    public BeatportTagger() {
        this.token = getToken();
    }

    @Override
    public List<SearchResult> search(SearchRequest searchRequest) {
        if (!this.token.isValid())
            this.token = getToken();

        String urlString = URI_BASE + searchRequest.RequestQuery();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder()
                .setHeader("Accept", "application/json")
                .setHeader("Authorization", "Bearer " + this.token.Value())
                .uri(URI.create(urlString))
                .GET()
                .build();

        String response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                                .thenApply(HttpResponse::body)
                                .join();// return the result so we could see the result on the console


        return SearchResultCreator.getResultsFromResponse(response);

    }

    private OAuthDTO getToken() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder()
                .setHeader("Accept", "application/json")
                .uri(URI.create("https://embed.beatport.com/token"))
                .GET()
                .build();

        String response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                                .thenApply(HttpResponse::body)
                                .join();// return the result so we could see the result on the console

        Map<String, String> map = new Gson().fromJson(response, new TypeToken<Map<String, String>>() {}.getType());

        LOG.info("Token created");
        return new OAuthDTO(map.get("access_token"), map.get("expires_in"));
    }
}
