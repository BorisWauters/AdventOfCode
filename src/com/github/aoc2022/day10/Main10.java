package com.github.aoc2022.day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Main10 {

    public static void main(String[] args) {
        try (Stream<String> stream = Files.lines(Paths.get("input/aoc2022/day10.txt"))) {
            solve(stream.toArray(String[]::new));
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(String[] lines) {
        Cpu cpu = new Cpu();
        for (String line : lines) {
            cpu.executeCommand(line);
        }

        System.out.println("Part 1: " + cpu.getXSum());
        System.out.println("Part 2:");
        cpu.printCrt();
    }
}

class Cpu {
    private int x;
    private int cycle;

    private int xSum = 0;

    private boolean[][] crt;
    private int crtLine;

    public Cpu() {
        x = 1;
        cycle = 0;

        crt = new boolean[6][40];
    }

    public void executeCommand(String command) {
        if ("noop".equals(command)) {
            cycle();
            return;
        }

        // addx
        int diff = Integer.parseInt(command.split(" ")[1]);
        cycle();
        cycle();
        x += diff;
    }

    private void cycle() {
        int currentPosition = cycle % 40;

        if (currentPosition == x || Math.abs(x - currentPosition) == 1) {
            crt[crtLine][currentPosition] = true;
        }

        cycle++;

        if (cycle % 40 == 0) {
            crtLine++;
        }

        if ((cycle - 20) % 40 == 0) {
            xSum += (x * cycle);
        }
    }

    public int getXSum() {
        return xSum;
    }

    public void printCrt() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 40; j++) {
                System.out.print(crt[i][j] ? "#" : " ");
            }
            System.out.println(" ");
        }
    }
}
