package me.jvegaf.musikbox.shared.infrastructure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Sanitizer {
    public static List<String> sanitize(List<String> qArgs) {
        var res = new ArrayList<String>();

        for (String qArg : qArgs) {
            var strArr = sanitizeElement(qArg);
            res.addAll(Arrays.asList(strArr));
        }

        return res;
    }

    private static String[] sanitizeElement(String element) {
        return element
                .replaceAll("_", " ")
                .replaceAll("-", " ")
                .replaceAll("[(]", " ")
                .replaceAll("[)]", " ")
                .replaceAll(" {2}", " ")
                .trim()
                .split(" ");
    }

}
