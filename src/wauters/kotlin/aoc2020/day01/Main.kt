package wauters.kotlin.aoc2020.day01

import wauters.kotlin.Solution

fun main() {
    val solution = Main()
    print(solution.solvePartTwo())
}

class Main : Solution() {
    override fun partOne(lines: Array<String>): String {
        val sortedEntries = lines.asList().map { str -> Integer.parseInt(str) }.sorted()

        var first = 0
        var last = sortedEntries.size - 1

        while (sortedEntries[first] + sortedEntries[last] != 2020) {
            if (sortedEntries[first] + sortedEntries[last] > 2020) {
                last--
            } else {
                first++
            }
        }

        return (sortedEntries[first] * sortedEntries[last]).toString()
    }

    override fun partTwo(lines: Array<String>): String {
        val sortedEntries = lines.asList().map { str -> Integer.parseInt(str) }.sorted()

        var i1 = 0
        var i2 = 1
        var i3 = sortedEntries.size - 1

        while (sortedEntries[i1] + sortedEntries[i2] + sortedEntries[i3] != 2020) {
            if (sortedEntries[i1] + sortedEntries[i3] >= 2020 - sortedEntries[i2]) {
                i2 = i1 + 1
                i3--
            } else if (i2 < i3 - 1) {
                i2++
            } else {
                i1++
                i2 = i1 + 1
            }
        }

        return (sortedEntries[i1] * sortedEntries[i2] * sortedEntries[i3]).toString()
    }
}