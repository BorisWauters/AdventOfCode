package com.github;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Template {

    public static void main(String[] args) {
        // write your code here
        try (Stream<String> stream = Files.lines(Paths.get("input/test.txt"))) {
            solve(stream.toArray(String[]::new));
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(String[] lines) throws IOException {
        for (int i = 0; i < lines.length; i++) {
            //todo
        }
    }
}
