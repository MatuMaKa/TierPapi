package xyz.matumaka.tiers.util;

import java.util.Map;

public class MiniJSONObject {
    private final Map<String, Object> map;

    public MiniJSONObject(Map<String, Object> map) {
        this.map = map;
    }

    public boolean has(String key) {
        return map.containsKey(key);
    }

    public int getInt(String key) {
        Object val = map.get(key);
        if (val instanceof Number) return ((Number) val).intValue();
        try { return Integer.parseInt(val.toString()); }
        catch (Exception e) { return 0; }
    }

    public boolean getBoolean(String key) {
        Object val = map.get(key);
        if (val instanceof Boolean) return (Boolean) val;
        return Boolean.parseBoolean(val.toString());
    }

    public MiniJSONObject getJSONObject(String key) {
        Object val = map.get(key);
        if (val instanceof Map<?, ?>) {
            return new MiniJSONObject((Map<String, Object>) val);
        }
        return new MiniJSONObject(Map.of());
    }
}
