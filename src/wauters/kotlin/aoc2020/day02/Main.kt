package wauters.kotlin.aoc2020.day02

import wauters.kotlin.Solution

fun main() {
    val solution = Main()
    print(solution.solvePartTwo())
}

class Main : Solution() {
    override fun partOne(lines: Array<String>): String {
        return lines.count { line -> isValidPart1(line) }.toString()
    }

    private fun isValidPart1(line: String): Boolean {
        val split1 = line.split(": ")
        val split2 = split1[0].split(" ")
        val split3 = split2[0].split("-")
        val min = Integer.parseInt(split3[0])
        val max = Integer.parseInt(split3[1])
        val char = split2[1][0]
        val password = split1[1]

        return password.filter { c -> c == char }.length in min..max
    }

    override fun partTwo(lines: Array<String>): String {
        return lines.count { line -> isValidPart2(line) }.toString()
    }

    private fun isValidPart2(line: String): Boolean {
        val split1 = line.split(": ")
        val split2 = split1[0].split(" ")
        val split3 = split2[0].split("-")
        val i1 = Integer.parseInt(split3[0]) - 1
        val i2 = Integer.parseInt(split3[1]) - 1
        val c = split2[1][0]
        val password = split1[1]

        return (password[i1] == c) != (password[i2] == c)
    }
}