package me.jvegaf.musikbox.context.tags.infrastructure.tagger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.log4j.Log4j2;
import me.jvegaf.musikbox.context.tagger.SearchQu;
import me.jvegaf.musikbox.context.tagger.SearchResult;
import me.jvegaf.musikbox.context.tagger.SearchResultCreator;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class BeatportTagger {

    private final String              URI_BASE = "https://api.beatport.com/v4/catalog/search/?q=";
    private final SearchResultCreator creator;
    private       OAuthDTO            token;


    public BeatportTagger() {
        this.creator = new SearchResultCreator();
        this.token   = getToken();
    }

    public List<SearchResult> search(SearchQu searchRequest) {
        if (!this.token.isValid()) this.token = getToken();

        String urlString = URI_BASE + searchRequest.query();

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


        return creator.getResultsFromResponse(response);

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

        log.info("Token created");
        return new OAuthDTO(map.get("access_token"), map.get("expires_in"));
    }
}
