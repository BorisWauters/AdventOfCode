package com.github.day07;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class Part2 {

    public static void main(String[] args) {
        try (Stream<String> stream = Files.lines(Paths.get("input/day07.txt"))) {
            int[] input = stream.flatMap(s -> Arrays.stream(s.split(","))).mapToInt(Integer::parseInt).toArray();
            solve(input);
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(int[] crabPositions) {
        // In some cases the floored average gives the right answer
        double average = Arrays.stream(crabPositions).average().getAsDouble();
        int flooredAverage = (int) average;
        int roundedAverage = (int) Math.round(average);
        int sum = Math.min(
                getTotalFuelCost(crabPositions, flooredAverage),
                getTotalFuelCost(crabPositions, roundedAverage)
        );
        System.out.println(sum);
    }

    private static int getTotalFuelCost(int[] crabPositions, int endPosition) {
        int sum = 0;
        for (int crabPosition : crabPositions) {
            int steps = Math.abs(crabPosition - endPosition);
            if (steps % 2 == 0)
                sum += (steps / 2) * (steps + 1);
            else
                sum += (steps / 2) * steps + steps;
        }
        return sum;
    }
}
