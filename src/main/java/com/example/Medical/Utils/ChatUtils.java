package com.example.Medical.Utils;

import java.util.Locale;
import java.util.regex.*;

public class ChatUtils {

    public static String detectIntent(String query) {
        String normalized = query == null ? "" : query.toLowerCase(Locale.ROOT);

        boolean hasNic = normalized.contains("nic") || normalized.matches(".*\\d{12}.*");
        boolean asksForRecord = containsAny(
                normalized,
                "show",
                "find",
                "get",
                "lookup",
                "record",
                "details",
                "history",
                "prescription",
                "bp level",
                "fbs",
                "lab",
                "report"
        );
        boolean mentionsPatient = normalized.contains("patient");

        
        if (hasNic || (mentionsPatient && asksForRecord)) {
            return "PATIENT_QUERY";
        }

        return "MEDICAL_QUERY";
    }

    private static boolean containsAny(String query, String... keywords) {
        for (String keyword : keywords) {
            if (query.contains(keyword)) {
                return true;
            }
        }
        return false;
    }

    public static String extractNIC(String query) {
        Pattern pattern = Pattern.compile("\\d{12}");
        Matcher matcher = pattern.matcher(query);

        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }
}