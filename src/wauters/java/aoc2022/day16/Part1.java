package wauters.java.aoc2022.day16;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Part1 {

    private static int best = 0;

    public static void main(String[] args) {
        try (Stream<String> stream = Files.lines(Paths.get("input/aoc2022/day16.txt"))) {
            solve(stream.toArray(String[]::new));
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(String[] lines) {
        Map<String, Valve> valves = readInput(lines);
        completeDistanceMaps(valves);
        removeUselessEntries(valves);

        int result = assign(30, 0, "AA", valves.values().stream().sorted().toList());

        System.out.println(result);
    }

    private static Map<String, Valve> readInput(String[] lines) {
        Map<String, Valve> valves = new HashMap<>();
        for (String line : lines) {
            String[] lineParts = line.split(";");
            String key = lineParts[0].substring(6, 8);
            int flowRate = Integer.parseInt(lineParts[0].substring(23));
            String[] tunnels = lineParts[1].replaceAll("\\s", "")
                    .replaceAll("[a-z]", "")
                    .split(",");

            valves.put(key, new Valve(key, flowRate, tunnels));
        }
        return valves;
    }

    private static void completeDistanceMaps(Map<String, Valve> valves) {
        for (int i = 2; i < valves.size(); i++) {
            int finalI = i;
            boolean modified = false;
            for (Valve valve : valves.values()) {
                Set<String> newEntries = valve.distances.keySet().stream()
                        .flatMap(key -> valves.get(key).tunnels.stream())
                        .filter(key -> !valve.distances.containsKey(key))
                        .collect(Collectors.toSet());
                modified |= !newEntries.isEmpty();
                newEntries.forEach(e -> valve.distances.put(e, finalI));
            }
            if (!modified) {
                return;
            }
        }
    }

    private static void removeUselessEntries(Map<String, Valve> valves) {
        Set<String> entriesToRemove = new HashSet<>();
        for (var entry : valves.values()) {
            if (entry.flowRate == 0) {
                entriesToRemove.add(entry.key);
            }
        }
        for (var entry : entriesToRemove) {
            valves.remove(entry);
        }
    }

    private static int assign(int time, int score, String position, List<Valve> valvesToAdd) {
        int bestFutureScore = 0;
        int tempTime = time;
        for (Valve valve : valvesToAdd) {
            tempTime--;
            if (tempTime < 0) {
                break;
            }
            bestFutureScore += tempTime * valve.flowRate;
        }

        int bound = score + bestFutureScore;

        if (bound < best) {
            return 0;
        }

        int localBest = score;
        for (Valve valve : valvesToAdd) {
            int updatedTime = time - 1 - valve.distances.get(position);
            int updatedScore = score + (updatedTime * valve.flowRate);

            if (updatedTime < 0) {
                continue;
            }

            List<Valve> updatedValves = new ArrayList<>();
            for (Valve v : valvesToAdd) {
                if (v != valve) {
                    updatedValves.add(v);
                }
            }

            int endScore = assign(updatedTime, updatedScore, valve.key, updatedValves);
            localBest = Math.max(localBest, endScore);
            if (endScore > best) {
                best = endScore;
            }

        }
        return localBest;
    }
}
