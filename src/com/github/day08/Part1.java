package com.github.day08;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Stream;

public class Part1 {

    //Key: amount of segments on display, Value: display value
    private static final Map<Integer, Integer> digitMap = Map.of(
            2, 1,
            4, 4,
            3, 7,
            7, 8
    );

    private static int sum = 0;

    public static void main(String[] args) {
        try (Stream<String> stream = Files.lines(Paths.get("input/day08.txt"))) {
            solve(stream.toArray(String[]::new));
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(String[] lines) {
        for (String line : lines) {
            String[] digitIO = line.split(" \\| ");
            String[] digitOutput = digitIO[1].split(" ");
            for (String segment : digitOutput)
                checkMatchingDigits(segment.toCharArray());
        }
        System.out.println(sum);
    }

    private static void checkMatchingDigits(char[] segments) {
        if (digitMap.containsKey(segments.length))
            sum++;
    }
}
