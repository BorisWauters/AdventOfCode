package com.github.aoc2023.day07;

import com.github.Solution;

import java.util.ArrayList;
import java.util.List;

public class Main07 extends Solution {
    @Override
    protected void solve(String[] lines) {
        List<Hand> p1Hands = new ArrayList<>();
        List<Hand2> p2Hands = new ArrayList<>();
        for (String line : lines) {
            String hand = line.substring(0, 5);
            int bet = Integer.parseInt(line.substring(6));

            p1Hands.add(new Hand(hand, bet));
            p2Hands.add(new Hand2(hand, bet));
        }

        p1Hands.sort(Hand::compareTo);
        p2Hands.sort(Hand2::compareTo);

        int p1Sum = 0;
        int p2Sum = 0;
        for (int i = 0; i < p1Hands.size(); i++) {
            p1Sum += p1Hands.get(i).getBet() * (i + 1);
            p2Sum += p2Hands.get(i).getBet() * (i + 1);
        }
        System.out.println("Part 1: " + p1Sum);
        System.out.println("Part 2: " + p2Sum);

        // too low: 251296863
    }
}
