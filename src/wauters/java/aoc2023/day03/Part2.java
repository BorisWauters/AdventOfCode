package wauters.java.aoc2023.day03;

import wauters.java.Solution;

import java.util.ArrayList;
import java.util.List;

public class Part2 extends Solution {

    @Override
    protected void solve(String[] lines) {
        int sum = 0;
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];

            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);

                if (c == '*') {
                    sum += getGearRatio(lines, i, j);
                }
            }
        }
        System.out.println(sum);
    }

    private boolean isNumber(char c) {
        return c >= '0' && c <= '9';
    }

    private int getGearRatio(String[] lines, int lineNumber, int gearIndex) {
        ArrayList<Integer> numbers = new ArrayList<>(2);
        String line = lines[lineNumber];
        if (gearIndex > 0 && isNumber(line.charAt(gearIndex - 1))) {
            numbers.add(fetchNumberReverse(gearIndex - 1, line));
        }
        if (gearIndex < line.length() - 1 && isNumber(line.charAt(gearIndex + 1))) {
            numbers.add(fetchNumber(gearIndex + 1, line));
        }

        if (lineNumber > 0) {
            numbers.addAll(fetchNumbersFromLine(gearIndex, lines[lineNumber - 1]));
        }
        if (lineNumber < lines.length - 1) {
            numbers.addAll(fetchNumbersFromLine(gearIndex, lines[lineNumber + 1]));
        }

        if (numbers.size() < 2) {
            return 0;
        }

        int gearRatio = 1;
        for (int gear : numbers) {
            gearRatio *= gear;
        }

        return gearRatio;
    }

    private List<Integer> fetchNumbersFromLine(int index, String line) {
        List<Integer> numbers = new ArrayList<>(2);

        if (isNumber(line.charAt(index - 1)) && isNumber(line.charAt(index + 1))) {
            if (isNumber(line.charAt(index))) {
                numbers.add(fetchNumberTwoWays(index, line));
            } else {
                numbers.add(fetchNumberReverse(index - 1, line));
                numbers.add(fetchNumber(index + 1, line));
            }
        } else if (isNumber(line.charAt(index - 1))) {
            if (isNumber(line.charAt(index))) {
                numbers.add(fetchNumberReverse(index, line));
            } else {
                numbers.add(fetchNumberReverse(index - 1, line));
            }
        } else if (isNumber(line.charAt(index + 1))) {
            if (isNumber(line.charAt(index))) {
                numbers.add(fetchNumber(index, line));
            } else {
                numbers.add(fetchNumber(index + 1, line));
            }
        } else if (isNumber(line.charAt(index))) {
            numbers.add(Integer.parseInt(String.valueOf(line.charAt(index))));
        }

        return numbers;
    }

    private int fetchNumber(int index, String line) {
        StringBuilder number = new StringBuilder();
        char c = line.charAt(index);
        while (isNumber(c)) {
            number.append(c);
            index++;

            if (index >= line.length()) {
                break;
            }
            c = line.charAt(index);
        }

        return Integer.parseInt(number.toString());
    }

    private int fetchNumberReverse(int index, String line) {
        StringBuilder number = new StringBuilder();
        char c = line.charAt(index);
        while (isNumber(c)) {
            number.append(c);
            index--;

            if (index < 0) {
                break;
            }
            c = line.charAt(index);
        }

        return Integer.parseInt(number.reverse().toString());
    }

    private int fetchNumberTwoWays(int index, String line) {
        String number = "";
        int first = fetchNumberReverse(index, line);
        int second = fetchNumber(index + 1, line);
        return Integer.parseInt(number + first + second);
    }
}
