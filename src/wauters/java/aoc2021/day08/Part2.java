package wauters.java.aoc2021.day08;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class Part2 {

    private static final Map<Integer, Set<Integer>> digitMap = Map.of(
            2, Set.of(1),
            3, Set.of(7),
            4, Set.of(4),
            5, Set.of(2, 3, 5),
            6, Set.of(0, 6, 9),
            7, Set.of(8)
    );

    private static final Set<char[]> fivePartSegments = new HashSet<>();
    private static final Set<char[]> sixPartSegments = new HashSet<>();

    private static final Map<Integer, char[]> segmentMap = new HashMap<>();
    private static int sum = 0;

    public static void main(String[] args) {
        try (Stream<String> stream = Files.lines(Paths.get("input/aoc2021/day08.txt"))) {
            solve(stream.toArray(String[]::new));
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(String[] lines) {
        for (String line : lines) {
            String[] digitIO = line.split(" \\| ");
            String[] digitInput = digitIO[0].split(" ");
            String[] digitOutput = digitIO[1].split(" ");

            //Deduce all digits
            for (String s : digitInput)
                checkMatchingDigits(s.toCharArray());
            char topRightSegment = findTopRightSegment(segmentMap.get(1));
            char bottomRightSegment = findBottomRightSegment(segmentMap.get(1), topRightSegment);
            findZeroAndNine();
            findTwoThreeFive(topRightSegment, bottomRightSegment);

            //Calculate number and add to sum
            int number = 0;
            for (int i = 0; i < digitOutput.length; i++) {
                int multiplier = (int) Math.pow(10, i);
                int index = digitOutput.length - i - 1;
                number += getDigitValue(digitOutput[index].toCharArray()) * multiplier;
            }
            sum += number;

            //Clear data
            segmentMap.clear();
            fivePartSegments.clear();
            sixPartSegments.clear();
        }
        System.out.println(sum);
    }

    private static void checkMatchingDigits(char[] segments) {
        Set<Integer> potentialDigits = digitMap.get(segments.length);
        if (potentialDigits.size() == 1) {
            segmentMap.putIfAbsent((Integer) potentialDigits.toArray()[0], segments);
        } else if (segments.length == 5) {
            fivePartSegments.add(segments);
        } else {
            sixPartSegments.add(segments);
        }
    }

    /**
     * Only 6 doesn't contain all segments of 1
     * 6 only contains the bottom segment of 1
     */

    private static char findTopRightSegment(char[] one) {
        for (char segment : one) {
            for (char[] segments : sixPartSegments) {
                if (hasNot(segments, segment)) {
                    segmentMap.putIfAbsent(6, segments);
                    return segment;
                }
            }
        }
        //Should not happen
        return one[0];
    }

    /**
     * 1 only has two segments so by knowing the top segment we automatically know the bottom one
     */
    private static char findBottomRightSegment(char[] one, char topRightSegment) {
        for (char o : one) {
            if (o != topRightSegment)
                return o;
        }
        //Should not happen
        return one[0];
    }

    /**
     * Possible digits of the six-part segments: 0, 6, 9
     * 9 is the only one of the six-part segments that contains all segments of 4
     * Since we've already found six the remaining one is 0
     */
    private static void findZeroAndNine() {
        for (char[] segments : sixPartSegments) {
            Set<Character> segmentSet = new HashSet<>();
            for (char c : segments) segmentSet.add(c);

            Set<Character> fourSegmentSet = new HashSet<>();
            for (char c : segmentMap.get(4)) fourSegmentSet.add(c);

            if (segmentSet.containsAll(fourSegmentSet)) {
                segmentMap.putIfAbsent(9, segments);
                continue;
            }

            Set<Character> sixSegmentSet = new HashSet<>();
            for (char c : segmentMap.get(6)) sixSegmentSet.add(c);

            if (!segmentSet.containsAll(sixSegmentSet)) {
                segmentMap.putIfAbsent(0, segments);
            }
        }
    }

    /**
     * Possible digits of the five-part segments: 2, 3, 5
     * 5 is the only digit of the five-part segments that doesn't contain the top right segment
     * 2 is the only digit of the five-part segments that doesn't contain the bottom right segment
     * 3 otherwise
     */
    private static void findTwoThreeFive(char top, char bottom) {
        for (char[] segments : fivePartSegments) {
            if (hasNot(segments, top)) {
                segmentMap.putIfAbsent(5, segments);
            } else if (hasNot(segments, bottom)) {
                segmentMap.putIfAbsent(2, segments);
            } else {
                segmentMap.putIfAbsent(3, segments);
            }
        }
    }

    private static boolean hasNot(char[] segments, char segmentToFind) {
        for (char segment : segments) {
            if (segmentToFind == segment)
                return false;
        }
        return true;
    }

    private static int getDigitValue(char[] digit) {
        for (var entry : segmentMap.entrySet()) {
            Set<Character> segmentSet = new HashSet<>();
            for (char c : entry.getValue()) segmentSet.add(c);

            Set<Character> digitSegmentSet = new HashSet<>();
            for (char c : digit) digitSegmentSet.add(c);
            if (segmentSet.containsAll(digitSegmentSet) && digitSegmentSet.containsAll(segmentSet)) {
                return entry.getKey();
            }
        }
        //Should not happen
        return -1;
    }
}
