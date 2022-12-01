package com.github.aoc2021.day11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Part1 {

    private static final int[][] octopuses = new int[10][10];
    private static int sum = 0;

    public static void main(String[] args) {
        try (Stream<String> stream = Files.lines(Paths.get("input/aoc2021/day11.txt"))) {
            solve(stream.toArray(String[]::new));
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(String[] lines) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                octopuses[i][j] = Integer.parseInt(String.valueOf(lines[i].charAt(j)));
            }
        }

        for (int s = 1; s <= 100; s++) {
            step();
        }

        System.out.println(sum);
    }

    public static void step() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (octopuses[i][j] != 0 && octopuses[i][j] <= 9 && allNeighborsFlashed(i, j)) {
                    octopuses[i][j] = 10;
                    sum++;
                }
            }
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                ignite(i, j);
            }
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (octopuses[i][j] > 9)
                    octopuses[i][j] = 0;
            }
        }
    }

    public static void ignite(int i, int j) {
        if (octopuses[i][j] > 9) return;

        octopuses[i][j]++;
        if (octopuses[i][j] <= 9)
            return;

        sum++;
        for (int k = -1; k <= 1; k++) {
            for (int l = -1; l <= 1; l++) {
                if ((k == 0 && l == 0) || i + k < 0 || j + l < 0 || i + k >= 10 || j + l >= 10)
                    continue;

                ignite(i + k, j + l);
            }
        }
    }

    public static boolean allNeighborsFlashed(int i, int j) {
        for (int k = -1; k <= 1; k++) {
            for (int l = -1; l <= 1; l++) {
                if ((k == 0 && l == 0) || i + k < 0 || j + l < 0 || i + k >= 10 || j + l >= 10)
                    continue;

                int val = octopuses[i + k][j + l];
                if (val != 9) {
                    return false;
                }
            }
        }
        return true;
    }
}
