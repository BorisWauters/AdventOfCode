package com.github.aoc2022.day08;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import com.github.helper.IntPair;

public class Part2 {

    public static void main(String[] args) {
        try (Stream<String> stream = Files.lines(Paths.get("input/aoc2022/day08.txt"))) {
            solve(stream.toArray(String[]::new));
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(String[] lines) {
        Grid grid = new Grid(lines);
        System.out.println(grid.getHighestScenicScore());
    }
}

class Grid {
    private int length;
    private int[][] grid;
    private Map<IntPair, Integer> scenicScores;

    // Assuming a N x N grid
    public Grid(String[] lines) {
        this.length = lines.length;
        this.grid = new int[length][length];
        this.scenicScores = new HashMap<>();

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                grid[i][j] = Integer.parseInt(lines[i].substring(j, j + 1));
            }
        }
    }

    public int getHighestScenicScore() {
        setScenicScores();
        return scenicScores.values().stream().mapToInt(i -> i).max().getAsInt();
    }

    private void setScenicScores() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                for (Transposition transposition : Transposition.values()) {
                    setScenicScoreForTransposition(transposition, i, j);
                }
            }
        }
    }

    private void setScenicScoreForTransposition(Transposition transposition, int i, int j) {
        IntPair pair = transposition.getPair(grid, i, j);
        int scenicScore = getScenicScoreForTransposition(transposition, i, j);

        scenicScores.putIfAbsent(pair, 1);
        scenicScores.compute(pair, (k,v) -> v * scenicScore);
    }

    private int getScenicScoreForTransposition(Transposition transposition, int i, int j) {
        int current = transposition.get(grid, i, j);
        int scenicScore = 0;
        for (int k = j + 1; k < grid.length; k++) {
            int o = transposition.get(grid, i, k);
            scenicScore++;
            if (current <= o) {
                return scenicScore;
            }
        }
        return scenicScore;
    }
}

enum Transposition {
    TOP_DOWN,
    DOWN_TOP,
    LEFT_RIGHT,
    RIGHT_LEFT;

    public int get(int[][] grid, int i, int j) {
        IntPair pair = getPair(grid, i, j);
        return getWithPair(grid, pair);
    }

    public int getWithPair(int[][] grid, IntPair pair) {
        return grid[pair.getA()][pair.getB()];
    }

    public IntPair getPair(int[][] grid, int i, int j) {
        int length = grid.length;
        switch (this) {
            case TOP_DOWN:
                return new IntPair(i, j);
            case DOWN_TOP:
                return new IntPair(i, length - j - 1);
            case LEFT_RIGHT:
                return new IntPair(j, i);
            case RIGHT_LEFT:
                return new IntPair(length - j - 1, i);
            default:
                return new IntPair(i, j);
        }
    }
}
