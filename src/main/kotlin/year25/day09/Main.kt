package year25.day09

import AoCResultPrinter
import Reader
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

const val year: Int = 25
const val day: Int = 9

fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
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
    }.flatten().toHashSet()
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

enum class CrossingStatus{
    NON_EDGE,
    UP_CORNER,
    DOWN_CORNER,
}

fun getFilledTiles(boundaryTiles: Set<Pair<Long, Long>>): Set<Pair<Long, Long>> {
    val maxRow = boundaryTiles.maxOf { it.first }
    val minRow = boundaryTiles.minOf { it.first }
    val maxColumn = boundaryTiles.maxOf { it.second }
    val minColumn = boundaryTiles.minOf { it.second }

    val filledTiles = boundaryTiles.toHashSet()

    for (rowIndex in minRow..maxRow) {
        var crossings = 0
        var crossingStatus = CrossingStatus.NON_EDGE
        if (rowIndex % 100L == 0L) {
            println(rowIndex)
        }

        for (columnIndex in minColumn..maxColumn) {
            val coordinate = Pair(rowIndex, columnIndex)
            if (coordinate in boundaryTiles) {
                val up = Pair(rowIndex-1, columnIndex) in boundaryTiles
                val down = Pair(rowIndex+1, columnIndex) in boundaryTiles
                if (up && down) { // crossing vertical edge not on a corner
                    check(crossingStatus == CrossingStatus.NON_EDGE)
                    crossings++
                } else if (up) { // crossing on an up corner
                    if (crossingStatus == CrossingStatus.NON_EDGE) {
                        crossingStatus = CrossingStatus.UP_CORNER
                    } else if (crossingStatus == CrossingStatus.UP_CORNER) {
                        crossingStatus = CrossingStatus.NON_EDGE
                    } else if (crossingStatus == CrossingStatus.DOWN_CORNER) {
                        crossingStatus = CrossingStatus.NON_EDGE
                        crossings++
                    }
                } else if (down) { // crossing on a down corner
                    if (crossingStatus == CrossingStatus.NON_EDGE) {
                        crossingStatus = CrossingStatus.DOWN_CORNER
                    } else if (crossingStatus == CrossingStatus.DOWN_CORNER) {
                        crossingStatus = CrossingStatus.NON_EDGE
                    } else if (crossingStatus == CrossingStatus.UP_CORNER) {
                        crossingStatus = CrossingStatus.NON_EDGE
                        crossings++
                    }
                }
            } else if (crossings % 2 == 1) {
                filledTiles.add(coordinate)
            }
        }
    }
    return filledTiles
}

fun isInteriorRectangle(c1: Pair<Long, Long>, c2: Pair<Long, Long>, filledTiles: Set<Pair<Long, Long>>): Boolean {
    println("$c1 -> $c2")
    for (row in min(c1.first, c2.first)..max(c1.first, c2.first)) {
        if ((Pair(row, c1.second) !in filledTiles) || (Pair(row, c2.second) !in filledTiles)) {
            return false
        }
    }
    for (column in min(c1.second, c2.second) .. max(c1.second, c2.second)) {
        if ((Pair(c1.first, column) !in filledTiles) || (Pair(c2.first, column) !in filledTiles)) {
            return false
        }
    }
    return true
}

fun getPart2Result(redTiles: List<Pair<Long, Long>>): Long {
    val boundaryTiles = getBoundaryTiles(redTiles)
    println(boundaryTiles)
    println("poop")
    val filledTiles = getFilledTiles(boundaryTiles)
    println(filledTiles)

    return (0..<redTiles.lastIndex).toList().parallelStream()
        .peek { println(it) }
        .map { i ->
            (i + 1..redTiles.lastIndex).map { j ->
                if (isInteriorRectangle(redTiles[i], redTiles[j], filledTiles))
                    return@map boxSize(redTiles[i], redTiles[j])
                else return@map 0L
            }.max()
        }
        .max{i, j -> i.compareTo(j) }.orElse(-1L)
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
