package com.github.aoc2022.day16;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Not efficient but it works.
 */
public class Part2 {

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

        int time = 26;
        List<Valve> sorted = valves.values().stream().sorted().toList();

        int result = assign(
                new Walkers(new Walker("I", time, "AA"), new Walker("E", time, "AA")),
                0, sorted, "AA");

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

    private static int assign(Walkers walkers, int score, List<Valve> valvesToAdd, String helper) {
        int bestWalker = walk(score, valvesToAdd, helper, walkers.best(), walkers.other());
        int otherWalker = walk(score, valvesToAdd, helper, walkers.other(), walkers.best());

        return Math.max(bestWalker, otherWalker);
    }

    private static int walk(int score, List<Valve> valvesToAdd, String helper, Walker walker, Walker other) {
        int bestFutureScore = 0;
        int tempTime1 = walker.time();
        int tempTime2 = other.time();
        for (Valve valve : valvesToAdd) {
            if (tempTime1 < tempTime2) {
                int temp = tempTime1;
                tempTime1 = tempTime2;
                tempTime2 = temp;
            }

            if (tempTime1 > 0) {
                tempTime1--;
                bestFutureScore += tempTime1 * valve.flowRate;
            } else {
                break;
            }
        }

        int bound = score + bestFutureScore;
        if (bound < best) {
            return 0;
        }

        int localBest = score;
        for (Valve valve : valvesToAdd) {
            int updatedTime = walker.time() - 1 - valve.distances.get(walker.position());
            int updatedScore = score + (updatedTime * valve.flowRate);
            String helperString = helper + " | " + walker.key() + "-" + valve.key;

            if (updatedTime <= 0) {
                continue;
            }

            List<Valve> updatedValves = new ArrayList<>();
            for (Valve v : valvesToAdd) {
                if (v != valve) {
                    updatedValves.add(v);
                }
            }

            Walker updatedWalker = new Walker(walker.key(), updatedTime, valve.key);
            Walkers updatedWalkers = new Walkers(updatedWalker, other);

            int endScore = assign(updatedWalkers, updatedScore, updatedValves, helperString);
            localBest = Math.max(localBest, endScore);
            if (endScore > best) {
                best = endScore;
            }
        }
        return localBest;
    }
}
