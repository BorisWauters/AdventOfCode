package wauters.java;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Launcher {

    public static void main(String[] args) {
        Solution solution = new wauters.java.aoc2023.day01.Part2();

        try (Stream<String> stream = Files.lines(Paths.get(solution.input))) {
            solution.solve(stream.toArray(String[]::new));
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }
}
