package me.jvegaf.musikbox.services.tagger;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

final class OAuthDTOTest {


    @Test
    void checkResponseOfValidToken() {
        OAuthDTO oAuthDTO = new OAuthDTO("Ft6g7cATZCDhbYVncycwMPUw7D8L9W","36000");

        assertTrue(oAuthDTO.isValid());
    }

    @Test
    void checkResponseOfInvalidToken() {
        OAuthDTO oAuthDTO = new OAuthDTO("Ft6g7cATZCDhbYVncycwMPUw7D8L9W","1");

//        await().atMost(Durations.TWO_SECONDS).untilAsserted(() -> assertFalse(oAuthDTO.isValid()));

    }

}
