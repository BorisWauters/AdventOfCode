package wauters.java.aoc2021.day14;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * This was a first solution, good enough for part 1 with only 10 steps.
 * This brute force approach is terribly inefficient when more than 15 steps are required like in part 2 (40 steps)
 * In Main14 a more efficient solution was implemented which works for both parts.
 */
public class Main14Inefficient {

    public static void main(String[] args) {
        try (Stream<String> stream = Files.lines(Paths.get("input/aoc2021/day14.txt"))) {
            solve(stream.toArray(String[]::new), 10);
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(String[] lines, int steps) {
        String polymerTemplate = lines[0];
        Map<String, String> elementMap = new HashMap<>();

        for (int i = 2; i < lines.length; i++) {
            String[] lineArguments = lines[i].split(" -> ");
            String pair = lineArguments[0];
            String elementToInsert = lineArguments[1];

            elementMap.put(pair, elementToInsert);
        }

        for (int i = 0; i < steps; i++) {
            for (int j = polymerTemplate.length() - 1; j > 0; j--) {
                String pair = polymerTemplate.substring(j - 1, j + 1);

                if (elementMap.containsKey(pair)) {
                    String start = polymerTemplate.substring(0, j);
                    String end = polymerTemplate.substring(j);
                    polymerTemplate = start + elementMap.get(pair) + end;
                }
            }
        }

        Map<Character, Long> quantityMap = new HashMap<>();
        for (char c : polymerTemplate.toCharArray()) {
            quantityMap.putIfAbsent(c, 0L);
            quantityMap.computeIfPresent(c, (k, v) -> ++v);
        }

        long max = quantityMap.values().stream().max(Long::compareTo).orElseThrow();
        long min = quantityMap.values().stream().min(Long::compareTo).orElseThrow();
        System.out.println(max - min);
    }
}
