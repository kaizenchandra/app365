package com.synechisveltiosi.apis.app365.common.util;


import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

public final class StringHelper {

    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");


    public static String valueOf(Object value) {
        if (value == null) return null;

        return String.valueOf(value);
    }

    public static String slug(String value) {

        String noWhiteSpace = WHITESPACE.matcher(value).replaceAll("-");
        String normalized = Normalizer.normalize(noWhiteSpace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");

        return slug.toLowerCase(Locale.ENGLISH);
    }
}
