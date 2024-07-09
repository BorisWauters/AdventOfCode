package com.github.aoc2023.day02;

import com.github.Solution;

public class Part2 extends Solution {
    @Override
    protected void solve(String[] lines) {
        int sum = 0;
        for (String line : lines) {
            sum += powerOfGame(line.split(": ")[1]);
        }

        System.out.println(sum);
    }

    private int powerOfGame(String gameString) {
        Game game = new Game();
        String[] subsets = gameString.split("; ");
        for (String subset : subsets) {
            game.addSubset(subset);
        }
        return game.getPower();
    }

    static class Game {
        private int maxRed = 0;
        private int maxGreen = 0;
        private int maxBlue = 0;

        public int getPower() {
            return maxRed * maxGreen * maxBlue;
        }

        public void addSubset(String subset) {
            String[] cubes = subset.split(", ");
            for (String cube : cubes) {
                addCube(cube);
            }
        }

        private void addCube(String cube) {
            String[] split = cube.split(" ");
            int value = Integer.parseInt(split[0]);
            String color = split[1];

            switch (color) {
                case "red" -> maxRed = Math.max(maxRed, value);
                case "green" -> maxGreen = Math.max(maxGreen, value);
                case "blue" -> maxBlue = Math.max(maxBlue, value);
                default -> throw new IllegalArgumentException("Invalid color: " + color);
            }
        }
    }
}
