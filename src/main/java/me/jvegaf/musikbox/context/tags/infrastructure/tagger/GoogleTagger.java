package me.jvegaf.musikbox.context.tags.infrastructure.tagger;

import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import me.jvegaf.musikbox.context.tags.application.SearchQu;

import java.io.IOException;


public final class GoogleTagger {


    public void search(String title, String artist, Integer duration) {

        WebClient      webClient = new WebClient();
        final SearchQu searchQu  = new SearchQu(title, artist);


        try {
            HtmlPage   page       = webClient.getPage("https://www.google.com/search?q=" + searchQu.query());
            HtmlButton button     = page.getFirstByXPath("//*[@id=\"L2AGLb\"]");
            HtmlPage   page2      = button.click();
            String     pageAsText = page2.getPage().getWebResponse().getContentAsString();
            HtmlAnchor element = page2.getAnchors()
                                      .stream()
                                      .filter(anchor -> containsPattern("discogs", anchor.getHrefAttribute()))
                                      .findFirst()
                                      .get();
            String url = element.getHrefAttribute();
            System.out.println(url);
            webClient.close();

            webClient = new WebClient();
            WebRequest request = new WebRequest(new java.net.URL(url), HttpMethod.GET);
            //            request.setAdditionalHeader("Accept", "application/json");
            WebResponse response       = webClient.loadWebResponse(request);
            String      responseAsText = response.getContentAsString();
            System.out.println(responseAsText);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean containsPattern(String pattern, String text) {
        return text.contains(pattern);
    }
}
