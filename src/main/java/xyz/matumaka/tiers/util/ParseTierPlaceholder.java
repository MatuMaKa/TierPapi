package xyz.matumaka.tiers.util;

import xyz.matumaka.tiers.TierPapi;

public class ParseTierPlaceholder {

    private static TierPapi plugin = TierPapi.getInstance();

    public static String parseTierPlaceholder(MiniJSONObject json, String params) {
        params = params.toLowerCase();
        String UNRANKED = TierPapi.getInstance().getConfig().getString("unranked", "-");

        // Simple points
        if (params.equals("points") && json.has("points")) {
            return String.valueOf(json.getInt("points"));
        }

        // Tier or Peak placeholders
        if (params.endsWith("_tier") || params.endsWith("_peak")) {
            String[] parts = params.split("_");
            if (parts.length < 2) return UNRANKED;

            String gamemode = parts[0];
            boolean isTier = params.endsWith("_tier");
            boolean isPeak = params.endsWith("_peak");

            if (!json.has("rankings")) return UNRANKED;
            MiniJSONObject rankings = json.getJSONObject("rankings");

            if (!rankings.has(gamemode)) return UNRANKED;
            MiniJSONObject modeObj = rankings.getJSONObject(gamemode);

            boolean retired = modeObj.has("retired") && modeObj.getBoolean("retired");
            String retiredPrefix = retired ? "R" : "";

            int pos = modeObj.has("pos") ? modeObj.getInt("pos") : 0;
            String prefixKey = String.valueOf(modeObj.has("tier") ? modeObj.getInt("tier") : 1);

            String prefix = plugin.getConfig().getString("tiers.prefix." + prefixKey, "");
            String suffix = plugin.getConfig().getString("tiers.suffix." + prefixKey, "");
            String format = plugin.getConfig().getString("tier", "%prefix% %tier% %suffix%");

            String tierValue = "";
            if (isTier) {
                int tier = modeObj.has("tier") ? modeObj.getInt("tier") : 0;
                tierValue = retiredPrefix + tier;
            } else if (isPeak) {
                int peakTier = modeObj.has("peak_tier") ? modeObj.getInt("peak_tier") : 0;
                tierValue = retiredPrefix + peakTier;
            }

            // Replace format placeholders
            return format.replace("%prefix%", prefix)
                    .replace("%tier%", tierValue)
                    .replace("%suffix%", suffix);
        }

        return UNRANKED;
    }
}
