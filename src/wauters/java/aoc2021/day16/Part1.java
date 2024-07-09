package com.github.aoc2021.day16;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Stream;

public class Part1 {

    private static int versionSum = 0;

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
        try (Stream<String> stream = Files.lines(Paths.get("input/aoc2021/day16.txt"))) {
            solve(stream.findFirst().orElseThrow());
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(String input) {
        String binary = hexadecimalToBinary(input);
        handlePacket(binary);
        System.out.println(versionSum);
    }

    private static int handlePacket(String binary) {
        if (!binary.contains("1")) return 0;

        int version = binaryToInt(binary.substring(0, 3));
        versionSum += version;
        long id = binaryToInt(binary.substring(3, 6));
        String packet = binary.substring(6);

        if (id == 4)
            return 6 + handlePacketWithId4(packet);
        return 6 + handleOperator(packet);
    }

    private static int handleOperator(String packet) {
        if (packet.charAt(0) == '0')
            return 1 + handleOperator0(packet.substring(1));
        return 1 + handleOperator1(packet.substring(1));
    }

    private static int handleOperator0(String packet) {
        int totalLengthOfSubPackets = binaryToInt(packet.substring(0, 15));
        packet = packet.substring(15, 15 + totalLengthOfSubPackets);

        int startIndex = 0;
        while (true) {
            packet = packet.substring(startIndex);
            startIndex = handlePacket(packet);
            if (startIndex == 0) {
                return 15 + totalLengthOfSubPackets;
            }
        }
    }

    private static int handleOperator1(String packet) {
        int numberOfSubPackets = binaryToInt(packet.substring(0, 11));
        packet = packet.substring(11);

        int startIndex = 0;
        for (int i = 0; i < numberOfSubPackets; i++) {
            String subPacket = packet.substring(startIndex);
            startIndex += handlePacket(subPacket);
        }
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
        return packet.substring(i).contains("1") ? i : packet.length();
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
}
