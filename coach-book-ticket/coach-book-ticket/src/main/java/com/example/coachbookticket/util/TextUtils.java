package com.example.coachbookticket.util;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class TextUtils {

    private static final Pattern DIACRITICS = Pattern.compile("\\p{M}+");
    private static final Pattern NON_ALPHANUM = Pattern.compile("[^\\p{IsAlphabetic}\\p{IsDigit}]");

    public static String normalizeForSearch(String input) {
        if (input == null) return "";

        // 1. NFD normalize (tách dấu)
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);

        // 2. remove combining marks (dấu)
        normalized = DIACRITICS.matcher(normalized).replaceAll("");

        // 3. replace special letters that are NOT decomposed by NFD (đ)
        normalized = normalized.replace('đ', 'd').replace('Đ', 'D');

        // 4. remove non-alphanumeric (spaces, punctuation, symbols)
        normalized = NON_ALPHANUM.matcher(normalized).replaceAll("");

        // 5. to lower
        return normalized.toLowerCase();
    }

}
