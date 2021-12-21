package me.jvegaf.musikbox.services.tagger;

import me.jvegaf.musikbox.services.tagger.OAuthDTO;
import me.jvegaf.musikbox.services.tagger.SearchResult;
import me.jvegaf.musikbox.services.web.client.*;
import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.gargoylesoftware.htmlunit.HttpMethod.GET;


public class BeatportTagger {
    public static final String URI_BASE = "https://www.beatport.com";
    private final WebClient client;
    private OAuthDTO token = null;
    private final HttpMethod GET_METHOD = GET;

    public BeatportTagger(Client client) {
        this.client = client.getWebClient();
    }

    public List<SearchResult> search(String[] reqArgs) {
        List<SearchResult> results = new ArrayList<>();
        StringBuilder sb = new StringBuilder(URI_BASE);
        sb.append("/search/tracks?q=");
        var query = QueryBuilder.build(reqArgs);
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

        return new SearchResult(id, title, remixed, artists, link);
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

    public void fetchTrackEmbed(String id) {
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
                String json = response.getContentAsString();
                System.out.println(json);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
