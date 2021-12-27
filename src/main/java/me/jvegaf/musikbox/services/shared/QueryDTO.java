package me.jvegaf.musikbox.services.shared;


import java.util.List;

public class QueryDTO {
    private final String value;

    private QueryDTO(List<String> elements, String delimiter) {
        this.value = String.join(delimiter, elements);
    }

    public static QueryDTO create(List<String> elements, String delimiter) {
        return new QueryDTO(elements, delimiter);
    }

    public String Value() {
        return value;
    }
}
