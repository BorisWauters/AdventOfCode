package com.github.aoc2023.day01;

import com.github.Solution;

import java.util.ArrayList;
import java.util.List;

public class Part2 extends Solution {

    @Override
    public void solve(String[] lines) {
        int sum = 0;
        for (String line : lines) {
            sum += parseCode(line);
        }
        System.out.println(sum);
    }

    private int parseCode(String line) {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < line.length(); i++) {
            try {
                int digit = getDigit(line, i);
                if (digit > 0) {
                    numbers.add(digit);
                }
            } catch (StringIndexOutOfBoundsException e) {
                // ignore
            }
        }
        int first = numbers.get(0);
        int last = numbers.get(numbers.size() - 1);
        return (10 * first) + last;
    }

    private int getDigit(String line, int i) {
        return switch (line.charAt(i)) {
            case '1' -> 1;
            case '2' -> 2;
            case '3' -> 3;
            case '4' -> 4;
            case '5' -> 5;
            case '6' -> 6;
            case '7' -> 7;
            case '8' -> 8;
            case '9' -> 9;
            case 'o' -> {
                if (line.charAt(i + 1) == 'n' && line.charAt(i + 2) == 'e') {
                    yield 1;
                }
                yield -1;
            }
            case 't' -> {
                if (line.charAt(i + 1) == 'w' && line.charAt(i + 2) == 'o') {
                    yield 2;
                }
                if (line.charAt(i + 1) == 'h' && line.charAt(i + 2) == 'r' && line.charAt(i + 3) == 'e' && line.charAt(i + 4) == 'e') {
                    yield 3;
                }
                yield -1;
            }
            case 'f' -> {
                if (line.charAt(i + 1) == 'o' && line.charAt(i + 2) == 'u' && line.charAt(i + 3) == 'r') {
                    yield 4;
                }
                if (line.charAt(i + 1) == 'i' && line.charAt(i + 2) == 'v' && line.charAt(i + 3) == 'e') {
                    yield 5;
                }
                yield -1;
            }
            case 's' -> {
                if (line.charAt(i + 1) == 'i' && line.charAt(i + 2) == 'x') {
                    yield 6;
                }
                if (line.charAt(i + 1) == 'e' && line.charAt(i + 2) == 'v' && line.charAt(i + 3) == 'e' && line.charAt(i + 4) == 'n') {
                    yield 7;
                }
                yield -1;
            }
            case 'e' -> {
                if (line.charAt(i + 1) == 'i' && line.charAt(i + 2) == 'g' && line.charAt(i + 3) == 'h' && line.charAt(i + 4) == 't') {
                    yield 8;
                }
                yield -1;
            }
            case 'n' -> {
                if (line.charAt(i + 1) == 'i' && line.charAt(i + 2) == 'n' && line.charAt(i + 3) == 'e') {
                    yield 9;
                }
                yield -1;
            }
            default -> -1;
        };
    }
}
