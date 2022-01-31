package com.github.jvegaf.musikbox.shared.infrastructure;


import com.github.jvegaf.musikbox.shared.domain.UuidGenerator;

import java.util.UUID;


public final class JavaUuidGenerator implements UuidGenerator {

    @Override
    public String generate() {
        return UUID.randomUUID()
                   .toString();
    }
}
