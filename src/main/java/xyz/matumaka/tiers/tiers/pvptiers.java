package xyz.matumaka.tiers.tiers;

import org.json.JSONObject;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import static xyz.matumaka.tiers.util.parseTierPlaceholder.parseTierPlaceholder;

public class pvptiers extends PlaceholderExpansion {
    private final ConcurrentHashMap<String, JSONObject> cache = new ConcurrentHashMap<>();

    @Override
    @NotNull
    public String getIdentifier() {
        return "pvptiers";
    }

    @Override
    @NotNull
    public String getAuthor() {
        return "MatuMaKa";
    }

    @Override
    @NotNull
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        String uuid = player.getUniqueId().toString().replace("-", "");

        if (cache.containsKey(uuid)) {
            JSONObject json = cache.get(uuid);
            return parseTierPlaceholder(json, params);
        }

        CompletableFuture.runAsync(() -> {
            try {
                String url = "https://pvptiers.com/api/profile/" + uuid;
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .build();

                HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
                JSONObject json = new JSONObject(response.body());
                cache.put(uuid, json);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        JSONObject json = cache.get(uuid);
        if (json == null) return "Loading...";
        return parseTierPlaceholder(json, params);
    }

}
