package me.jvegaf.musikbox.services.web.client;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import org.apache.commons.logging.LogFactory;

import java.util.logging.Level;

public class ClientWeb {
    private final WebClient webClient;

    public ClientWeb() {
        this.webClient = new WebClient(BrowserVersion.BEST_SUPPORTED);
        this.webClient.getOptions().setJavaScriptEnabled(true);

        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");

        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);
    }

    public WebClient Client() {
        return this.webClient;
    }
}
