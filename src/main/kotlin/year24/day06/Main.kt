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

    val path = getPath(grid, guardStart)
    val visited = path.path.distinct()

    printer.endSetup()

    //Do Part 1
    val part1Result = visited.size
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(grid, guardStart, visited)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

class Path() {
    val path: MutableList<GridCoordinate> = mutableListOf()
    val pathDirections = mutableSetOf<Pair<GridCoordinate, CardinalDirection>>()
    var isLoop = false
        private set

    fun add(gridCoordinate: GridCoordinate, cardinalDirection: CardinalDirection) {
        val pair = Pair(gridCoordinate, cardinalDirection)
        if (pathDirections.contains(pair)) {
            isLoop = true
        }
        path.add(gridCoordinate)
        pathDirections.add(pair)
    }
}

fun getPath(
    grid: Grid<Char>,
    guardStart: GridCoordinate,
    extraObstruction: GridCoordinate? = null
): Path {
    val path = Path()
    var guardPosition = guardStart
    var guardDirection = CardinalDirection.UP

    while (grid.isInBounds(guardPosition)) {
        path.add(guardPosition, guardDirection)
        if (path.isLoop) {
            return path
        }
        var forward = guardPosition.getMovement(guardDirection)
        while (forward == extraObstruction || isObstruction(forward, grid)) {
            guardDirection = guardDirection.clockwise
            forward = guardPosition.getMovement(guardDirection)
        }
        guardPosition = forward
    }
    return path
}

fun isObstruction(coordinate: GridCoordinate, grid: Grid<Char>) =
    grid.isInBounds(coordinate) && grid.get(coordinate) == '#'

fun getPart1Result(path: List<GridCoordinate>): Int {
    return path.size
}

fun getPart2Result(grid: Grid<Char>, guardStart: GridCoordinate, visited: List<GridCoordinate>): Int {
    return visited.count { getPath(grid ,guardStart, it).isLoop }
}
