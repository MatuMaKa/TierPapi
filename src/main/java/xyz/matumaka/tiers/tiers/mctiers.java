package xyz.matumaka.tiers.tiers;

import xyz.matumaka.tiers.util.MiniJSONObject;
import xyz.matumaka.tiers.util.SimpleJsonParser;
import xyz.matumaka.tiers.util.parseTierPlaceholder;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class mctiers extends PlaceholderExpansion {

    @Override
    @NotNull
    public String getIdentifier() { return "mctiers"; }

    @Override
    @NotNull
    public String getAuthor() { return "MatuMaKa"; }

    @Override
    @NotNull
    public String getVersion() { return "1.0.0"; }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        String uuid = player.getUniqueId().toString().replace("-", "");

        try {
            String url = "https://mctiers.com/api/profile/" + uuid;
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Map<String, Object> map = SimpleJsonParser.parse(response.body());
            MiniJSONObject json = new MiniJSONObject(map);

            return parseTierPlaceholder.parseTierPlaceholder(json, params);

        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }
}
