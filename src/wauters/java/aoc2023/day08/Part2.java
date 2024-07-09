package wauters.java.aoc2023.day08;

import wauters.java.Solution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Part2 extends Solution {
    @Override
    protected void solve(String[] lines) {
        String leftRight = lines[0];
        Map<String, Node> nodes = new HashMap<>();
        List<Node> currentNodes = new ArrayList<>();

        for (int i = 2; i < lines.length; i++) {
            String line = lines[i];

            String currentNodeId = line.substring(0, 3);
            Node currentNode = new Node(currentNodeId);
            nodes.put(currentNodeId, currentNode);

            if (currentNodeId.charAt(2) == 'A') {
                currentNodes.add(currentNode);
            }
        }

        for (int i = 2; i < lines.length; i++) {
            String line = lines[i];
            String currentNodeId = line.substring(0, 3);
            String left = line.substring(7, 10);
            String right = line.substring(12, 15);

            Node currentNode = nodes.get(currentNodeId);

            currentNode.setLeft(nodes.get(left));
            currentNode.setRight(nodes.get(right));
        }

        List<Integer> loopCounts = new ArrayList<>();

        for (Node node : currentNodes) {
            int index = 0;
            int stepCount = 0;
            while (!node.isEnd()) {
                char direction = leftRight.charAt(index);
                node = node.next(direction);
                index++;
                stepCount++;
                if (index >= leftRight.length()) {
                    index = 0;
                }
            }
            loopCounts.add(stepCount);
        }

        Map<Integer, Integer> factorCount = new HashMap<>();
        for (int number : loopCounts) {
            getFactors(number).forEach((key, value) -> {
                factorCount.putIfAbsent(key, value);
                factorCount.computeIfPresent(key, (k, v) -> Math.max(v, value));
            });
        }

        factorCount.entrySet().stream().mapToLong(entry -> (long) entry.getKey() * entry.getValue()).reduce((a, b) -> a * b).ifPresent(System.out::println);
    }

    private Map<Integer, Integer> getFactors(int number) {
        Map<Integer, Integer> factorCount = new HashMap<>();
        for (int i = 2; i <= number; i++) {
            if (number % i == 0) {
                factorCount.putAll(getFactors(number / i));
                factorCount.put(i, factorCount.getOrDefault(i, 0) + 1);
                break;
            }
        }
        return factorCount;
    }

    static class Node {
        private final String name;
        private Node left;
        private Node right;
        private final boolean end;

        public Node(String name) {
            this.name = name;
            this.end = name.charAt(2) == 'Z';
        }

        public String getName() {
            return name;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public Node next(char direction) {
            return direction == 'L' ? left : right;
        }

        public boolean isEnd() {
            return end;
        }
    }
}
