package com.github.day03;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Part2 {

    public static void main(String[] args) {
        try (Stream<String> stream = Files.lines(Paths.get("input/day03.txt"))) {
            solve(stream.toArray(String[]::new));
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(String[] lines) {

        String[] most = lines.clone();
        String[] least = lines.clone();
        for (int i = 0; i < lines[0].length(); i++) {
            if (most.length > 1) {
                int mostCommonBit = findMostCommonBitAt(most, i);
                most = removeEntriesWithoutCommonBitAt(most, i, mostCommonBit);
            }
            if (least.length > 1) {
                int mostCommonBit = findMostCommonBitAt(least, i);
                least = removeEntriesWithoutCommonBitAt(least, i, 1 - mostCommonBit);
            }
        }
        long oxygenValue = parseDecimal(most[0].toCharArray());
        long co2Rating = parseDecimal(least[0].toCharArray());
        System.out.println(oxygenValue * co2Rating);
    }

    private static int findMostCommonBitAt(String[] lines, int i) {
        double total = 0;
        for (String line : lines) {
            total += Double.parseDouble(String.valueOf(line.charAt(i)));
        }
        double divider = ((double) lines.length) / 2;
        return total - divider < 0 ? 0 : 1;
    }

    private static String[] removeEntriesWithoutCommonBitAt(String[] lines, int i, int commonBit) {
        List<String> filteredList = new ArrayList<>();
        for (String line : lines) {
            if (Integer.parseInt(String.valueOf(line.charAt(i))) == commonBit) {
                filteredList.add(line);
            }
        }
        return filteredList.toArray(new String[0]);
    }

    private static long parseDecimal(char[] binary) {
        long decimal = 0;
        for (int i = 0; i < binary.length; i++) {
            int value = Integer.parseInt(String.valueOf(binary[i]));
            if (value > 0)
                decimal += Math.round(Math.pow(2, binary.length - 1 - i));
        }
        return decimal;
    }
}
