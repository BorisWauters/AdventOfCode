package wauters.kotlin.aoc2020.day03

import wauters.kotlin.Solution

fun main() {
    val solution = Main()
    print(solution.solvePartTwo())
}

class Main : Solution() {
    override fun partOne(lines: Array<String>): String {
        return countTreesInSlope(lines, 1, 3).toString()
    }

    override fun partTwo(lines: Array<String>): String {
        return arrayOf(
            countTreesInSlope(lines, 1, 1),
            countTreesInSlope(lines, 1, 3),
            countTreesInSlope(lines, 1, 5),
            countTreesInSlope(lines, 1, 7),
            countTreesInSlope(lines, 2, 1),
        ).reduce { acc, i -> acc * i }.toString()
    }

    private fun countTreesInSlope(forest: Array<String>, stepSizeDown: Int, stepSizeRight: Int): Long {
        var treeCount = 0L
        val mod = forest[0].length
        for (i in stepSizeDown..<forest.size step stepSizeDown) {
            if (forest[i][(stepSizeRight * i / stepSizeDown) % mod] == '#') {
                treeCount++
            }
        }
        println("$stepSizeDown, $stepSizeRight\t$treeCount")
        return treeCount
    }
}