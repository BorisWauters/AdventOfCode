package com.github.aoc2023.day04;

import com.github.Solution;

import java.util.Arrays;

public class Part1 extends Solution {
    @Override
    protected void solve(String[] lines) {
        int sum = 0;
        for(String line : lines) {
            line = line.replaceAll("Card\\s+\\d+:", "").trim();
            line = line.replaceAll("\\s+[|]\\s+", "_");
            line = line.replaceAll("\\s+", "-");


            String[] parts = line.split("_");
            int[] card = Arrays.stream(parts[0].split("-")).mapToInt(Integer::parseInt).toArray();
            int[] winningNumbers = Arrays.stream(parts[1].split("-")).mapToInt(Integer::parseInt).toArray();
            sum += getPoints(card, winningNumbers);
        }
        System.out.println(sum);
    }

    private int getPoints(int[] card, int[] winningNumbers) {
        int power = -1;
        for (int number : card) {
            if (contains(winningNumbers, number)) {
                power++;
            }
        }
        if (power < 0) {
            return 0;
        }

        return (int) Math.pow(2, power);
    }

    private boolean contains(int[] array, int number) {
        for (int entry : array) {
            if (entry == number) {
                return true;
            }
        }
        return false;
    }
}
