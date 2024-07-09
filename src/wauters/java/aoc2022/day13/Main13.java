package wauters.java.aoc2022.day13;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Main13 {

    public static void main(String[] args) {
        try (Stream<String> stream = Files.lines(Paths.get("input/aoc2022/day13.txt"))) {
            solve(stream.toArray(String[]::new));
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(String[] lines) {
        List<Packet> packets = new ArrayList<>();

        for (String line : lines) {
            List<Packet> p = new ArrayList<>();
            Packet.build(line, p);
            packets.addAll(p);
        }
        // Part 1
        int sum = 0;
        for (int i = 0; i < packets.size() / 2; i++) {
            Packet p1 = packets.get(2 * i);
            Packet p2 = packets.get(2 * i + 1);

            int index = i + 1;
            if (p1.compareTo(p2) <= 0) {
                sum += index;
            }
        }

        System.out.println("Part 1: " + sum);

        // Part 2
        List<Packet> dividers = new ArrayList<>(2);
        Packet.build("[[2]]", dividers);
        Packet.build("[[6]]", dividers);
        packets.addAll(dividers);

        packets.sort((p1, p2) -> p1.compareTo(p2));

        int decoderKey = 1;
        for (Packet divider : dividers) {
            decoderKey *= (packets.indexOf(divider) + 1);
        }
        System.out.println("Part 2: " + decoderKey);
    }
}
