package com.github.aoc2021.day12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class Part2 {

    private static final Map<String, List<String>> adjacencyMap = new HashMap<>();
    private static final String START = "start";
    private static final String END = "end";

    private static int numberOfPaths = 0;

    public static void main(String[] args) {
        try (Stream<String> stream = Files.lines(Paths.get("input/aoc2021/day12.txt"))) {
            solve(stream.toArray(String[]::new));
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(String[] lines) {
        for (String line : lines) {
            String[] caves = line.split("-");
            addCavesToAdjacencyMap(caves[0], caves[1]);
        }
        Deque<String> startPath = new ArrayDeque<>();
        startPath.add(START);
        findPath(startPath, new HashSet<>(), true);

        System.out.println(numberOfPaths);
    }

    private static void findPath(Deque<String> path, Set<String> cavesToAvoid, boolean allowCaveToAvoid) {
        String lastCave = path.peekLast();
        if (END.equals(lastCave)) {
            numberOfPaths++;
            return;
        }
        if (isSmallCave(lastCave))
            cavesToAvoid.add(lastCave);

        for (String cave : adjacencyMap.get(lastCave)) {
            if (START.equals(cave) || (cavesToAvoid.contains(cave) && !allowCaveToAvoid))
                continue;

            Deque<String> newPath = new ArrayDeque<>(path);
            newPath.add(cave);
            boolean allowCaveToAvoidGoingForward = allowCaveToAvoid;

            if (cavesToAvoid.contains(cave))
                allowCaveToAvoidGoingForward = false;

            findPath(newPath, new HashSet<>(cavesToAvoid), allowCaveToAvoidGoingForward);
        }
    }

    private static void addCavesToAdjacencyMap(String cave1, String cave2) {
        adjacencyMap.putIfAbsent(cave1, new ArrayList<>());
        adjacencyMap.get(cave1).add(cave2);

        adjacencyMap.putIfAbsent(cave2, new ArrayList<>());
        adjacencyMap.get(cave2).add(cave1);
    }

    private static boolean isSmallCave(String cave) {
        return cave.equals(cave.toLowerCase());
    }
}
