package me.jvegaf.musikbox.shared.infrastructure;

import me.jvegaf.musikbox.shared.domain.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

@Service
public class PictureFetcher {

    public byte[] fetchFromURI(String pictureUri) throws IOException {
        URL                   url    = new URL(pictureUri);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        URLConnection         conn   = url.openConnection();
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:96.0) Gecko/20100101 Firefox/96.0");

        try (InputStream inputStream = conn.getInputStream()) {
            int n;
            byte[] buffer = new byte[1024];
            while (-1 != (n = inputStream.read(buffer))) {
                output.write(buffer, 0, n);
            }
        }
        return output.toByteArray();
    }

    public String getMimeType(String pictureUri) throws IOException {
        URL                   url    = new URL(pictureUri);
        URLConnection         conn   = url.openConnection();
        return conn.getContentType();
    }
}
