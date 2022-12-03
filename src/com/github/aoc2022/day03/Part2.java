package com.github.aoc2022.day03;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class Part2 {
    public static void main(String[] args) {
        try (Stream<String> stream = Files.lines(Paths.get("input/aoc2022/day03.txt"))) {
            solve(stream.toArray(String[]::new));
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(String[] lines) {
        int totalScore = 0;
        for (int i = 0; i < lines.length/3; i++) {
            String l1 = lines[3*i];
            String l2 = lines[3*i + 1];
            String l3 = lines[3*i + 2];
        
            String common = extractCommonChar(l1, l2, l3);

            int score = convertScore(common);
            totalScore += score;
        }

        System.out.println(totalScore);
    }

    private static String extractCommonChar(String rs1, String rs2, String rs3) {
        Set<Character> set = new HashSet<>();
        Set<Character> set2 = new HashSet<>();

        for (char c : rs1.toCharArray()) {
            set.add(c);
        }

        for (char c : rs2.toCharArray()) {
            if (set.contains(c)) {
                set2.add(c);
            }
        }

        for (char c : rs3.toCharArray()) {
            if (set2.contains(c)) {
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
