package com.github.day01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Part2 {

    public static void main(String[] args) {
        try (Stream<String> stream = Files.lines(Paths.get("input/day01.txt"))) {
            solve(stream.mapToInt(Integer::parseInt).toArray());
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(int[] lines) {
        int count = 0;
        int previousSum = lines[0] + lines[1] + lines[2];
        for (int i = 3; i < lines.length; i++) {
            int sum = lines[i] + lines[i - 1] + lines[i - 2];

            if (previousSum < sum)
                count++;

            previousSum = sum;
        }
        System.out.println(count);
    }
}
