package me.jvegaf.musikbox.shared.infrastructure;


import me.jvegaf.musikbox.shared.domain.UuidGenerator;

import java.util.UUID;


public final class JavaUuidGenerator implements UuidGenerator {

    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
