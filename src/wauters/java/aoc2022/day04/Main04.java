package wauters.java.aoc2022.day04;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class Main04 {

    public static void main(String[] args) {
        try (Stream<String> stream = Files.lines(Paths.get("input/aoc2022/day04.txt"))) {
            solve(stream.toArray(String[]::new));
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(String[] lines) {
        int count1 = 0;
        int count2 = 0;
        for (String line : lines) {
            String[] sections = line.split(",");

            int[] i = Arrays.stream(sections)
                    .flatMap(s -> Arrays.stream(s.split("-")))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            if (completeOverlap(i[0], i[1], i[2], i[3])) {
                count1++;
            }
            if (overlap(i[0], i[1], i[2], i[3])) {
                count2++;
            }
        }

        System.out.println("Part1: " + count1);
        System.out.println("Part2: " + count2);
    }

    private static boolean completeOverlap(int min1, int max1, int min2, int max2) {
        if (min1 <= min2 && max1 >= max2)
            return true;

        if (min1 >= min2 && max1 <= max2)
            return true;

        return false;
    }

    private static boolean overlap(int min1, int max1, int min2, int max2) {
        return overlapsOver(min1, max1, min2, max2) || overlapsOver(min2, max2, min1, max1);
    }

    private static boolean overlapsOver(int min1, int max1, int min2, int max2) {
        if (min1 <= min2 && min2 <= max1)
            return true;

        if (min1 <= max2 && max2 <= max1)
            return true;

        return false;
    }
}
