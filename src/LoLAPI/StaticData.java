package LoLAPI;

import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Created by IvanOP on 07.06.2017.
 */
public class StaticData {
    public static Map<String, String> championIdToName;
    public static Map<String, String> itemIdToItemImage;

    static {
        championIdToName = LoLAPI.getChampionIdToName();
        itemIdToItemImage = LoLAPI.getItemIdToItemImage();
    }

    static {

    }

    public static String getChampionId(String name) {
        return championIdToName.get(name);
    }

}
