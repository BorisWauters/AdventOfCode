package com.github.aoc2022.day02;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Part1 {

    public static void main(String[] args) {
        try (Stream<String> stream = Files.lines(Paths.get("input/aoc2022/day02.txt"))) {
            solve(stream.toArray(String[]::new));
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(String[] lines) {
        int score = 0;
        for (String line : lines ) {
            score += computeScore(line.charAt(0), line.charAt(2));
        }

        System.out.println(score);
    }

    private static int computeScore(char other, char own) {
        Sign ownSign = Sign.fromChar(own);
        Sign otherSign = Sign.fromChar(other);
        return getGamescore(ownSign.beats(otherSign)) + ownSign.getPoints();
    }

    private static int getGamescore(int result) {
        if (result < 0)
            return 0;
        if (result == 0)
            return 3;
        return 6;
    }

    enum Sign {
        ROCK('A', 'X'),
        PAPER('B', 'Y'),
        SCISSORS('C', 'Z');
    
        private final int other;
        private final int own;
    
        Sign(char other, char own) {
            this.other = other;
            this.own = own;
        }
    
        public int beats(Sign other) {
            int result = this.ordinal() - other.ordinal();
            if (result == 0)
                return 0;
            if (Math.abs(result) == 1)
                return result;
            return -result;
        }
    
        public int getPoints() {
            return this.ordinal() + 1;
        }
    
        public static Sign fromChar(char c) {
            for (Sign sign : Sign.values()) {
                if (sign.other == c || sign.own == c)
                    return sign;
            }
            throw new RuntimeException("Illegal char");
        }
    }
}
