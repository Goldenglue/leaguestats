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

    public static String getSummonerIdFromJsonNode(JsonNode node) {
        return "Summoner id: " + node.get("id").asText();
    }

    public static Map<String, String> getChampionIdToName() {
        JsonNode rootNode = getJsonNodeFromResponse("https://euw1.api.riotgames.com/lol/static-data/v3/champions?dataById=true");
        return StreamSupport.stream(rootNode.get("data").spliterator(), false)
                .collect(Collectors.toMap(node -> node.get("name").asText(), node -> node.get("id").asText()));
    }

    public static String getSummonerInfo(String name) {
        name = name.replaceAll(" ","");
        JsonNode rootNode = getJsonNodeFromResponse("https://euw1.api.riotgames.com/lol/summoner/v3/summoners/by-name/" + name);
        PrettyPrinter.prettyPrintJSonNode(rootNode);
        return rootNode.get("accountId").asText();
    }
}
