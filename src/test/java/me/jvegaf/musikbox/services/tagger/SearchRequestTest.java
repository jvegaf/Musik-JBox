package me.jvegaf.musikbox.services.tagger;

import me.jvegaf.musikbox.context.tagger.SearchRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class SearchRequestTest {

    @Test
    void trycreateASimpleRequest() {
        SearchRequest request = SearchRequest.createSimpleRequest("The Real Life (club mix)");
        assertEquals("The+Real+Life+club+mix&type=tracks", request.RequestQuery());
    }

    @Test
    void createCompleteRequest() {
        SearchRequest request = SearchRequest.createCompleteRequest("The Real Life\" (club mix) ", " raven maize");
        assertEquals("The+Real+Life+club+mix&type=tracks&artist_name=raven+maize", request.RequestQuery());
    }

    @Test
    void createProblematicRequest() {
        SearchRequest
                request =
                SearchRequest.createCompleteRequest("Give Me Your Love (Valentino Kanzyani      Mix)", "Carl Cox");
        assertEquals("Give+Me+Your+Love+Valentino+Kanzyani+Mix&type=tracks&artist_name=Carl+Cox",
                     request.RequestQuery());
    }
}