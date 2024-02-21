package year21.day07

import AoCResultPrinter
import Reader
import kotlin.math.abs

const val year: Int = 21
const val day: Int = 7

fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
    val startingPositions: List<Int> = lines[0].split(",")
        .map { token -> token.toInt() }
    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(startingPositions)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(startingPositions)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getPart1Result(startingPositions: List<Int>): Int {
    return (startingPositions.min() .. startingPositions.max()).minOf { target ->
        startingPositions.sumOf { position -> abs(position - target) }
    }
}

fun getPart2Result(startingPositions: List<Int>): Int {
    return (startingPositions.min() .. startingPositions.max()).minOf { target ->
        startingPositions.map { position -> abs(position - target) }
            .sumOf { dist -> (dist * (dist + 1))/2 }
    }
}
