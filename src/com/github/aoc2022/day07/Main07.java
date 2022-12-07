package com.github.aoc2022.day07;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Main07 {

    public static int TOTAL_MEMORY = 70000000;
    public static int NEEDED_MEMORY = 30000000;

    private static List<Directory> dirs = new ArrayList<>();

    public static void main(String[] args) {
        try (Stream<String> stream = Files.lines(Paths.get("input/aoc2022/day07.txt"))) {
            solve(stream.toArray(String[]::new));
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(String[] lines) {
        Directory root = new Directory();
        Directory current = root;

        // Build file structure
        for (String line : lines) {
            if (line.charAt(0) == '$') {
                current = parseCommand(root, current, line);
            } else {
                parseContent(current, line);
            }
        }

        // Part 1
        int totalFileSumBelowThreshold = dirs.stream()
                .mapToInt(d -> d.getFileSum())
                .filter(size -> size <= 100000)
                .sum();
        System.out.println("Part 1: " + totalFileSumBelowThreshold);

        // Part 2
        int availableMemory = TOTAL_MEMORY - root.getFileSum();
        int memoryToDelete = NEEDED_MEMORY - availableMemory;

        int bestFit = dirs.stream()
                .mapToInt(d -> d.getFileSum())
                .filter(size -> size >= memoryToDelete)
                .min()
                .orElseThrow();
        System.out.println("Part 2: " + bestFit);
    }

    private static Directory parseCommand(Directory root, Directory current, String line) {
        String[] commandInfo = line.split(" ");

        // Ignore ls
        if ("ls".equals(commandInfo[1])) {
            return current;
        }

        // Cd
        switch (commandInfo[2]) {
            case "/":
                return root;
            case "..":
                return current.parent;
            default:
                return current.children.stream()
                        .filter(d -> d.isDir(commandInfo[2]))
                        .findFirst()
                        .orElseThrow();
        }
    }

    private static void parseContent(Directory directory, String line) {
        String[] commandInfo = line.split(" ");

        // Dir
        if ("dir".equals(commandInfo[0])) {
            dirs.add(directory.addDir(commandInfo[1]));
            return;
        }

        // File
        directory.addFile(Integer.parseInt(commandInfo[0]), commandInfo[1]);
    }
}

class Directory {
    Directory parent;
    String name;
    List<Directory> children;
    List<File> files;

    private int fileSum;

    public Directory() {
        this.parent = null;
        this.name = "/";
        this.children = new ArrayList<>();
        this.files = new ArrayList<>();

        this.fileSum = -1;
    }

    public Directory(Directory parent, String name) {
        this.parent = parent;
        this.name = name;
        this.children = new ArrayList<>();
        this.files = new ArrayList<>();

        this.fileSum = -1;
    }

    public boolean isDir(String name) {
        return this.name.equals(name);
    }

    public Directory addDir(String name) {
        Directory dir = new Directory(this, name);
        this.children.add(dir);
        return dir;
    }

    public void addFile(int size, String name) {
        File file = new File(this, name, size);
        this.files.add(file);
    }

    public int getFileSum() {
        // Save fileSum so it only needs to be calculated once
        if (fileSum >= 0)
            return fileSum;

        fileSum = 0;
        for (Directory child : children) {
            this.fileSum += child.getFileSum();
        }

        for (File file : files) {
            this.fileSum += file.size;
        }

        return fileSum;
    }
}

class File {
    Directory parent;
    String name;
    int size;

    public File(Directory parent, String name, int size) {
        this.parent = parent;
        this.name = name;
        this.size = size;
    }
}
