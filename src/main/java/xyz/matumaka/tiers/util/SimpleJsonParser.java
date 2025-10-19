package xyz.matumaka.tiers.util;

import java.util.Map;
import java.util.HashMap;

public class SimpleJsonParser {

    public static Map<String, Object> parse(String json) {
        json = json.trim();
        if (!json.startsWith("{") || !json.endsWith("}")) return Map.of();

        Map<String, Object> map = new HashMap<>();
        json = json.substring(1, json.length() - 1); // remove { }

        int braceCount = 0;
        StringBuilder key = new StringBuilder();
        StringBuilder value = new StringBuilder();
        boolean inQuotes = false;
        boolean readingKey = true;

        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);

            if (c == '"') inQuotes = !inQuotes;

            if (readingKey) {
                if (c == ':' && !inQuotes && braceCount == 0) {
                    readingKey = false;
                    continue;
                }
                key.append(c);
            } else {
                if (c == '{' && !inQuotes) braceCount++;
                if (c == '}' && !inQuotes) braceCount--;

                if (c == ',' && !inQuotes && braceCount == 0) {
                    map.put(clean(key.toString()), parseValue(value.toString()));
                    key.setLength(0);
                    value.setLength(0);
                    readingKey = true;
                    continue;
                }
                value.append(c);
            }
        }

        if (key.length() > 0) {
            map.put(clean(key.toString()), parseValue(value.toString()));
        }

        return map;
    }

    private static String clean(String str) {
        return str.trim().replaceAll("^\"|\"$", "");
    }

    private static Object parseValue(String val) {
        val = val.trim();
        if (val.startsWith("{") && val.endsWith("}")) return parse(val);
        if (val.equals("true") || val.equals("false")) return Boolean.parseBoolean(val);
        try { return Integer.parseInt(val); } catch (Exception e) {}
        try { return Double.parseDouble(val); } catch (Exception e) {}
        return val.replaceAll("^\"|\"$", "");
    }
}
