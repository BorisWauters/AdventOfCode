package wauters.java.aoc2021.day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class Part2 {

    private static final Map<Character, Integer> missingCharacterMap = Map.of(
            ')', 1,
            ']', 2,
            '}', 3,
            '>', 4
    );

    private static final Map<Character, Character> characterMap = Map.of(
            '(', ')',
            '[', ']',
            '{', '}',
            '<', '>'
    );

    public static void main(String[] args) {
        try (Stream<String> stream = Files.lines(Paths.get("input/aoc2021/day10.txt"))) {
            solve(stream.toArray(String[]::new));
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(String[] lines) {
        List<Long> scores = new ArrayList<>();
        for (String line : lines) {
            Deque<Character> stack = new ArrayDeque<>();

            if (isIllegalLine(line, stack))
                continue;

            long lineScore = 0;
            int stackSize = stack.size();
            for (int i = 0; i < stackSize; i++) {
                lineScore *= 5;

                Character openCharacter = stack.pop();
                Character closingCharacter = characterMap.get(openCharacter);
                lineScore += missingCharacterMap.get(closingCharacter);
            }
            scores.add(lineScore);
        }
        scores.sort(Comparator.naturalOrder());
        int middleIndex = (scores.size()) / 2;
        System.out.println(scores.get(middleIndex));
    }

    private static boolean isIllegalLine(String line, Deque<Character> stack) {
        for (Character character : line.toCharArray()) {
            if (isOpenCharacter(character)) {
                stack.push(character);
                continue;
            }

            Character openCharacter = stack.pop();
            if (!Objects.equals(characterMap.get(openCharacter), character)) {
                //Illegal character
                return true;
            }
        }
        return false;
    }

    private static boolean isOpenCharacter(Character character) {
        return characterMap.containsKey(character);
    }
}
