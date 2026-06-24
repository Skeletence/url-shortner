package com.example.urlshortner.util;

import org.springframework.stereotype.Component;

@Component
public class Base62Converter {

    private static final String ALLOWED_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int BASE = ALLOWED_CHARACTERS.length();

    public static String encode(long input) {
        if (input == 0) {
            return String.valueOf(ALLOWED_CHARACTERS.charAt(0));
        }

        StringBuilder encodedString = new StringBuilder();
        while (input > 0) {
            int remainder = (int) (input % BASE);
            encodedString.append(ALLOWED_CHARACTERS.charAt(remainder));
            input = input / BASE;
        }

        return encodedString.reverse().toString();
    }
}
