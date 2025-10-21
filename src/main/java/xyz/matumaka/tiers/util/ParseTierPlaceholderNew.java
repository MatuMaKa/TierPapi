package xyz.matumaka.tiers.util;

import xyz.matumaka.tiers.TierPapi;

import java.util.List;
import java.util.Map;

public class ParseTierPlaceholderNew {// You can later load this from config

    public static String parseTierPlaceholderNew(Object jsonData, String params) {
        String UNRANKED = TierPapi.getInstance().getConfig().getString("unranked", "-");
        if (!(jsonData instanceof List)) return UNRANKED;

        params = params.toLowerCase();

        List<Object> players = (List<Object>) jsonData;
        if (players.isEmpty()) return UNRANKED;

        Map<String, Object> player = (Map<String, Object>) players.get(0);

        // Total points
        if (params.equals("points") && player.containsKey("total_points")) {
            return String.valueOf(player.get("total_points"));
        }

        // Kit-specific placeholders
        if (params.endsWith("_tier") || params.endsWith("_peak") || params.endsWith("_points")) {
            String[] parts = params.split("_");
            if (parts.length < 2) return UNRANKED;

            String kitName = params.substring(0, params.lastIndexOf('_')); // handle underscores
            String type = parts[parts.length - 1]; // tier / peak / points

            if (!player.containsKey("kits")) return UNRANKED;
            List<Object> kits = (List<Object>) player.get("kits");

            for (Object obj : kits) {
                Map<String, Object> kit = (Map<String, Object>) obj;
                String jsonKitName = kit.get("kit_name").toString().toLowerCase();

                if (jsonKitName.equals(kitName)) {
                    boolean retired = kit.containsKey("retired") && (boolean) kit.get("retired");
                    String retiredPrefix = retired ? "R" : "";

                    switch (type) {
                        case "tier":
                            if (kit.containsKey("tier_name") && kit.get("tier_name") != null)
                                return retiredPrefix + kit.get("tier_name").toString();
                            break;
                        case "peak":
                            Object peak = kit.get("peak_tier_name");
                            if (peak != null) return peak.toString();
                            if (kit.containsKey("tier_name") && kit.get("tier_name") != null)
                                return retiredPrefix + kit.get("tier_name").toString();
                            break;
                        case "points":
                            if (kit.containsKey("points")) return String.valueOf(kit.get("points"));
                            break;
                    }

                    return UNRANKED; // fallback if type exists but data is null
                }
            }

            // Kit not found
            return UNRANKED;
        }

        return "";
    }
}
