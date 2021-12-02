package com.github.day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Part2 {

    private static int depth = 0;
    private static int position = 0;
    private static int aim = 0;

    private static final String FORWARD = "forward";
    private static final String DOWN = "down";
    private static final String UP = "up";

    public static void main(String[] args) {
        // write your code here
        try (Stream<String> stream = Files.lines(Paths.get("input/day2.txt"))) {
            solve(stream.toArray(String[]::new));
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(String[] lines) throws IOException {
        for (String line : lines) {
            String[] values = line.split(" ");

            String command = values[0];
            int commandValue = Integer.parseInt(values[1]);

            switch (command) {
                case FORWARD:
                    forward(commandValue);
                    break;
                case DOWN:
                    down(commandValue);
                    break;
                case UP:
                    up(commandValue);
                    break;
                default:
                    break;
            }
        }
        System.out.println(depth * position);
    }

    private static void forward(int value) {
        position += value;
        depth += value * aim;
    }

    private static void down(int value) {
        aim += value;
    }

    private static void up(int value) {
        aim -= value;
    }
}
