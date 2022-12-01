package com.github.aoc2021.day09;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Part1 {

    public static void main(String[] args) {
        try (Stream<String> stream = Files.lines(Paths.get("input/day09.txt"))) {
            solve(stream.toArray(String[]::new));
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(String[] lines) {
        int[][] heightMap = new int[lines.length][lines[0].length()];
        int sum = 0;
        for (int i = 0; i < lines.length; i++) {
            for (int j = 0; j < lines[i].length(); j++) {
                char c = lines[i].charAt(j);
                int value = Integer.parseInt(String.valueOf(c));
                heightMap[i][j] = value;
            }
        }

        for (int i = 0; i < heightMap.length; i++) {
            for (int j = 0; j < heightMap[0].length; j++) {
                if (higherUp(heightMap, i, j) && higherDown(heightMap, i, j) && higherLeft(heightMap, i, j) && higherRight(heightMap, i, j)) {
                    sum += heightMap[i][j];
                    sum++;
                }
            }
        }
        System.out.println(sum);
    }

    private static boolean higherUp(int[][] heightMap, int i, int j) {
        if (i < 1) return true;
        return heightMap[i][j] < heightMap[i - 1][j];
    }

    private static boolean higherDown(int[][] heightMap, int i, int j) {
        if (i > heightMap.length - 2) return true;
        return heightMap[i][j] < heightMap[i + 1][j];
    }

    private static boolean higherLeft(int[][] heightMap, int i, int j) {
        if (j < 1) return true;
        return heightMap[i][j] < heightMap[i][j - 1];
    }

    private static boolean higherRight(int[][] heightMap, int i, int j) {
        if (j > heightMap[0].length - 2) return true;
        return heightMap[i][j] < heightMap[i][j + 1];
    }
}
