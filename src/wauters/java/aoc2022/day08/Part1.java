package com.github.aoc2022.day08;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Stream;

import com.github.helper.IntPair;

public class Part1 {

    public static void main(String[] args) {
        try (Stream<String> stream = Files.lines(Paths.get("input/aoc2022/day08.txt"))) {
            solve(stream.toArray(String[]::new));
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(String[] lines) {
        int length = lines.length;
        int width = lines[0].length();
        int totalNumberOfTrees = length * width;
        int[][] grid = new int[length][width];

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = Integer.parseInt(lines[i].substring(j, j + 1));
            }
        }

        //todo avoid duplication -> done in part 2
        Set<IntPair> invisibleX = new HashSet<>();
        for (int i = 1; i < length - 1; i++) {
            Set<IntPair> invisibleFB = new HashSet<>();

            // from front to back
            int tallest = grid[i][0];
            for (int j = 1; j < width - 1; j++) {
                int current = grid[i][j];
                if (current > tallest) {
                    tallest = current;
                    continue;
                }
                
                invisibleFB.add(new IntPair(i, j));
            }

            // from back to front
            tallest = grid[i][width - 1];
            for (int j = width - 2; j >= 1; j--) {
                int current = grid[i][j];
                if (current > tallest) {
                    tallest = current;
                    continue;
                }

                IntPair pair = new IntPair(i, j);
                if (invisibleFB.contains(pair)) {
                    invisibleX.add(pair);
                }
            }
        }

        Set<IntPair> invisible = new HashSet<>();
        for (int j = 1; j < length - 1; j++) {
            Set<IntPair> invisibleTB = new HashSet<>();

            // from top to bottom
            int tallest = grid[0][j];
            for (int i = 1; i < width - 1; i++) {
                int current = grid[i][j];
                if (current > tallest) {
                    tallest = current;
                    continue;
                }

                invisibleTB.add(new IntPair(i, j));
            }

            // from bottom to top
            tallest = grid[length - 1][j];
            for (int i = width - 2; i >= 1; i--) {
                int current = grid[i][j];
                if (current > tallest) {
                    tallest = current;
                    continue;
                }

                IntPair pair = new IntPair(i, j);
                if (invisibleTB.contains(pair) && invisibleX.contains(pair)) {
                    invisible.add(pair);
                }
            }
        }

        System.out.println(totalNumberOfTrees - invisible.size());
    }
}
