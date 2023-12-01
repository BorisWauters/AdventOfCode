package com.github.aoc2023.day01;

import com.github.Solution;

public class Part1 extends Solution {

    @Override
    public void solve(String[] lines) {
        int sum = 0;
        for (String line : lines) {
            line = line.replaceAll("\\D", "");
            sum += Integer.parseInt(line.substring(0, 1) + line.charAt(line.length() - 1));
        }
        System.out.println(sum);
    }
}
