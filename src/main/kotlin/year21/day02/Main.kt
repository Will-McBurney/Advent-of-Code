package year21.day02

import AoCResultPrinter
import Reader
const val year: Int = 21
const val day: Int = 2



fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(lines)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(lines)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getPart1Result(lines: List<String>): Int {
    var forward: Int = 0
    var depth: Int = 0

    for (line in lines) {
        val magnitude: Int = line.substringAfter(" ").toInt()
        when (line.substringBefore(" ")) {
            "forward" -> forward += magnitude
            "down" -> depth += magnitude
            "up" -> depth -= magnitude
        }
    }

    return forward * depth
}

fun getPart2Result(lines: List<String>): Long {
    var forward: Long = 0
    var depth: Long = 0
    var aim: Long = 0

    for (line in lines) {
        val magnitude: Int = line.substringAfter(" ").toInt()
        when (line.substringBefore(" ")) {
            "forward" -> {
                forward += magnitude
                depth += aim * magnitude
            }
            "down" -> aim += magnitude
            "up" -> aim -= magnitude
        }
    }

    return forward * depth
}
