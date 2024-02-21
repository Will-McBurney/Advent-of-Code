package year21.day05

import AoCResultPrinter
import GridCoordinate
import Reader
import kotlin.math.abs
import kotlin.math.max

const val year: Int = 21
const val day: Int = 5


fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
    val coordinatePairs = lines.map(::getCoordinatePair)

    printer.endSetup()

    //Do Part 1
    val part1Result = getHazardousCoordinates(
        coordinatePairs.filter { pair -> isHorizontalOrVertical(pair) }
    )
    printer.endPart1()

    //Do Part 2
    val part2Result = getHazardousCoordinates(coordinatePairs)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getCoordinatePair(line: String): Pair<GridCoordinate, GridCoordinate> {
    val coordinateStrings = line.split(" -> ")
    return Pair(
        getCoordinateFromString(coordinateStrings[0]),
        getCoordinateFromString(coordinateStrings[1])
    )
}

fun getCoordinateFromString(string: String): GridCoordinate {
    val coordinates: List<Int> = string.trim().split(",").map(String::toInt)
    return (GridCoordinate(coordinates[0], coordinates[1]))
}

fun isHorizontalOrVertical(coordinatePair: Pair<GridCoordinate, GridCoordinate>): Boolean {
    return coordinatePair.first.row == coordinatePair.second.row || coordinatePair.first.col == coordinatePair.second.col
}

fun getCoordinateLine(coordinatePair: Pair<GridCoordinate, GridCoordinate>): List<GridCoordinate> {
    val dRow = getDirection(coordinatePair) { it.row }
    val dCol = getDirection(coordinatePair) { it.col }

    val distance = max(
        abs(coordinatePair.first.row - coordinatePair.second.row),
        abs(coordinatePair.first.col - coordinatePair.second.col)
    )
    val list = (0..distance).map { i ->
        GridCoordinate(
            coordinatePair.first.row + i * dRow,
            coordinatePair.first.col + i * dCol
        )
    }
    return list
}

private fun getDirection(
    coordinatePair: Pair<GridCoordinate, GridCoordinate>,
    getDimension: (GridCoordinate) -> Int
): Int {
    val diff = getDimension(coordinatePair.first) - getDimension(coordinatePair.second)
    return when {
        diff < 0 -> 1
        diff == 0 -> 0
        else -> -1
    }
}


fun getHazardousCoordinates(coordinatePairs: List<Pair<GridCoordinate, GridCoordinate>>): Int {
    return coordinatePairs.map { pair -> getCoordinateLine(pair) }
        .flatten()
        .fold(mutableMapOf<GridCoordinate, Int>()) { map, item ->
            map[item] = 1 + map.getOrDefault(item, 0)
            map
        }.values.count { v -> v >= 2 }
}

fun getPart2Result(): Int {
    return 0
}
