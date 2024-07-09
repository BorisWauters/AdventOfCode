package wauters.java.aoc2021.day06;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class Main06 {

    public static void main(String[] args) {
        try (Stream<String> stream = Files.lines(Paths.get("input/aoc2021/day06.txt"))) {
            int[] input = stream.flatMap(s -> Arrays.stream(s.split(","))).mapToInt(Integer::parseInt).toArray();
            System.out.print("Part 1: ");
            solve(input, 80);
            System.out.print("Part 2: ");
            solve(input, 256);
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(int[] lines, int days) {
        long[] amountOfLanternFish = new long[9];
        for (int line : lines) {
            amountOfLanternFish[line]++;
        }

        for (int i = 0; i < days; i++) {
            long[] newAmountOfLanternFish = new long[9];

            for (int j = 0; j < 8; j++) {
                newAmountOfLanternFish[j] = amountOfLanternFish[j+1];
            }
            newAmountOfLanternFish[6] += amountOfLanternFish[0];
            newAmountOfLanternFish[8] = amountOfLanternFish[0];

            amountOfLanternFish = newAmountOfLanternFish;
        }

        System.out.println(Arrays.stream(amountOfLanternFish).sum());
    }
}
