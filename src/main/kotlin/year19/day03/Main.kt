package year19.day03

import AoCResultPrinter
import CardinalDirection
import Reader
import kotlin.math.abs

const val year: Int = 19
const val day: Int = 3



fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)

    val directions1 = getDirections(lines[0])
    val directions2 = getDirections(lines[1])
    val path1 = getCoordinatesOnPath(directions1)
    val path2 = getCoordinatesOnPath(directions2)
    val intersections = getIntersections(path1, path2)

    println(path1)

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(intersections)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(path1, path2, intersections)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

data class Direction(
    val distance: Int,
    val direction: CardinalDirection
) {
    constructor(string: String): this(
        distance = string.substring(1).toInt(),
        direction = when(string[0]) {
            'L' -> CardinalDirection.LEFT
            'R' -> CardinalDirection.RIGHT
            'U' -> CardinalDirection.UP
            'D' -> CardinalDirection.DOWN
            else -> throw IllegalArgumentException(string)
        }
    )
}

fun getDirections(line: String): List<Direction> {
    return line.split(",")
        .map { Direction(it) }
}

fun getCoordinatesOnPath(directions: List<Direction>): Map<Pair<Int, Int>, Int> {
    val path = mutableMapOf<Pair<Int, Int>, Int>()
    var currentX = 0
    var currentY = 0
    var currentDistance = 0
    for (direction in directions) {
        val dCol = direction.direction.dCol
        val dRow = direction.direction.dRow
        repeat(direction.distance) { index ->
            path[Pair(currentX + dCol * (index + 1), currentY + dRow * (index + 1))] = currentDistance + index + 1
        }
        currentX += direction.distance * direction.direction.dCol
        currentY += direction.distance * direction.direction.dRow
        currentDistance += direction.distance
    }
    return path
}

fun getIntersections(path1: Map<Pair<Int, Int>, Int>, path2: Map<Pair<Int, Int>, Int>) =
    path1.keys.filter { path2.keys.contains(it) }

fun getPart1Result(
    intersections: List<Pair<Int, Int>>
) = intersections.minOf { coordinate -> abs(coordinate.first) + abs(coordinate.second) }

fun getPart2Result(
    path1: Map<Pair<Int, Int>, Int>,
    path2: Map<Pair<Int, Int>, Int>,
    intersections: List<Pair<Int, Int>>
) = intersections.minOf { coordinate -> path1[coordinate]!! + path2[coordinate]!! }
