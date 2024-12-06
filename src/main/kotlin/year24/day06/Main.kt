package year24.day06

import AoCResultPrinter
import CardinalDirection
import Grid
import GridCoordinate
import Reader

const val year: Int = 24
const val day: Int = 6



fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)

    val grid: Grid<Char> = Grid(lines.map{
        it.toCharArray().toMutableList()
    }.toMutableList())

    val guardStart: GridCoordinate = grid.coordinates.single{ grid.get(it) == '^'}


    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(grid, guardStart)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(grid, guardStart)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun isObstruction(coordinate: GridCoordinate, grid: Grid<Char>) =
    grid.isInBounds(coordinate) && grid.get(coordinate) == '#'

fun getPart1Result(grid: Grid<Char>, guardStart: GridCoordinate): Int {
    val path = mutableSetOf<GridCoordinate>()
    path.add(guardStart)
    var guardPosition = guardStart
    var guardDirection = CardinalDirection.UP

    while (grid.isInBounds(guardPosition)) {
        var forward = guardPosition.getMovement(guardDirection)
        while (isObstruction(forward, grid)) {
            guardDirection = guardDirection.clockwise
            forward = guardPosition.getMovement(guardDirection)
        }
        path.add(forward)
        guardPosition = forward
    }
    return path.size - 1 // counting stepping out of bounds
}

fun getPart2Result(grid: Grid<Char>, guardStart: GridCoordinate): Int {
    return grid.coordinates.filterNot { isObstruction(it, grid) }
        .count { isLoop(guardStart, grid, it) }
}

fun isLoop(guardStart: GridCoordinate, grid: Grid<Char>, newObstruction: GridCoordinate): Boolean {
    val pathDirections = mutableSetOf<Pair<GridCoordinate, CardinalDirection>>()
    pathDirections.add(Pair(guardStart, CardinalDirection.UP))
    var guardPosition = guardStart
    var guardDirection = CardinalDirection.UP
    while (grid.isInBounds(guardPosition)) {
        var forward = guardPosition.getMovement(guardDirection)
        while (forward == newObstruction || isObstruction(forward, grid)) {
            guardDirection = guardDirection.clockwise
            forward = guardPosition.getMovement(guardDirection)
        }
        val pair = Pair(forward, guardDirection)
        if (pathDirections.contains(pair)) {
            return true
        }
        pathDirections.add(pair)
        guardPosition = forward
    }
    return false
}
