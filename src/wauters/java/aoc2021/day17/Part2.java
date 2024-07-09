package com.github.aoc2021.day17;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class Part2 {

    public static void main(String[] args) {
        try (Stream<String> stream = Files.lines(Paths.get("input/aoc2021/day17.txt"))) {
            solve(stream.toArray(String[]::new)[0]);
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(String line) {
        String[] content = line.substring(13).split(", ");

        Grid grid = new Grid(
                Arrays.stream(content)
                        .flatMap(c -> Arrays.stream(c.substring(2).split("\\.\\.")))
                        .mapToInt(Integer::parseInt).toArray());

        int count = 0;

        // dy range [yMin, |yMin|]
        for (int dy = grid.yMin; dy <= Math.abs(grid.yMin); dy++) {
            // one dy can be in the correct range multiple times
            int[] numberOfSteps = getNumberOfSteps(grid, dy);

            if (numberOfSteps.length == 0) {
                continue;
            }

            // use set to eliminate duplicate entries
            Set<Integer> possibleXVelocities = new HashSet<>();
            for (int n : numberOfSteps) {
                possibleXVelocities.addAll(getXVelocitiesForNumberOfSteps(grid, n));
            }
            count += possibleXVelocities.size();
        }

        System.out.println(count);
    }

    private static int[] getNumberOfSteps(Grid grid, int dy) {
        List<Integer> possibleNumbersOfSteps = new ArrayList<>();
        int numberOfSteps = 0;
        int position = 0;
        while (!grid.overshotY(position)) {
            if (grid.fallsWithinY(position))
                possibleNumbersOfSteps.add(numberOfSteps);
            numberOfSteps++;
            position += dy;
            dy--;
        }
        return possibleNumbersOfSteps.stream().mapToInt(i -> i).toArray();
    }

    private static Set<Integer> getXVelocitiesForNumberOfSteps(Grid grid, int numberOfSteps) {
        int dxMax = (grid.xMax + (numberOfSteps * (numberOfSteps + 1)) / 2) / numberOfSteps;

        Set<Integer> xVelocities = new HashSet<>();
        for (int dx = 1; dx <= dxMax; dx++) {
            if (grid.fallsWithinX(getGridPositionX(dx, numberOfSteps))) {
                xVelocities.add(dx);
            }
        }

        return xVelocities;
    }

    private static int getGridPositionX(int dx, int numberOfSteps) {
        if (numberOfSteps > dx) {
            numberOfSteps = dx;
        }

        return numberOfSteps * dx - (numberOfSteps * (numberOfSteps + 1) / 2);
    }
}

class Grid {
    final int xMin;
    final int xMax;
    final int yMin;
    final int yMax;

    public Grid(int[] i) {
        xMin = i[0];
        xMax = i[1];
        yMin = i[2];
        yMax = i[3];
    }

    public boolean fallsWithinY(int y) {
        return yMin <= y && y <= yMax;
    }

    public boolean overshotY(int y) {
        return y < yMin;
    }

    public boolean fallsWithinX(int x) {
        return xMin <= x && x <= xMax;
    }
}