package xyz.matumaka.tiers.util;

import org.json.JSONObject;

public class json {
    public static String getValueFromJson(JSONObject json, String key) {
        return json.has(key) ? json.get(key).toString() : "N/A";
    }
}
