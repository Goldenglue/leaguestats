package LoLAPI;

import java.util.Map;

/**
 * Created by IvanOP on 07.06.2017.
 */
public class StaticData {
    public static Map<String, String> championIdToName;
    static {
        championIdToName = LoLAPI.getChampionIdToName();
    }

    public static String getChampionId(String name) {
        return championIdToName.get(name);
    }
}
