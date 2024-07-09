package wauters.java.aoc2023.day03;

import wauters.java.Solution;

public class Part1 extends Solution {
    @Override
    protected void solve(String[] lines) {
        int sum = 0;
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];

            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);

                int start = j;
                StringBuilder number = new StringBuilder();
                while (isNumber(c)) {
                    number.append(c);
                    j++;

                    if (j >= line.length()) {
                        break;
                    }
                    c = line.charAt(j);
                }
                int end = j;

                if (!number.isEmpty() && isAttachedToMachinePart(lines, i, start, end)) {
                    sum += Integer.parseInt(number.toString());
                }
            }
        }
        System.out.println(sum);
    }

    private boolean isNumber(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isAttachedToMachinePart(String[] lines, int lineNumber, int start, int end) {
        String line = lines[lineNumber];
        if (start > 0 && line.charAt(start - 1) != '.') {
            return true;
        }
        if (end < line.length() - 1 && line.charAt(end) != '.') {
            return true;
        }

        boolean hasPart = false;

        if (lineNumber > 0) {
            hasPart = containsMachinePart(start, end, lines[lineNumber - 1]);
        }
        if (lineNumber < lines.length - 1) {
            hasPart = hasPart || containsMachinePart(start, end, lines[lineNumber + 1]);
        }

        return hasPart;
    }

    private static boolean containsMachinePart(int start, int end, String line) {
        for (int i = start - 1; i <= end; i++) {
            if (i < 0 || i >= line.length()) {
                continue;
            }

            if (line.charAt(i) != '.') {
                return true;
            }
        }
        return false;
    }
}
