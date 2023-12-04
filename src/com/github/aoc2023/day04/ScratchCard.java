package com.github.aoc2023.day04;

public class ScratchCard {
    private final int id;
    private final int[] card;
    private final int[] winningNumbers;

    private int amount;

    public ScratchCard(int id, int[] card, int[] winningNumbers) {
        this.id = id;
        this.card = card;
        this.winningNumbers = winningNumbers;

        this.amount = 1;
    }

    public int getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public void copy(int x) {
        amount += x;
    }

    public int getPoints() {
        int points = 0;
        for (int number : card) {
            if (contains(winningNumbers, number)) {
                points++;
            }
        }
        return points;
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
