package me.jvegaf.musikbox.services.tagger;

import java.time.Instant;

public final class OAuthDTO {
    private final String accessToken;
    private final Instant expiresInstant;

    private OAuthDTO(String accessToken, String expiresIn) {
        this.accessToken = accessToken;
        this.expiresInstant = getExpiresInstant(expiresIn);
    }

    public static OAuthDTO create(String accessToken, String expiresIn) {
        return new OAuthDTO(accessToken, expiresIn);
    }

    public String Value() {
        return this.accessToken;
    }

    public boolean isValid() {
        return this.expiresInstant.isAfter(Instant.now());
    }

    private Instant getExpiresInstant(String expireStr) {
        return Instant.now().plusSeconds(Long.parseLong(expireStr));
    }
}
