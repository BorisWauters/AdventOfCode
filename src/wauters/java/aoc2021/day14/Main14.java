package wauters.java.aoc2021.day14;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Main14 {

    public static void main(String[] args) {
        try (Stream<String> stream = Files.lines(Paths.get("input/aoc2021/day14.txt"))) {
            solve(stream.toArray(String[]::new), 40);
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(String[] lines, int steps) {
        Map<String, Long> polymerMap = new HashMap<>();
        Map<String, String> elementMap = new HashMap<>();
        Map<String, Long> quantityMap = new HashMap<>();

        for (int i = 1; i < lines[0].length(); i++) {
            String pair = lines[0].substring(i - 1, i + 1);
            polymerMap.putIfAbsent(pair, 0L);
            polymerMap.computeIfPresent(pair, (k, v) -> ++v);
        }

        for (char c : lines[0].toCharArray()) {
            updateQuantityMap(quantityMap, Character.toString(c), 1L);
        }

        for (int i = 2; i < lines.length; i++) {
            String[] lineArguments = lines[i].split(" -> ");
            String pair = lineArguments[0];
            String elementToInsert = lineArguments[1];

            elementMap.put(pair, elementToInsert);
        }

        for (int i = 0; i < steps; i++) {
            Map<String, Long> updatedPolymerMap = new HashMap<>();
            for (var polymerEntry : polymerMap.entrySet()) {
                String pair = polymerEntry.getKey();
                long value = polymerEntry.getValue();

                if (elementMap.containsKey(pair)) {
                    String firstNewPair = pair.charAt(0) + elementMap.get(pair);
                    updatedPolymerMap.putIfAbsent(firstNewPair, 0L);
                    updatedPolymerMap.computeIfPresent(firstNewPair, (k, v) -> v + value);

                    String secondNewPair = elementMap.get(pair) + pair.substring(1);
                    updatedPolymerMap.putIfAbsent(secondNewPair, 0L);
                    updatedPolymerMap.computeIfPresent(secondNewPair, (k, v) -> v + value);

                    updateQuantityMap(quantityMap, elementMap.get(pair), value);
                } else {
                    updatedPolymerMap.putIfAbsent(pair, 0L);
                    updatedPolymerMap.computeIfPresent(pair, (k, v) -> v + value);
                }
            }
            polymerMap = updatedPolymerMap;
        }

        long max = quantityMap.values().stream().max(Long::compareTo).orElseThrow();
        long min = quantityMap.values().stream().min(Long::compareTo).orElseThrow();
        System.out.println(max - min);
    }

    private static void updateQuantityMap(Map<String, Long> quantityMap, String element, long value) {
        quantityMap.putIfAbsent(element, 0L);
        quantityMap.computeIfPresent(element, (k, v) -> v + value);
    }
}
