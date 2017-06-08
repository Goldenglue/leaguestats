package Statistics;

import LoLAPI.LoLAPI;
import LoLAPI.StaticData;
import com.fasterxml.jackson.databind.JsonNode;
import util.PrettyPrinter;

import java.util.*;
import java.util.stream.StreamSupport;

/**
 * Created by IvanOP on 07.06.2017.
 */
public class DataProcessor {
    private static String[] desirableStats = {"win",
            "kills", "deaths", "assists", "totalDamageDealtToChampions", "magicDamageDealtToChampions",
            "physicalDamageDealtToChampions", "trueDamageDealtToChampions", "visionScore", "goldEarned",
            "totalMinionsKilled", "neutralMinionsKilled", "neutralMinionsKilledTeamJungle",
            "neutralMinionsKilledEnemyJungle", "wardsPlaced"};
    private static String[] desirableItems = {"item0", "item1", "item2", "item3", "item4", "item5", "item6"};
    private static HashSet<String> setOfDesirableStats = new HashSet<>();
    private static HashSet<String> setOfDesirableItems = new HashSet<>();

    static {
        setOfDesirableStats.addAll(Arrays.asList(desirableStats));
        setOfDesirableItems.addAll(Arrays.asList(desirableItems));
    }

    public static Map<String, String> getInfo(String name, String champion) {
        Map<String, String> mapOfInfoFromSingleMatch = new HashMap<>();
        JsonNode matchStats = getMatchJson(name, champion);
        PrettyPrinter.prettyPrintJSonNode(matchStats);
        mapOfInfoFromSingleMatch.putAll(getCombatStats(matchStats.get("stats")));
        mapOfInfoFromSingleMatch.putAll(getItems(matchStats.get("stats")));
        return mapOfInfoFromSingleMatch;
    }

    private static Map<String, String> getCombatStats(JsonNode rootNode) {
        Map<String, String> mapOfCombatStats = new HashMap<>();
        double kills = rootNode.get("kills").asDouble();
        double deaths = rootNode.get("deaths").asDouble();
        double assists = rootNode.get("assists").asDouble();
        String KDA = String.valueOf(((kills + assists) / deaths));
        if (KDA.length() > 4) {
            KDA = KDA.substring(0, 5);
        }
        mapOfCombatStats.put("kda", KDA);
        Iterator<String> iterator = rootNode.fieldNames();
        while (iterator.hasNext()) {
            String currentValue = iterator.next();
            if ((setOfDesirableStats.contains(currentValue))) {
                System.out.println(currentValue);
                mapOfCombatStats.put(currentValue, rootNode.get(currentValue).asText());
            }
        }
        return mapOfCombatStats;
    }

    private static Map<String, String> getItems(JsonNode rootNode) {
        Map<String, String> mapOfItems = new HashMap<>();
        Iterator<String> iterator = rootNode.fieldNames();
        while (iterator.hasNext()) {
            String currentValue = iterator.next();
            if ((setOfDesirableItems.contains(currentValue))) {
                System.out.println(currentValue);
                mapOfItems.put(currentValue, rootNode.get(currentValue).asText());
            }
        }
        return mapOfItems;
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
        return matchInfo.get("participants").get(participantId - 1);
    }
}
