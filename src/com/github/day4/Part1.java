package com.github.day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Part1 {

    public static void main(String[] args) {
        try (Stream<String> stream = Files.lines(Paths.get("input/test.txt"))) {
            solve(stream.toArray(String[]::new));
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(String[] lines) {
        for (int i = 0; i < lines.length; i++) {
            //todo
        }
    }
}
