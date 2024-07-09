package wauters.java.aoc2023.day02;

import wauters.java.Solution;

public class Part1 extends Solution {
    @Override
    protected void solve(String[] lines) {
        int count = 0;
        int sum = 0;
        for (String line : lines) {
            count++;

            if (isGamePossible(line.split(": ")[1])) {
                sum += count;
            }
        }

        System.out.println(sum);
    }

    private boolean isGamePossible(String game) {
        String[] subsets = game.split("; ");
        for (String subset : subsets) {
            if (!isSubsetPossible(subset)) {
                return false;
            }
        }
        return true;
    }

    private boolean isSubsetPossible(String subset) {
        String[] cubes = subset.split(", ");
        for (String cube : cubes) {
            if (!isCubePossible(cube)) {
                return false;
            }
        }
        return true;
    }

    private boolean isCubePossible(String cube) {
        String[] split = cube.split(" ");
        int value = Integer.parseInt(split[0]);
        String color = split[1];

        return switch (color) {
            case "red" -> value <= 12;
            case "green" -> value <= 13;
            case "blue" -> value <= 14;
            default -> false;
        };
    }
}
