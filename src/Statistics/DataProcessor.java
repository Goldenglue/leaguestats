package Statistics;

import LoLAPI.LoLAPI;
import LoLAPI.StaticData;
import com.fasterxml.jackson.databind.JsonNode;
import util.PrettyPrinter;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by IvanOP on 07.06.2017.
 */
public class DataProcessor {
    public static String getKDA(String name, String champion) {
        String championId = StaticData.getChampionId(champion);
        JsonNode summonerInfo = LoLAPI.getSummonerInfo(name);
        List<String> matchIds = LoLAPI.getMatchesIdByAccountIdAndChampion(summonerInfo.get("accountId").asText(), championId);
        JsonNode matchInfo = LoLAPI.getMatchById(matchIds.get(0));
        /*PrettyPrinter.prettyPrintJSonNode(matchStats.get("participants"));
        PrettyPrinter.prettyPrintJSonNode(matchStats.get("participantIdentities"));*/
        int participantId = StreamSupport.stream(matchInfo.get("participantIdentities").spliterator(), false)
                .filter(node -> node.get("player").get("currentAccountId").equals(summonerInfo.get("accountId")))
                .mapToInt(node -> node.get("participantId").asInt())
                .sum();
        JsonNode matchStats = matchInfo.get("participants").get(participantId - 1).get("stats");
        PrettyPrinter.prettyPrintJSonNode(matchStats);
        double kills = matchStats.get("kills").asDouble();
        double deaths = matchStats.get("deaths").asDouble();
        double assists = matchStats.get("assists").asDouble();
        String KDA = String.valueOf(((kills + assists) / deaths));
        if (KDA.length() > 4) {
            return KDA.substring(0, 4);
        } else
            return KDA;
    }
}
