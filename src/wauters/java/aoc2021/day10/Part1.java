package com.github.aoc2021.day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public class Part1 {

    private static final Map<Character, Integer> illegalCharacterMap = Map.of(
            ')', 3,
            ']', 57,
            '}', 1197,
            '>', 25137
    );

    private static final Map<Character, Character> characterMap = Map.of(
            '(', ')',
            '[', ']',
            '{', '}',
            '<', '>'
    );
    private static int score = 0;

    public static void main(String[] args) {
        try (Stream<String> stream = Files.lines(Paths.get("input/aoc2021/day10.txt"))) {
            solve(stream.toArray(String[]::new));
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(String[] lines) {
        for (String line : lines) {
            Deque<Character> stack = new ArrayDeque<>();

            for (Character character : line.toCharArray()) {
                if (isOpenCharacter(character)) {
                    stack.push(character);
                    continue;
                }

                Character openCharacter = stack.pop();
                if (!Objects.equals(characterMap.get(openCharacter), character)) {
                    //Illegal character
                    score += illegalCharacterMap.get(character);
                    break;
                }
            }
        }
        System.out.println(score);
    }

    private static boolean isOpenCharacter(Character character) {
        return characterMap.containsKey(character);
    }
}
