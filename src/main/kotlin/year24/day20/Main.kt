package year24.day20

import AoCResultPrinter
import Grid
import GridCoordinate
import Reader

const val year: Int = 24
const val day: Int = 20


const val MIN_CHEAT_SIZE = 100
const val MAX_CHEAT_DISTANCE = 20


fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)

    val grid = Grid(lines.map { it.toCharArray().toMutableList() }.toMutableList())

    val start = grid.coordinates.single { grid.get(it) == 'S' }
    val end = grid.coordinates.single { grid.get(it) == 'E' }

    val path = getPath(grid, start, end)

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(grid, path)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(path)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getPath(
    grid: Grid<Char>,
    start: GridCoordinate,
    end: GridCoordinate
): Map<GridCoordinate, Int> {
    val path = mutableMapOf<GridCoordinate, Int>()
    var current = start
    var currentIndex = 0
    path[start] = 0
    while (current != end) {
        val neighbors = grid.getCardinalNeighbors(current).filterNot { isWall(grid, it) }
            .filterNot { path.contains(it) }
        require (neighbors.size <= 1)
        neighbors.forEach {
            path[it] = currentIndex
            currentIndex++
            current = it
        }
    }
    return path
}

fun isWall(grid: Grid<Char>, coordinate: GridCoordinate): Boolean {
    return grid.isInBounds(coordinate) && grid.get(coordinate) == '#'
}



fun getPart1Result(grid: Grid<Char>, path: Map<GridCoordinate, Int>): Int {
    return path.keys.sumOf { c ->
        CardinalDirection.entries.count { dir ->
            val w = c.getMovement(dir)
            if (!isWall(grid, w)) return@count false
            val n = w.getMovement(dir)
            if (isWall(grid,n) || !path.contains(n)) return@count false
            if (path[c]!! > path[n]!!) return@count false
            path[n]!! - path[c]!! - 1 >= MIN_CHEAT_SIZE
        }
    }
}


fun getPart2Result(path: Map<GridCoordinate, Int>): Int {
    val pathList = path.keys.toMutableList()
    return pathList.mapIndexed { sInd, s ->
        (sInd + MIN_CHEAT_SIZE+2 ..< path.size)
            .count { eInd ->
                s.manhattanDistanceTo(pathList[eInd]) <= MAX_CHEAT_DISTANCE
                && eInd - sInd - s.manhattanDistanceTo(pathList[eInd]) >= MIN_CHEAT_SIZE }
    }.sum()
}
