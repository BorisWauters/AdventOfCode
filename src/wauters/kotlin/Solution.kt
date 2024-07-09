package wauters.kotlin

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

abstract class Solution protected constructor() {
    val test: String = "input/test.txt"
    val input: String

    init {
        val path = this.javaClass.canonicalName.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()
        this.input = "input/" + path[path.size - 3] + "/" + path[path.size - 2] + ".txt"
    }

    protected abstract fun partOne(lines: Array<String>): String
    protected abstract fun partTwo(lines: Array<String>): String

    fun testPartOne(): String {
        return partOne(parseData(test))
    }

    fun testPartTwo(): String {
        return partTwo(parseData(test))
    }

    fun solvePartOne(): String {
        return partOne(parseData(input))
    }

    fun solvePartTwo(): String {
        return partTwo(parseData(input))
    }

    private fun parseData(path: String): Array<String> {
        try {
            Files.lines(Paths.get(path)).use { stream ->
                return stream.toArray { length -> arrayOfNulls<String>(length) }
            }
        } catch (e: IOException) {
            System.err.println(e.localizedMessage)
            return arrayOf()
        }
    }

}