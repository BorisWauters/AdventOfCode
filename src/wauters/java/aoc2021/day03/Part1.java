package wauters.java.aoc2021.day03;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Part1 {

    public static void main(String[] args) {
        try (Stream<String> stream = Files.lines(Paths.get("input/aoc2021/day03.txt"))) {
            solve(stream.toArray(String[]::new));
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(String[] lines) {
        int[] total = new int[lines[0].length()];

        for (String line : lines) {
            for (int i = 0; i < total.length; i++) {
                int bitValue = Integer.parseInt(String.valueOf(line.charAt(i)));
                total[i] += bitValue;
            }
        }

        int[] gammaRate = new int[total.length];
        int[] epsilonRate = new int[total.length];

        for (int i = 0; i < total.length; i++) {
            total[i] -= lines.length / 2;

            if (total[i] > 0)
                gammaRate[i] = 1;
            else if (total[i] < 0)
                gammaRate[i] = 0;
            else
                System.err.println("0 value should not happen");

            epsilonRate[i] = 1 - gammaRate[i];
        }

        System.out.println(parseDecimal(gammaRate) * parseDecimal(epsilonRate));
    }

    private static long parseDecimal(int[] binary) {
        long decimal = 0;
        for (int i = 0; i < binary.length; i++) {
            if (binary[i] > 0)
                decimal += Math.round(Math.pow(2, binary.length - 1 - i));
        }
        return decimal;
    }
}
