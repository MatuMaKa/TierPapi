package xyz.matumaka.tiers.util;

public class parseTierPlaceholder {

    public static String parseTierPlaceholder(MiniJSONObject json, String params) {
        params = params.toLowerCase();

        // Simple points
        if (params.equals("points") && json.has("points")) {
            return String.valueOf(json.getInt("points"));
        }

        // Tier or Peak placeholders
        if (params.endsWith("_tier") || params.endsWith("_peak")) {
            String[] parts = params.split("_");
            if (parts.length < 2) return "Invalid";

            String gamemode = parts[0];
            boolean isTier = params.endsWith("_tier");
            boolean isPeak = params.endsWith("_peak");

            if (!json.has("rankings")) return "No rankings";
            MiniJSONObject rankings = json.getJSONObject("rankings");

            if (!rankings.has(gamemode)) return "No gamemode";
            MiniJSONObject modeObj = rankings.getJSONObject(gamemode);

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

        return "Invalid";
    }
}
