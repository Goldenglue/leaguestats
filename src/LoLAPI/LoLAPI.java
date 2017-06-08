package LoLAPI;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;
import util.PrettyPrinter;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by IvanOP on 07.06.2017.
 */
public class LoLAPI {
    private static final HttpClient client = HttpClient.newHttpClient();
    private static String APIKEY = "";

    static {
        try {
            APIKEY = Files.readAllLines(Paths.get("apikey.txt")).get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JsonNode getJsonNodeFromResponse(String uriString) {
        HttpRequest request = HttpRequest.newBuilder(URI.create(uriString)).GET().header("X-Riot-Token", APIKEY).build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandler.asString());
            if (response.statusCode() == 200) {
                return new ObjectMapper().readTree(response.body());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JsonNode getSummonerInfo(String name) {
        name = name.replaceAll(" ", "");
        JsonNode rootNode = getJsonNodeFromResponse("https://euw1.api.riotgames.com/lol/summoner/v3/summoners/by-name/" + name);
        PrettyPrinter.prettyPrintJSonNode(rootNode);
        return rootNode;
    }


    public static List<String> getMatchesIdByAccountIdAndChampion(String accountId, String championId) {
        JsonNode rootNode = getJsonNodeFromResponse("https://euw1.api.riotgames.com/lol/match/v3/matchlists/by-account/" + accountId + "?queue=420&champion=" + championId);
        if (rootNode == null) {
            return null;
        }
        JsonNode childNode = rootNode.get("matches");
        List<String> setOfId = new ArrayList<>();
        childNode.forEach(node -> setOfId.add(node.get("gameId").asText()));
        return setOfId;

    }

    public static JsonNode getMatchById(String id) {
        return getJsonNodeFromResponse("https://euw1.api.riotgames.com/lol/match/v3/matches/" + id);
    }

    static Map<String, String> getChampionIdToName() {
        JsonNode rootNode = getJsonNodeFromResponse("https://euw1.api.riotgames.com/lol/static-data/v3/champions?dataById=true");
        return StreamSupport.stream(rootNode.get("data").spliterator(), false)
                .collect(Collectors.toMap(node -> node.get("name").asText(), node -> node.get("id").asText()));
    }

    static Map<String, String> getItemIdToItemImage() {
        JsonNode rootNode = getJsonNodeFromResponse("https://euw1.api.riotgames.com/lol/static-data/v3/items?itemListData=image");
        return StreamSupport.stream(rootNode.get("data").spliterator(), false)
                .collect(Collectors.toMap(node -> node.get("id").asText(), node -> node.get("image").get("full").asText()));
    }

}
