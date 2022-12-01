package com.github.aoc2021.day21;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Part1 {

    private static int dieValue = 0;
    private static int numberOfDieRolls = 0;

    private static final int[] playerPosition = new int[2];

    private static final int[] playerScore = {0, 0};

    public static void main(String[] args) {
        try (Stream<String> stream = Files.lines(Paths.get("input/day21.txt"))) {
            solve(stream.toArray(String[]::new));
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(String[] lines) {
        for (int i = 0; i < lines.length; i++) {
            playerPosition[i] = Integer.parseInt(lines[i].replaceAll("\\D+\\d\\D+", "")) - 1;
        }

        int currentPlayer = 0;
        while (playerScore[0] < 1000 && playerScore[1] < 1000) {
            int dieValue = rollThreeDice();
            int newPosition = adjustPosition(playerPosition[currentPlayer], dieValue);

            playerPosition[currentPlayer] = newPosition;
            playerScore[currentPlayer] += newPosition + 1;

            currentPlayer++;
            currentPlayer %= 2;
        }

        for (int i = 0; i < 2; i++) {
            if (playerScore[i] >= 1000)
                continue;

            System.out.println(playerScore[i]*numberOfDieRolls);
        }
    }

    private static int adjustPosition(int currentPosition, int dieValue) {
        return (currentPosition + dieValue) % 10;
    }

    private static int rollThreeDice() {
        return rollDie() + rollDie() + rollDie();
    }

    private static int rollDie() {
        dieValue = dieValue % 100 + 1;
        numberOfDieRolls++;
        return dieValue;
    }
}
