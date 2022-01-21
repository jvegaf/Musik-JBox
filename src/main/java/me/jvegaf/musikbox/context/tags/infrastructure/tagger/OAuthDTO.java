package me.jvegaf.musikbox.context.tags.infrastructure.tagger;

import java.time.Instant;

public final class OAuthDTO {
    private final String  accessToken;
    private final Instant expiresInstant;

    public OAuthDTO(String accessToken, String expiresIn) {
        this.accessToken    = accessToken;
        this.expiresInstant = getExpiresInstant(expiresIn);
    }

    private Instant getExpiresInstant(String expireStr) {
        return Instant.now().plusSeconds(Long.parseLong(expireStr));
    }

    public String Value() {
        return this.accessToken;
    }

    public boolean isValid() {
        return this.expiresInstant.isAfter(Instant.now());
    }
}
