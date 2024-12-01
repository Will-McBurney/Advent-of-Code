package year20.day03

import AoCResultPrinter
import Grid
import GridCoordinate
import Reader

const val year: Int = 20
const val day: Int = 3

const val TREE = '#'

val SLOPES = listOf(
    Pair(1, 1),
    Pair(3, 1),
    Pair(5, 1),
    Pair(7, 1),
    Pair(1, 2)
)

fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
    val grid = getGrid(lines)

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(grid, 3, 1)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(grid, SLOPES)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getGrid(lines: List<String>): Grid<Char> {
    return Grid(
        lines.map { line -> line.toMutableList() }
            .toMutableList()
    )
}



fun getPart1Result(grid: Grid<Char>, right: Int, down: Int): Int {
    var currentPosition = GridCoordinate(0, 0)
    var treeCount = 0
    while (currentPosition.row < grid.numRows) {
        if (grid.get(currentPosition) == TREE) {
            treeCount++
        }
        currentPosition = GridCoordinate(
            row = currentPosition.row + down,
            col = (currentPosition.col + right) % grid.numCols
        )
    }
    return treeCount
}



fun getPart2Result(grid: Grid<Char>, slopes: List<Pair<Int, Int>>): Long =
    slopes.map { slope -> getPart1Result(grid, slope.first, slope.second).toLong() }
        .reduce(Long::times)
