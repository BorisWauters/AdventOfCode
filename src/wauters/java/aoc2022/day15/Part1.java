package wauters.java.aoc2022.day15;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class Part1 {

    public static void main(String[] args) {
        try (Stream<String> stream = Files.lines(Paths.get("input/aoc2022/day15.txt"))) {
            solve(stream.toArray(String[]::new));
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(String[] lines) {
        int y = 2000000;
        List<SensorEntry> entries = SensorEntry.fetch(lines);

        Set<Integer> infeasibleXPositions = new HashSet<>();

        for (var entry : entries) {
            infeasibleXPositions.addAll(entry.caclulateInfeasibleBeaconPoints(y));
        }

        System.out.println(infeasibleXPositions.size());
    }
}
