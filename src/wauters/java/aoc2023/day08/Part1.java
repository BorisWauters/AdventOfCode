package com.github.aoc2023.day08;

import com.github.Solution;
import com.github.helper.Pair;

import java.util.HashMap;
import java.util.Map;

public class Part1 extends Solution {
    @Override
    protected void solve(String[] lines) {
        String leftRight = lines[0];
        Map<String, Pair<String, String>> desertMap = new HashMap<>();

        for (int i = 2; i < lines.length; i++) {
            String line = lines[i];

            String currentNode = line.substring(0, 3);

            String left = line.substring(7, 10);
            String right = line.substring(12, 15);

            desertMap.put(currentNode, new Pair<>(left, right));
        }

        int index = 0;
        int stepCount = 0;
        String currentNode = "AAA";

        while (!"ZZZ".equals(currentNode)) {
            char direction = leftRight.charAt(index);

            if (direction == 'L') {
                currentNode = desertMap.get(currentNode).getA();
            } else {
                currentNode = desertMap.get(currentNode).getB();
            }

            index++;
            stepCount++;
            if (index >= leftRight.length()) {
                index = 0;
            }
        }

        System.out.println(stepCount);
    }
}
