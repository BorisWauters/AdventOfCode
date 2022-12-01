package com.github.aoc2021.day16;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class Part2 {

    private static long lastValue = 0;

    private static final Map<Character, String> hexToBin = Map.ofEntries(
            Map.entry('0', "0000"),
            Map.entry('1', "0001"),
            Map.entry('2', "0010"),
            Map.entry('3', "0011"),
            Map.entry('4', "0100"),
            Map.entry('5', "0101"),
            Map.entry('6', "0110"),
            Map.entry('7', "0111"),
            Map.entry('8', "1000"),
            Map.entry('9', "1001"),
            Map.entry('A', "1010"),
            Map.entry('B', "1011"),
            Map.entry('C', "1100"),
            Map.entry('D', "1101"),
            Map.entry('E', "1110"),
            Map.entry('F', "1111")
    );

    public static void main(String[] args) {
        try (Stream<String> stream = Files.lines(Paths.get("input/day16.txt"))) {
            solve(stream.findFirst().orElseThrow());
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(String input) {
        String binary = hexadecimalToBinary(input);
        handlePacket(binary);
        System.out.println(lastValue);
    }

    private static int handlePacket(String binary) {
        if (!binary.contains("1")) return 0;

        int version = binaryToInt(binary.substring(0, 3));
        int id = binaryToInt(binary.substring(3, 6));
        String packet = binary.substring(6);

        if (id == 4)
            return 6 + handlePacketWithId4(packet);
        return 6 + handleOperator(packet, id);
    }

    private static int handleOperator(String packet, int id) {
        if (packet.charAt(0) == '0')
            return 1 + handleOperator0(packet.substring(1), id);
        return 1 + handleOperator1(packet.substring(1), id);
    }

    private static int handleOperator0(String packet, int id) {
        int totalLengthOfSubPackets = binaryToInt(packet.substring(0, 15));
        packet = packet.substring(15, 15 + totalLengthOfSubPackets);

        List<Long> subValues = new ArrayList<>();

        int startIndex = 0;
        while (true) {
            packet = packet.substring(startIndex);
            startIndex = handlePacket(packet);

            if (startIndex == 0)
                break;

            subValues.add(lastValue);
        }

        executeOperator(subValues, id);
        return 15 + totalLengthOfSubPackets;
    }

    private static int handleOperator1(String packet, int id) {
        int numberOfSubPackets = binaryToInt(packet.substring(0, 11));
        packet = packet.substring(11);

        List<Long> subValues = new ArrayList<>(numberOfSubPackets);

        int startIndex = 0;
        for (int i = 0; i < numberOfSubPackets; i++) {
            String subPacket = packet.substring(startIndex);
            startIndex += handlePacket(subPacket);
            subValues.add(lastValue);
        }

        executeOperator(subValues, id);
        return 11 + startIndex;
    }

    private static int handlePacketWithId4(String packet) {
        boolean end = false;
        int i = 0;
        String packetContent = "";
        while (!end) {
            end = packet.charAt(i) == '0';
            packetContent = packetContent.concat(packet.substring(i + 1, i + 5));
            i += 5;
        }

        lastValue = binaryToLong(packetContent);
        return packet.substring(i).contains("1") ? i : packet.length();
    }

    private static void executeOperator(List<Long> values, int id) {
        switch (id) {
            case 0:
                lastValue = sum(values);
                return;
            case 1:
                lastValue = product(values);
                return;
            case 2:
                lastValue = Collections.min(values);
                return;
            case 3:
                lastValue = Collections.max(values);
                return;
            case 5:
                lastValue = greaterThan(values);
                return;
            case 6:
                lastValue = lessThan(values);
                return;
            case 7:
                lastValue = equalTo(values);
                return;
            default:
        }
    }

    private static long sum(List<Long> values) {
        return values.stream().mapToLong(Long::longValue).sum();
    }

    private static long product(List<Long> values) {
        long product = values.get(0);
        for (int i = 1; i < values.size(); i++) {
            product *= values.get(i);
        }
        return product;
    }

    private static int greaterThan(List<Long> values) {
        return values.get(0) > values.get(1) ? 1 : 0;
    }

    private static int lessThan(List<Long> values) {
        return values.get(0) < values.get(1) ? 1 : 0;
    }

    private static int equalTo(List<Long> values) {
        return Objects.equals(values.get(0), values.get(1)) ? 1 : 0;
    }

    private static String hexadecimalToBinary(String hex) {
        StringBuilder bin = new StringBuilder();
        for (int i = 0; i < hex.length(); i++) {
            bin.append(hexToBin.get(hex.charAt(i)));
        }
        return bin.toString();
    }

    private static int binaryToInt(String bin) {
        return Integer.parseInt(bin, 2);
    }

    private static long binaryToLong(String bin) {
        return Long.parseLong(bin, 2);
    }
}
