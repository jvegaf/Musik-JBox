package me.jvegaf.musikbox.services.tagger;

import io.github.cdimascio.dotenv.Dotenv;
import me.jvegaf.musikbox.services.web.client.QueryBuilder;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpotifyTagger implements Tagger {

    private final ClientCredentialsRequest clientCredentialsRequest;
    private final SpotifyApi spotifyApi;


    public SpotifyTagger() {
        Dotenv dotenv = Dotenv.load();
        String c_ID = dotenv.get("SPOTIFY_ID");
        String c_S = dotenv.get("SPOTIFY_SECRET");
        this.spotifyApi = new SpotifyApi.Builder()
                .setClientId(c_ID)
                .setClientSecret(c_S)
                .build();
        this.clientCredentialsRequest = spotifyApi.clientCredentials()
                .build();
        clientCredentials_Sync();
    }

    private void clientCredentials_Sync() {
        try {
            ClientCredentials clientCredentials = clientCredentialsRequest.execute();

            // Set access token for further "spotifyApi" object usage
            System.out.println("Expires in: " + clientCredentials.getExpiresIn());
            this.spotifyApi.setAccessToken(clientCredentials.getAccessToken());

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public List<SearchResult> search(List<String> reqArgs) {
        List<SearchResult> result = new ArrayList<>();
        SearchTracksRequest req = makeTrackSearchRequest(reqArgs);
        try {
            Paging<Track> trackPaging = req.execute();

            System.out.println("Total tracks founded: " + trackPaging.getTotal());
            return makeTags(trackPaging.getItems());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return result;
    }

    private SearchTracksRequest makeTrackSearchRequest(List<String> reqArgs) {
        String req = QueryBuilder.build(reqArgs, " ").Value();

        return this.spotifyApi.searchTracks(req).build();
    }

    private List<SearchResult> makeTags(Track[] tracks) {
        List<SearchResult> result = new ArrayList<>();
        for (Track t: tracks) {
            SearchResult td = new SearchResult();
            td.setId(t.getId());
            td.setTitle(t.getName());
            td.setArtists(Arrays.stream(t.getArtists()).map(ArtistSimplified::getName).toList());
            td.setLinkURL(t.getUri());
            result.add(td);
            System.out.println(td);
        }
        return result;
    }


}
