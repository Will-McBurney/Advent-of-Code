package year25.day01

import AoCResultPrinter
import Reader
import kotlin.math.abs

const val year: Int = 25
const val day: Int = 1


fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
    val directions = lines.map { line ->
        when(line[0]) {
            'L' -> -1 * line.substring(1).toInt()
            'R' -> line.substring(1).toInt()
            else -> throw IllegalArgumentException()
        }
    }

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(directions, 50)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(directions, 50)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getPart1Result(directions: List<Int>, startingNumber: Int): Int {
    var zeroCount = 0
    var currentLocation = startingNumber
    directions.forEach { d ->
        currentLocation += d
        if (currentLocation % 100 == 0) {
            zeroCount++
        }
    }
    return zeroCount
}

fun getPart2Result(directions: List<Int>, startingNumber: Int): Int {
    var zeroCount = 0
    var currentLocation = startingNumber
    directions.forEach { d ->
        val move = if (d > 0) 1 else -1
        repeat(abs(d)) {
            currentLocation += move
            if (currentLocation % 100 == 0) {
                zeroCount++
            }
        }
    }
    return zeroCount
}
