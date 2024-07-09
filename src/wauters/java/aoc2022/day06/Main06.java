package wauters.java.aoc2022.day06;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class Main06 {

    public static void main(String[] args) {
        try (Stream<String> stream = Files.lines(Paths.get("input/aoc2022/day06.txt"))) {
            solve(stream.toArray(String[]::new)[0]);
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(String line) {
        System.out.println("Part 1: " + findMarker(line, 4));
        System.out.println("Part 2: " + findMarker(line, 14));
    }

    private static int findMarker(String line, int marker) {
        for (int i = marker; i < line.length(); i++) {
            String subString = line.substring(i-marker, i);
            int s = (int) Arrays.stream(subString.split("")).distinct().count();
            if (s == marker) {
               return i;
            }
        }
        throw new RuntimeException("Invalid input");
    }
}
