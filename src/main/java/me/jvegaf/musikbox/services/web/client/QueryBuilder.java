package me.jvegaf.musikbox.services.web.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QueryBuilder {
    public static QueryDTO build(String[] strArgs) {
        //        joeski+un+congo
        var elements = sanitize(strArgs);
        return QueryDTO.create(elements);
    }

    private static List<String> sanitize(String[] qArgs) {
        var res = new ArrayList<String>();

        for (String qArg : qArgs) {
            var strArr = qArg.replaceAll("_", " ").trim().split(" ");
            res.addAll(Arrays.asList(strArr));
        }

        return res;
    }

}
