package wauters.java.aoc2023.day10;

import wauters.java.Solution;

import java.util.*;
import java.util.stream.Collectors;

public class Main10 extends Solution {
    @Override
    protected void solve(String[] lines) {
        Pipe[][] nests = new Pipe[lines.length][lines[0].length()];
        Node start = getStart(lines);

        //Part 1 and some setup for part 2
        Set<Node> nodes = bfs(start);

        //Part 2
        while (nodes.stream().anyMatch(n -> n.getNeighbours().size() < 2)) {
            Set<Node> nodesToRemove = nodes.stream().filter(n -> n.getNeighbours().size() < 2).collect(Collectors.toSet());

            nodesToRemove.forEach(n -> {
                n.getNeighbours().forEach(neighbour -> neighbour.getNeighbours().remove(n));
                nodes.remove(n);
            });

            nodes.removeAll(nodesToRemove);
        }

        for (int i = 0; i < nests.length; i++) {
            for (int j = 0; j < nests[0].length; j++) {
                nests[i][j] = Pipe.GROUND;

            }
        }
        nodes.forEach(n -> nests[n.getI()][n.getJ()] = n.getPipe());

        int count = 0;
        // If the number of vertical pipes counted is odd, we are in a loop
        // L7 and FJ combinations count as one vertical pipe
        for (int i = 0; i < nests.length; i++) {
            Pipe last = Pipe.GROUND;
            int verticals = 0;
            for (int j = 0; j < nests[0].length; j++) {
                Pipe pipe = nests[i][j];
                if (pipe.isPipe()) {
                    if (pipe == Pipe.VERTICAL) {
                        verticals++;
                    } else if (pipe.isLF()) {
                        last = pipe;
                    } else if (pipe.isJ7() && (last.pointsNorth() != pipe.pointsNorth())) {
                        verticals++;
                    }
                } else if (verticals % 2 != 0) {
                    count++;
                }
            }
        }

        System.out.println("Part 2: " + count);
    }

    private static Node getStart(String[] lines) {
        Node[][] nodes = new Node[lines.length][lines[0].length()];
        Node start = null;

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            for (int j = 0; j < line.length(); j++) {
                nodes[i][j] = new Node(line.charAt(j), i, j);

                if (nodes[i][j].getPipe() == Pipe.START) {
                    start = nodes[i][j];
                }
            }
        }

        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes[0].length; j++) {

                if (i != nodes.length - 1) {
                    nodes[i][j].linkBottom(nodes[i + 1][j]);
                }

                if (j != nodes[0].length - 1) {
                    nodes[i][j].linkRight(nodes[i][j + 1]);
                }
            }
        }

        // Set the start pipe to its actual value
        int startI = start.getI();
        int startJ = start.getJ();

        Set<Pipe> potentialStartPipes = Arrays.stream(Pipe.values()).collect(Collectors.toSet());
        potentialStartPipes.removeAll(Set.of(Pipe.GROUND, Pipe.START));
        if (startI == 0 || !nodes[startI-1][startJ].getPipe().pointsSouth()) {
            potentialStartPipes.removeAll(Set.of(Pipe.VERTICAL, Pipe.NORTH_EAST, Pipe.NORTH_WEST));
        }
        if (startI == nodes.length - 1 || !nodes[startI+1][startJ].getPipe().pointsNorth()) {
            potentialStartPipes.removeAll(Set.of(Pipe.VERTICAL, Pipe.SOUTH_EAST, Pipe.SOUTH_WEST));
        }
        if (startJ == 0 || !nodes[startI][startJ-1].getPipe().pointsEast()) {
            potentialStartPipes.removeAll(Set.of(Pipe.HORIZONTAL, Pipe.NORTH_EAST, Pipe.SOUTH_EAST));
        }
        if (startJ == nodes[0].length - 1 || !nodes[startI][startJ+1].getPipe().pointsWest()) {
            potentialStartPipes.removeAll(Set.of(Pipe.HORIZONTAL, Pipe.NORTH_WEST, Pipe.SOUTH_WEST));
        }

        if (potentialStartPipes.size() != 1) {
            throw new RuntimeException("Invalid start pipe");
        }

        start.setPipe(potentialStartPipes.iterator().next());

        return start;
    }

    private Set<Node> bfs(Node start) {
        Set<Node> visited = new HashSet<>();
        Deque<Node> queue = new ArrayDeque<>();
        queue.add(start);

        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if (visited.contains(node)) {
                continue;
            }

            visited.add(node);

            List<Node> neighbours = node.getNeighbours();
            neighbours.stream().filter(n -> n.getDistance() < 0).forEach(n -> n.setDistance(node.getDistance() + 1));
            queue.addAll(neighbours);
        }

        System.out.println("Part 1: " + visited.stream().mapToInt(Node::getDistance).max().getAsInt());

        return visited;
    }
}
