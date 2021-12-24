package me.jvegaf.musikbox.services.web.client;

public class QueryBuilder {
    public static QueryDTO build(String[] strArgs, String delimiter) {
        //        joeski+un+congo
        var elements = Sanitizer.sanitize(strArgs);
        return QueryDTO.create(elements, delimiter);
    }

}
