package me.jvegaf.musikbox.services.web.client;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class QueryDTO {
    private final List<String> elements;
    private final String value;

    private QueryDTO(List<String> elements, String delimiter) {
        this.elements = elements;
        this.value = StringUtils.join(elements.toArray(), delimiter);
    }

    public static QueryDTO create(List<String> elements, String delimiter) {
        return new QueryDTO(elements, delimiter);
    }

    public List<String> Elements() {
        return elements;
    }

    public String Value() {
        return value;
    }
}
