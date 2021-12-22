package com.github.day22;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * This approach is not very nice, but good enough for part 1.
 * Part 2 will require a different approach because of the larger amount of cubes.
 */
public class Part1 {

    public static void main(String[] args) {
        try (Stream<String> stream = Files.lines(Paths.get("input/day22.txt"))) {
            solve(stream.toArray(String[]::new));
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(String[] lines) {
        boolean[][][] reactor = new boolean[101][101][101];

        for (String line : lines) {
            boolean on = "on".equals(line.split(" ")[0]);
            String[] ranges = line.split(" ")[1].split(",");

            int[] xRange = Arrays.stream(ranges[0].substring(2).split("[.][.]"))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            int[] yRange = Arrays.stream(ranges[1].substring(2).split("[.][.]"))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            int[] zRange = Arrays.stream(ranges[2].substring(2).split("[.][.]"))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            if (xRange[0] < -50 || xRange[1] > 50 || yRange[0] < -50 || yRange[1] > 50 || zRange[0] < -50 || zRange[1] > 50) {
                continue;
            }

            for (int i = xRange[0]; i <= xRange[1]; i++) {
                for (int j = yRange[0]; j <= yRange[1]; j++) {
                    for (int k = zRange[0]; k <= zRange[1]; k++) {
                        reactor[i + 50][j + 50][k + 50] = on;
                    }
                }
            }
        }

        calculateSum(reactor);
    }

    private static void calculateSum(boolean[][][] reactor) {
        int sum = 0;
        for (int i = 0; i < reactor.length; i++) {
            for (int j = 0; j < reactor.length; j++) {
                for (int k = 0; k < reactor.length; k++) {
                    if (reactor[i][j][k])
                        sum++;
                }
            }
        }
        System.out.println(sum);
    }
}
