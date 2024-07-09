package wauters.java.aoc2022.day01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Part1 {

    public static void main(String[] args) {
        try (Stream<String> stream = Files.lines(Paths.get("input/aoc2022/day01.txt"))) {
            solve(stream.toArray(String[]::new));
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(String[] lines) {
        int maxSum = 0;
        int currentSum = 0;
        for (String line : lines) {
            if (line.isBlank()) {
                maxSum = Math.max(maxSum, currentSum);
                currentSum = 0;
            } else {
                currentSum += Integer.parseInt(line);
            }
        }
        System.out.println(maxSum);
    }
}
