package me.jvegaf.musikbox.services.tagger;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import me.jvegaf.musikbox.services.parser.Parser;
import me.jvegaf.musikbox.services.web.client.ClientWeb;
import me.jvegaf.musikbox.services.web.client.QueryBuilder;
import me.jvegaf.musikbox.tracks.Track;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.gargoylesoftware.htmlunit.HttpMethod.GET;


public class BeatportTagger implements Tagger {
    public static final String URI_BASE = "https://www.beatport.com";
    private final WebClient client;
    private OAuthDTO token = null;
    private final HttpMethod GET_METHOD = GET;

    @Inject
    public BeatportTagger(ClientWeb clientWeb) {

        this.client = clientWeb.Client();
    }

    @Override
    public List<SearchResult> search(List<String> reqArgs) {
        List<SearchResult> results = new ArrayList<>();
        StringBuilder sb = new StringBuilder(URI_BASE);
        sb.append("/search/tracks?q=");
        String delimiter = "+";
        var query = QueryBuilder.build(reqArgs, delimiter);
        sb.append(query.Value());
        System.out.println("query url: " + sb);
        //

        try {
            HtmlPage resultpage = client.getPage(sb.toString());
            List<DomNode> divs = resultpage.getByXPath("//div[@class='buk-track-meta-parent']");

            for (DomNode div: divs) {
                SearchResult sr = makeResult(div);
                results.add(sr);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return results;

    }

    private SearchResult makeResult(DomNode div) {
        var id = div.getParentNode().getAttributes().getNamedItem("data-ec-id").getNodeValue();
        var title = div.querySelectorAll(".buk-track-primary-title").get(0).getFirstChild().toString();
        var remixed = div.querySelectorAll(".buk-track-remixed").get(0).getFirstChild().toString();
        List<String> artists = div.querySelector(".buk-track-artists").querySelectorAll("a").stream().map(a -> a.getFirstChild().toString()).collect(Collectors.toList());
        String link = URI_BASE + div.querySelectorAll("a").get(0).getAttributes().getNamedItem("href").getNodeValue();
        String title_remixed = title + " - " + remixed;
        return new SearchResult(id, title_remixed, title, artists, link);
    }

    private OAuthDTO updateToken() {
        if (this.token != null && this.token.isValid()) return this.token;
        try {
            Page page = client.getPage("https://embed.beatport.com/token");
            WebResponse response = page.getWebResponse();
            if (response.getContentType().equals("application/json")) {
                String json = response.getContentAsString();
                Map<String, String> map = new Gson().fromJson(json, new TypeToken<Map<String, String>>() {}.getType());

                return this.token = new OAuthDTO(map.get("access_token"), map.get("expires_in"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Track fetchTrack(String id) {
        OAuthDTO token = updateToken();
        String uri = String.format("https://api.beatport.com/v4/catalog/tracks/%s", id);
        WebRequest req;
        try {
            req = new WebRequest(new URL(uri), GET_METHOD);
            assert token != null;
            req.setAdditionalHeader("Authorization", "Bearer " + token.Value());
            Page page = client.getPage(req);
            WebResponse response = page.getWebResponse();
            if (response.getContentType().equals("application/json")) {
                String jsonStr = response.getContentAsString();
                return Parser.parseTrack(jsonStr);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
