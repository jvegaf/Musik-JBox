package me.jvegaf.musikbox.services.web.client;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class QueryDTO {
    private final List<String> elements;
    private final String value;

    private QueryDTO(List<String> elements) {
        this.elements = elements;
        this.value = StringUtils.join(elements.toArray(), "+");
    }

    public static QueryDTO create(List<String> elements) {
        return new QueryDTO(elements);
    }

    public List<String> Elements() {
        return elements;
    }

    public String Value() {
        return value;
    }
}
