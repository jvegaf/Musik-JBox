package me.jvegaf.musikbox.services.tagger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import me.jvegaf.musikbox.services.parser.Parser;
import me.jvegaf.musikbox.services.web.client.QueryBuilder;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class BeatportTagger implements Tagger {
    public static final String URI_BASE = "https://api.beatport.com/v4/catalog/search/?q=";
    public static final String QUERY_ARG_DELIMITER = "+";


    public BeatportTagger() { }

    @Override
    public List<SearchResult> search(List<String> reqArgs) {
        OAuthDTO token = getToken();

        var query = QueryBuilder.build(reqArgs, QUERY_ARG_DELIMITER);

        List<String>urlElements = new ArrayList<>();
        urlElements.add(URI_BASE);
        urlElements.add(query.Value());
        urlElements.add("&type=tracks");
        String urlStr = String.join("", urlElements);
        System.out.println("query url: " + urlStr);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder()
                .setHeader("Accept", "application/json")
                .setHeader("Authorization", "Bearer " + token.Value())
                .uri(URI.create(urlStr))
                .GET()
                .build();

        String response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                                .thenApply(HttpResponse::body)
                                .join();// return the result so we could see the result on the console


        return Parser.getResultsFromResponse(response);

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

        return new OAuthDTO(map.get("access_token"), map.get("expires_in"));
    }
}
