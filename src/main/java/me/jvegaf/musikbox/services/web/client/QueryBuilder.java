package me.jvegaf.musikbox.services.web.client;

import java.util.List;

public class QueryBuilder {
    public static QueryDTO build(List<String> strArgs, String delimiter) {
        //        joeski+un+congo
        return QueryDTO.create(strArgs, delimiter);
    }

}
