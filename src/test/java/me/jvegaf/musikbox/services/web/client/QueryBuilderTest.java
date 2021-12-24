package me.jvegaf.musikbox.services.web.client;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QueryBuilderTest {



    @Test
    void tryABuildQueryStringOneString() {
        List<String> input = new ArrayList<>();
        input.add("joeski_un_congo" );


        var resVO = QueryBuilder.build(Sanitizer.sanitize(input), "+");
        assertEquals(resVO.Value(), "joeski+un+congo");
    }

    @Test
    void tryABuildQueryStringCoupleStrings() {
        List<String> input = new ArrayList<>();
        input.add("joeski " );
        input.add(" un_congo " );


        var resVO = QueryBuilder.build(Sanitizer.sanitize(input), "+");
        assertEquals(resVO.Value(), "joeski+un+congo");
    }

    @Test
    void tryAnotherBuildQueryStringCoupleStrings() {
        List<String> input = new ArrayList<>();
        input.add("deadmau5_1981_Mike_Vale_vs_Jerome_Robins_Remix_" );


        var resVO = QueryBuilder.build(Sanitizer.sanitize(input), "+");
        assertEquals(resVO.Value(), "deadmau5+1981+Mike+Vale+vs+Jerome+Robins+Remix");
    }
}
