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

fun getFilledTileRanges(cornerTiles: List<Pair<Long, Long>>): Map<Long, List<LongRange>> {
    val maxColumn = cornerTiles.maxOf { it.second }
    val minColumn = cornerTiles.minOf { it.second }

    val rowsOfInterest = getRowsOfInterest(cornerTiles)
    val boundaryTiles = getBoundaryTiles(cornerTiles)

    val fillTileRanges = mutableMapOf<Long, List<LongRange>>()

    for (rowIndex in rowsOfInterest) {
        var crossings = 0
        var crossingStatus = CrossingStatus.NON_EDGE

        val rowFilledTiles = mutableListOf<Long>()

        for (columnIndex in minColumn..maxColumn) {
            val coordinate = Pair(rowIndex, columnIndex)
            if (coordinate in boundaryTiles) {
                rowFilledTiles.add(columnIndex)
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
                rowFilledTiles.add(columnIndex)
            }
        }
        if (rowFilledTiles.isEmpty()) {
            fillTileRanges[rowIndex] = mutableListOf()
            continue
        }

        val ranges = getRangesFromNumbers(rowFilledTiles)
        //println("$rowIndex -> $ranges")
        fillTileRanges[rowIndex] = ranges
    }
    return fillTileRanges
}

fun getRangesFromNumbers(rowFilledTiles: MutableList<Long>): MutableList<LongRange> {
    val ranges = mutableListOf<LongRange>()
    var rangeStart = rowFilledTiles.first()
    var last = rangeStart
    for (i in 1..rowFilledTiles.lastIndex) {
        if (rowFilledTiles[i] != last + 1) {
            ranges.add(rangeStart..last)
            rangeStart = rowFilledTiles[i]
        }
        if (i == rowFilledTiles.lastIndex) {
            ranges.add(rangeStart..rowFilledTiles[i])
        }
        last = rowFilledTiles[i]
    }
    return ranges
}

var rowsOfInterest: List<Long>? = null
private fun getRowsOfInterest(cornerTiles: List<Pair<Long, Long>>): List<Long> {
    if (rowsOfInterest == null) {
        rowsOfInterest = cornerTiles.map { it.first }
            .distinct()
            .sorted()
    }
    return rowsOfInterest!!
}

fun isInteriorRectangle(
    c1: Pair<Long, Long>,
    c2: Pair<Long, Long>,
    filledTileRanges: Map<Long, List<LongRange>>
): Boolean {
    val rowRange = min(c1.first, c2.first)..max(c1.first, c2.first)
    return filledTileRanges.keys.filter { k -> k in rowRange }
        .all { k ->
            val filledRanges = filledTileRanges[k]!!
            val c1Index = filledRanges.indexOfFirst { range ->  c1.second in range }
            val c2Index = filledRanges.indexOfFirst { range ->  c2.second in range }
            c1Index != -1 && c1Index == c2Index
        }
}

fun getPart2Result(redTiles: List<Pair<Long, Long>>): Long {
    val filledRanges = getFilledTileRanges(redTiles)
    
    val maxSize = (0 ..< redTiles.lastIndex).toList().parallelStream()
        .map { i ->
            (i + 1..redTiles.lastIndex).filter { j -> isInteriorRectangle(redTiles[i], redTiles[j], filledRanges) }
                .maxOfOrNull{ j -> boxSize(redTiles[i], redTiles[j])}
        }.mapToLong { optLong -> optLong ?: 0L }
        .max().asLong

    return maxSize;
    
//    var largestBox = 0L
//    for (i in 0 ..< redTiles.lastIndex) {
//        for (j in i+1 .. redTiles.lastIndex) {
//            val boxSize = boxSize(redTiles[i], redTiles[j])
//            if (boxSize > largestBox &&
//                isInteriorRectangle(
//                    redTiles[i],
//                    redTiles[j],
//                    filledRanges
//                )) {
//                largestBox = boxSize
//            }
//        }
//    }
//    return largestBox
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
