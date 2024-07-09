package com.github.aoc2022.day09;

public class Move {
    final String direction;
    final int steps;

    public Move(String[] move) {
        this.direction = move[0];
        this.steps = Integer.parseInt(move[1]);
    }
}
