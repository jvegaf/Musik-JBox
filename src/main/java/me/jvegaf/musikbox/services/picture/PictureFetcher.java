package me.jvegaf.musikbox.services.picture;

import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public final class PictureFetcher {

    private static final Logger LOG = Logger.getLogger(PictureFetcher.class);

    public static byte[] getFromURL(String imageUrl) {


        URL url = null;
        try {
            url = new URL(imageUrl);
        } catch (MalformedURLException e) {
            LOG.error("Malformed URL: " + imageUrl);
        }


        ByteArrayOutputStream output = new ByteArrayOutputStream();

        if (url != null) {
            try (InputStream stream = url.openStream()) {
                byte[] buffer = new byte[4096];

                while (true) {
                    int bytesRead = stream.read(buffer);
                    if (bytesRead < 0) {
                        break;
                    }
                    output.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
                LOG.error("Error while fetching image from URL: " + imageUrl);
            }
            return output.toByteArray();
        }
        return new byte[0];
    }
}