package com.github.aoc2021.day07;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class Part1 {

    public static void main(String[] args) {
        try (Stream<String> stream = Files.lines(Paths.get("input/aoc2021/day07.txt"))) {
            int[] input = stream.flatMap(s -> Arrays.stream(s.split(","))).mapToInt(Integer::parseInt).toArray();
            solve(input);
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(int[] crabPositions) {
        Arrays.sort(crabPositions);

        int median = crabPositions.length / 2;
        int sum;
        if (crabPositions.length % 2 == 0) {
            //Both median values are the same so the Math.min is not necessary for the given input data
            sum = Math.min(
                    getTotalFuelCost(crabPositions, crabPositions[median]),
                    getTotalFuelCost(crabPositions, crabPositions[median - 1])
            );
        } else {
            //Unused, input has even size
            sum = getTotalFuelCost(crabPositions, crabPositions[median]);
        }
        System.out.println(sum);
    }

    private static int getTotalFuelCost(int[] crabPositions, int endPosition) {
        int sum = 0;
        for (int crabPosition : crabPositions) {
            sum += Math.abs(crabPosition - endPosition);
        }
        return sum;
    }
}
