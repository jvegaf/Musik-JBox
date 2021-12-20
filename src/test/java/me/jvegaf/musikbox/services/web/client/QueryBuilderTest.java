package me.jvegaf.musikbox.services.web.client;

import me.jvegaf.musikbox.services.webclient.QueryBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QueryBuilderTest {



    @Test
    void tryABuildQueryStringOneString() {
        String[] input = {"joeski_un_congo "};


        var resVO = QueryBuilder.build(input);
        assertEquals(resVO.Value(), "joeski+un+congo");
    }

    @Test
    void tryABuildQueryStringCoupleStrings() {
        String[] input = {"joeski ", " un_congo " };


        var resVO = QueryBuilder.build(input);
        assertEquals(resVO.Value(), "joeski+un+congo");
    }

    @Test
    void tryAnotherBuildQueryStringCoupleStrings() {
        String[] input = {"deadmau5_1981_Mike_Vale_vs_Jerome_Robins_Remix_" };


        var resVO = QueryBuilder.build(input);
        assertEquals(resVO.Value(), "deadmau5+1981+Mike+Vale+vs+Jerome+Robins+Remix");
    }
}
