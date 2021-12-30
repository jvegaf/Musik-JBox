package me.jvegaf.musikbox.services.shared;

import me.jvegaf.musikbox.shared.infrastructure.Sanitizer;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SanitizerTest {

    @Test
    void sanitize() {
        List<String> test = List.of("test-teswt(text)_hola  ");

        List<String> expected = Arrays.asList("test","teswt","text","hola");

        List<String> result = Sanitizer.sanitize(test);
        assertEquals(expected, result);
    }
}