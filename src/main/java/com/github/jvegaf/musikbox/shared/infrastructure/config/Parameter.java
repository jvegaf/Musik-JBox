package com.github.jvegaf.musikbox.shared.infrastructure.config;

import com.github.jvegaf.musikbox.shared.domain.Service;
import io.github.cdimascio.dotenv.Dotenv;

@Service
public final class Parameter {

    private final Dotenv dotenv;

    public Parameter(Dotenv dotenv) {
        this.dotenv = dotenv;
    }

    public Integer getInt(String key) throws ParameterNotExist {
        String value = get(key);

        return Integer.parseInt(value);
    }

    public String get(String key) throws ParameterNotExist {
        String value = dotenv.get(key);

        if (null==value) {
            throw new ParameterNotExist(key);
        }

        return value;
    }
}
