package com.github.aoc2023.day04;

import com.github.Solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Part2 extends Solution {
    @Override
    protected void solve(String[] lines) {
        List<ScratchCard> scratchCards = new ArrayList<>();
        // Parse input
        for(String line : lines) {
            String[] parts = line.split(":");
            int id = Integer.parseInt(parts[0].replace("Card", "").trim());

            line = parts[1].trim();
            line = line.replaceAll("\\s+[|]\\s+", "_");
            line = line.replaceAll("\\s+", "-");

            parts = line.split("_");
            int[] card = Arrays.stream(parts[0].split("-")).mapToInt(Integer::parseInt).toArray();
            int[] winningNumbers = Arrays.stream(parts[1].split("-")).mapToInt(Integer::parseInt).toArray();

            scratchCards.add(new ScratchCard(id, card, winningNumbers));
        }

        // Calculate points
        int sum = 0;
        for (ScratchCard scratchCard : scratchCards) {
            sum += scratchCard.getAmount();
            int points = scratchCard.getPoints();
            for (int j = scratchCard.getId(); j < scratchCard.getId() + points; j++) {
                scratchCards.get(j).copy(scratchCard.getAmount());
            }
        }
        System.out.println(sum);
    }
}
