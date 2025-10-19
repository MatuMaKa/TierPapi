package xyz.matumaka.tiers.util;

import org.json.JSONObject;
import java.lang.String;

public class parseTierPlaceholder {

    public static String parseTierPlaceholder(JSONObject json, String params) {
        params = params.toLowerCase();

        if (params.equals("points") && json.has("points")) {
            return String.valueOf(json.getInt("points"));
        }

        if (params.contains("_tier") || params.contains("_peak")) {
            String[] parts = params.split("_");
            if (parts.length < 2) return "Invalid";

            String gamemode = parts[0];
            boolean isTier = params.endsWith("_tier");
            boolean isPeak = params.endsWith("_peak");

            if (!json.has("rankings")) return "No rankings";
            JSONObject rankings = json.getJSONObject(gamemode);
            if (!rankings.has("gamemode")) return "No gamemode";

            JSONObject modeObj = rankings.getJSONObject(gamemode);

            boolean retired = modeObj.has("retired") && modeObj.getBoolean("retired");
            String retiredPrefix = retired ? "R" : "";

            int pos = modeObj.has("pos") ? modeObj.getInt("pos") : 0;
            String prefix = (pos == 1) ? "LT" : "HT";

            if (isTier) {
                int tier = modeObj.has("tier") ? modeObj.getInt("tier") : 0;
                return retiredPrefix + prefix + tier;
            }

            if (isPeak) {
                int peakTier = modeObj.has("peak_tier") ? modeObj.getInt("peak_tier") : 0;
                int peakPos = modeObj.has("peak_pos") ? modeObj.getInt("peak_pos") : 0;
                String peakPrefix = (peakPos == 1) ? "LT" : "HT";
                return peakPrefix + peakTier;
            }
        }
        return "Unknown placeholder";
    }
}