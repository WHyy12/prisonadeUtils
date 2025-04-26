package test.prisonadeutils.client;

import java.util.*;

public class CalculatorState {
    public static String selectedArea = "River";
    public static String selectedMix = "Stone - Iron Mix";
    public static List<String> ores = new ArrayList<>(List.of("Stone", "Iron"));
    public static List<Integer> amounts = new ArrayList<>(List.of(1, 1));
    public static Integer mixAmount = 1;
    public static final Map<String, Map<String, Map<String, Integer>>> AREAS = new LinkedHashMap<>();

    static {
        // Initialize River
        Map<String, Map<String, Integer>> river = new LinkedHashMap<>();
        river.put("Stone - Iron Mix", new LinkedHashMap<>() {{
            put("Stone", 1);
            put("Iron", 1);
        }});
        river.put("Iron - Diamond Mix", new LinkedHashMap<>() {{
            put("Iron", 4);
            put("Diamond", 4);
        }});
        river.put("Diamond - Emerald Mix", new LinkedHashMap<>() {{
            put("Diamond", 8);
            put("Emerald", 8);
        }});
        river.put("River Mix", new LinkedHashMap<>() {{
            put("Iron", 12);
            put("Diamond", 36);
            put("Emerald", 24);
        }});
        AREAS.put("River", river);

        // Initialize Ranch
        Map<String, Map<String, Integer>> ranch = new LinkedHashMap<>();
        ranch.put("Granite - Coal Mix", new LinkedHashMap<>() {{
            put("Granite", 2);
            put("Coal", 3);
        }});
        ranch.put("Coal - Copper Mix", new LinkedHashMap<>() {{
            put("Coal", 6);
            put("Copper", 5);
        }});
        ranch.put("Copper - Redstone Mix", new LinkedHashMap<>() {{
            put("Copper", 10);
            put("Redstone", 10);
        }});
        ranch.put("Ranch Mix", new LinkedHashMap<>() {{
            put("Coal", 30);
            put("Copper", 75);
            put("Redstone", 50);
        }});
        AREAS.put("Ranch", ranch);

        // Initialize Port
        Map<String, Map<String, Integer>> port = new LinkedHashMap<>();
        port.put("Sandstone - Prismarine Mix", new LinkedHashMap<>() {{
            put("Sandstone", 6);
            put("Prismarine", 6);
        }});
        port.put("Prismarine - Oceanstone Mix", new LinkedHashMap<>() {{
            put("Prismarine", 8);
            put("Oceanstone", 7);
        }});
        port.put("Oceanstone - Seashine Mix", new LinkedHashMap<>() {{
            put("Oceanstone", 13);
            put("Seashine", 13);
        }});
        port.put("Port Mix", new LinkedHashMap<>() {{
            put("Prismarine", 56);
            put("Oceanstone", 140);
            put("Seashine", 91);
        }});
        AREAS.put("Port", port);

        // Initialize Empire
        Map<String, Map<String, Integer>> empire = new LinkedHashMap<>();
        empire.put("Moss Stone - Star Rock Mix", new LinkedHashMap<>() {{
            put("Moss Stone", 7);
            put("Star Rock", 7);
        }});
        empire.put("Star Rock - Aztec Relic Mix", new LinkedHashMap<>() {{
            put("Star Rock", 10);
            put("Aztec Relic", 8);
        }});
        empire.put("Aztec Relic - Amethyst", new LinkedHashMap<>() {{
            put("Aztec Relic", 14);
            put("Amethyst", 14);
        }});
        empire.put("Empire Mix", new LinkedHashMap<>() {{
            put("Star Rock", 70);
            put("Aztec Relic", 154);
            put("Amethyst", 98);
        }});
        AREAS.put("Empire", empire);

        // Initialize Citadel
        Map<String, Map<String, Integer>> citadel = new LinkedHashMap<>();
        citadel.put("Marble - Purpur Mix", new LinkedHashMap<>() {{
            put("Marble", 9);
            put("Purpur", 9);
        }});
        citadel.put("Purpur - Magma Mix", new LinkedHashMap<>() {{
            put("Purpur", 11);
            put("Magma", 11);
        }});
        citadel.put("Magma - Obsidian Mix", new LinkedHashMap<>() {{
            put("Magma", 16);
            put("Obsidian", 12);
        }});
        citadel.put("Citadel Mix", new LinkedHashMap<>() {{
            put("Purpur", 110);
            put("Magma", 270);
            put("Obsidian", 120);
        }});
        AREAS.put("Citadel", citadel);
    }
}
