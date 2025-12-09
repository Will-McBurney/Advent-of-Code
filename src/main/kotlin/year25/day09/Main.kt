package year25.day09

import AoCResultPrinter
import Reader
import kotlin.math.abs

const val year: Int = 25
const val day: Int = 9

fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "test_input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
    val redTiles = lines.map { line ->
        with(line.trim().split(",").map { it.toLong() }) {
            Pair(this[1], this[0])
        }
    }

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(redTiles)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result()
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun boxSize(c1: Pair<Long, Long>, c2: Pair<Long, Long>): Long {
    return (abs(c1.first - c2.first) + 1) * (abs(c1.second - c2.second) + 1)
}

fun getPart1Result(redTiles: List<Pair<Long, Long>>): Long {
    return redTiles.indices.filter {i -> i < redTiles.lastIndex}
        .maxOf { i ->
            (i+1 .. redTiles.lastIndex).maxOf { j ->
                boxSize(redTiles[i], redTiles[j])
            }
        }
}

fun getPart2Result(): Int {
    return 0
}
