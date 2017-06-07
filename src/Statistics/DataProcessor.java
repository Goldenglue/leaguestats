package Statistics;

import LoLAPI.LoLAPI;
import LoLAPI.StaticData;
import com.fasterxml.jackson.databind.JsonNode;
import util.PrettyPrinter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

/**
 * Created by IvanOP on 07.06.2017.
 */
public class DataProcessor {

    public static Map<String, String> getInfo(String name, String champion) {
        Map<String, String> mapOfInfoFromSingleMatch = new HashMap<>();
        JsonNode matchStats = getMatchJson(name, champion);
        PrettyPrinter.prettyPrintJSonNode(matchStats);
        mapOfInfoFromSingleMatch.put("kda", getKDA(matchStats));
        return mapOfInfoFromSingleMatch;
    }

    private static String getKDA(JsonNode rootNode) {
        double kills = rootNode.get("kills").asDouble();
        double deaths = rootNode.get("deaths").asDouble();
        double assists = rootNode.get("assists").asDouble();
        String KDA = String.valueOf(((kills + assists) / deaths));
        if (KDA.length() > 4) {
            return KDA.substring(0,4);
        } else {
            return KDA;
        }
    }

    private static JsonNode getMatchJson(String name, String champion) {
        String championId = StaticData.getChampionId(champion);
        JsonNode summonerInfo = LoLAPI.getSummonerInfo(name);
        List<String> matchIds = LoLAPI.getMatchesIdByAccountIdAndChampion(summonerInfo.get("accountId").asText(), championId);
        JsonNode matchInfo = LoLAPI.getMatchById(matchIds.get(0));
        int participantId = StreamSupport.stream(matchInfo.get("participantIdentities").spliterator(), false)
                .filter(node -> node.get("player").get("currentAccountId").equals(summonerInfo.get("accountId")))
                .mapToInt(node -> node.get("participantId").asInt())
                .sum();
        return matchInfo.get("participants").get(participantId - 1).get("stats");
    }
}
