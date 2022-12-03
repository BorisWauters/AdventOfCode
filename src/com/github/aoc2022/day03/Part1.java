package com.github.aoc2022.day03;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class Part1 {
    public static void main(String[] args) {
        try (Stream<String> stream = Files.lines(Paths.get("input/aoc2022/day03.txt"))) {
            solve(stream.toArray(String[]::new));
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(String[] lines) {
        int totalScore = 0;
        for (String line : lines) {
            int split = line.length() / 2;
            String comp1 = line.substring(0, split);
            String comp2 = line.substring(split);

            String common = extractCommonChar(comp1, comp2);

            int score = convertScore(common);
            totalScore += score;
        }

        System.out.println(totalScore);
    }

    private static String extractCommonChar(String comp1, String comp2) {
        Set<Character> set = new HashSet<>();

        for (char c : comp1.toCharArray()) {
            set.add(c);
        }

        for (char c : comp2.toCharArray()) {
            if (set.contains(c)) {
                return Character.toString(c);
            }
        }

        throw new RuntimeException("Illegal input");
    }

    private static int convertScore(String c) {
        int asciiValue = c.charAt(0);
        if (c.matches("[A-Z]")) {
            return asciiValue - 'A' + 27;
        }

        return asciiValue - 'a' + 1;
    }
}
