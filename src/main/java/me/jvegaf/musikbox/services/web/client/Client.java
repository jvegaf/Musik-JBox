package me.jvegaf.musikbox.services.web.client;

import com.gargoylesoftware.htmlunit.WebClient;

public class Client {
    private final WebClient webClient;

    public Client() {
        this.webClient = new WebClient();
    }

    public WebClient getWebClient() {
        return webClient;
    }
}
