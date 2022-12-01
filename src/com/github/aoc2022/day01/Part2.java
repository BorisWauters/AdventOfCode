package com.github.aoc2022.day01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.stream.Stream;

public class Part2 {
    
    public static void main(String[] args) {
        try (Stream<String> stream = Files.lines(Paths.get("input/aoc2022/day01.txt"))) {
            solve(stream.toArray(String[]::new));
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(String[] lines) {
        PriorityQueue<Integer> calories = new PriorityQueue<>(Comparator.reverseOrder());
        int currentSum = 0;
        for (String line : lines) {
            if(line.isBlank()) {
                calories.add(currentSum);
                currentSum = 0;
            } else {
                currentSum += Integer.parseInt(line);
            }
        }

        int sum = 0;
        for (int i = 0; i < 3; i++) {
            sum += calories.poll();
        }
        System.out.println(sum);
    }
}
