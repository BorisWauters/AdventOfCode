package com.github.aoc2021.day17;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Part1 {

    public static void main(String[] args) {
        try (Stream<String> stream = Files.lines(Paths.get("input/aoc2021/day17.txt"))) {
            solve(stream.toArray(String[]::new)[0]);
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(String line) {
        String[] content = line.substring(13).split(", ");

        String[] xContent = content[0].substring(2).split("\\.\\.");
        String[] yContent = content[1].substring(2).split("\\.\\.");

        int xMin = Integer.parseInt(xContent[0]);
        int xMax = Integer.parseInt(xContent[1]);
        int yMin = Integer.parseInt(yContent[0]);
        int yMax = Integer.parseInt(yContent[1]);

        // assuming y is always negative
        for (int y = yMin; y <= yMax; y++) {
            int dy = Math.abs(y) - 1;
            int numberOfSteps = 2 * (dy + 1);

            // assuming x is always positive
            int threshhold = 0;
            for (int i = 1; i < numberOfSteps; i++) {
                threshhold += i;
            }

            for (int x = xMax; x >= xMin; x--) {

                if (x > threshhold) {
                    int top;
                    // sum of all previous n numbers must be == to x
                    if (numberOfSteps % 2 == 0) {
                        int v = (int) Math.floor(x / numberOfSteps);
                        top = v + (numberOfSteps / 2);
                    } else {
                        // div by nos and get rest
                        int v = x / numberOfSteps;
                        top = v + ((int) Math.floor(numberOfSteps / 2));
                    }
                    int sum = 0;
                    for (int i = 0; i < numberOfSteps; i++) {
                        sum += top;
                        sum -= i;
                    }

                    if (sum == x) {
                        System.out.println(calculateYMax(dy, numberOfSteps));
                        return;
                    }
                } else {
                    // sum of all previous numbers must be == to x
                    int sum = 0;
                    for (int i = 1; i < numberOfSteps; i++) {
                        sum += i;

                        if (sum == x) {
                            System.out.println(calculateYMax(dy, numberOfSteps));
                            return;
                        } else if (sum > x) {
                            break;
                        }
                    }
                }
            }
        }
    }

    private static int calculateYMax(int dy, int numberOfSteps) {
        return (dy / 2) * (dy + 1);
    }
}
