package year25.day09

import AoCResultPrinter
import Reader
import year22.day04.getWorkPairs
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

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
    val part2Result = getPart2Result(redTiles)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun boxSize(c1: Pair<Long, Long>, c2: Pair<Long, Long>): Long {
    return (abs(c1.first - c2.first) + 1) * (abs(c1.second - c2.second) + 1)
}

/** this is O(n^2) which is bad, I know there's a divide and conquer approach, but it's fast enough I don't care */
fun getPart1Result(redTiles: List<Pair<Long, Long>>): Long {
    return (0..< redTiles.lastIndex).maxOf { i ->
        (i + 1..redTiles.lastIndex).maxOf { j ->
            boxSize(redTiles[i], redTiles[j])
        }
    }
}

fun getBoundaryTiles(redTiles: List<Pair<Long, Long>>): Set<Pair<Long, Long>> {
    return (0..redTiles.lastIndex).map{ i ->
        getConnectingTiles(redTiles[i], redTiles[(i+1) % redTiles.size])
    }.flatten().toSet()
}

fun getConnectingTiles(c1: Pair<Long, Long>, c2: Pair<Long, Long>): List<Pair<Long, Long>> {
    require(c1.first == c2.first || c1.second == c2.second)
    return if (c1.first == c2.first) { // same column
        (min(c1.second, c2.second)..max(c1.second, c2.second)).map { i ->
            Pair(c1.first, i)
        }
    } else { // same row
        (min(c1.first, c2.first)..max(c1.first, c2.first)).map { i ->
            Pair(i, c1.second)
        }
    }
}

fun getFilledTiles(boundaryTiles: Set<Pair<Long, Long>>): Set<Pair<Long, Long>> {
    val maxRow = boundaryTiles.maxOf { it.first }
    val minRow = boundaryTiles.minOf { it.first }
    val maxColumn = boundaryTiles.maxOf { it.second }
    val minColumn = boundaryTiles.minOf { it.second }

    val filledTiles = mutableSetOf<Pair<Long, Long>>()

    for (rowIndex in minRow..maxRow) {
        var parity = false
        for (columnIndex in minColumn..maxColumn) {
            val coordinate = Pair(rowIndex, columnIndex)
            if (coordinate in boundaryTiles) {
                filledTiles.add(coordinate)
                parity = !parity // this isn't right and I need to debug
            } else if (parity) {
                filledTiles.add(Pair(rowIndex, columnIndex))
            }
        }
    }
    val c = Pair(6L, 2L)
    println("After:  ${c in filledTiles}")

    return filledTiles
}

fun getPart2Result(redTiles: List<Pair<Long, Long>>): Long {
    val redTilesSet = redTiles.toSet()
    printGrid(redTilesSet)

    println()
    val boundaryTiles = getBoundaryTiles(redTiles)
    printGrid(boundaryTiles)
    println()
    val filledTiles = getFilledTiles(boundaryTiles)
    printGrid( filledTiles)
    return 0L
}

private fun printGrid(
    selectedTiles: Set<Pair<Long, Long>>
) {

    val gridSize = 1 + selectedTiles.maxOf{ max(it.first, it.second) }
    (0..gridSize).forEach { i ->
        (0..gridSize).forEach { j ->
            if (Pair(i, j) in selectedTiles) {
                print("X")
            } else {
                print(".")
            }
        }
        println()
    }
}
