package me.jvegaf.musikbox.services.shared;

import java.util.List;

public class QueryBuilder {
    public static QueryDTO build(List<String> strArgs, String delimiter) {
        return QueryDTO.create(strArgs, delimiter);
    }

}
