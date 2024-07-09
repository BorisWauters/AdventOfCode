package wauters.java.aoc2022.day13;

import java.util.ArrayList;
import java.util.List;

public class Packet implements Comparable<Packet> {
    private int value;
    private List<Packet> packets;
    private final boolean integer;

    public Packet(int value) {
        this.value = value;
        this.integer = true;
    }

    public Packet(List<Packet> packets) {
        this.packets = packets;
        this.integer = false;
    }

    @Override
    public int compareTo(Packet packet) {
        if (integer && packet.integer) {
            return Integer.compare(value, packet.value);
        }

        if (integer) {
            packets = List.of(new Packet(value));
        }

        if (packet.integer) {
            packet.packets = List.of(new Packet(packet.value));
        }

        if (packets.isEmpty()) {
            return packet.packets.isEmpty() ? 0 : -1;
        }

        for (int i = 0; i < packets.size(); i++) {
            if (i >= packet.packets.size())
                return 1;

            int comp = packets.get(i).compareTo(packet.packets.get(i));
            if (comp != 0)
                return comp;
        }

        return packets.size() < packet.packets.size() ? -1 : 0;
    }

    @Override
    public String toString() {
        if (integer) {
            return String.valueOf(value);
        }

        StringBuilder stb = new StringBuilder();
        stb.append("[");
        for (int i = 0; i < packets.size(); i++) {
            stb.append(packets.get(i)).append(",");
        }
        stb.append("]");
        return stb.toString().replace(",]", "]");
    }

    public static int build(String line, List<Packet> packets) {
        int last = 0;
        for (int i = 0; i < line.length(); i++) {
            if (last != i && (line.charAt(i) == ',' || line.charAt(i) == ']')) {
                packets.add(new Packet(Integer.parseInt(line.substring(last, i))));
            }

            if (line.charAt(i) == ',') {
                last = i + 1;
            }

            if (line.charAt(i) == ']') {
                return i;
            }

            if (line.charAt(i) == '[') {
                List<Packet> p = new ArrayList<>();
                i += build(line.substring(i + 1), p) + 1;
                last = i + 1;
                packets.add(new Packet(p));
            }
        }
        return 0;
    }
}
