package xyz.matumaka.tiers.util;

import xyz.matumaka.tiers.TierPapi;

public class ParseTierPlaceholder {

    private final static TierPapi plugin = TierPapi.getInstance();

    public static String parseTierPlaceholder(MiniJSONObject json, String params) {
        params = params.toLowerCase();
        String UNRANKED = TierPapi.getInstance().getConfig().getString("unranked", "-");

        if (params.equals("points") && json.has("points")) {
            return String.valueOf(json.getInt("points"));
        }

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
            String tierPos = pos == 1 ? "LT" : "HT";
            String prefixKey = String.valueOf(modeObj.has("tier") ? modeObj.getInt("tier") : 1);

            int tier = modeObj.has("tier") ? modeObj.getInt("tier") : 0;
            String tierString = tierPos + tier;
            int peakTier = modeObj.has("peak_tier") ? modeObj.getInt("peak_tier") : tier;
            int peakPos = modeObj.has("peak_pos") ? modeObj.getInt("peak_pos") : pos;
            String peakPosString = peakPos == 1 ? "LT" : "HT";
            String peakString = peakPosString + peakTier;

            String prefix = plugin.getConfig().getString("tiers.prefix." + prefixKey, "");
            String suffix = plugin.getConfig().getString("tiers.suffix." + prefixKey, "");
            String format = plugin.getConfig().getString("tier", "%prefix% %tier% %suffix%");
            
            String tierValue = "";
            if (isTier) {
                tierValue = retiredPrefix + tierString;
            } else if (isPeak) {
                tierValue = retiredPrefix + peakString;
            }

            return format.replace("%prefix%", prefix)
                    .replace("%tier%", tierValue)
                    .replace("%suffix%", suffix);
        }

        return UNRANKED;
    }
}
