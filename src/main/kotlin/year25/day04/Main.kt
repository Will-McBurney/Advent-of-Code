package year25.day04

import AoCResultPrinter
import Grid
import GridCoordinate
import Reader

const val year: Int = 25
const val day: Int = 4



fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
    val grid = Grid (
        lines.map { line ->
            line.toCharArray().map { ch -> ch == '@' }.toMutableList()
        }.toMutableList()
    )

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(grid)
    println(part1Result)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(grid)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getRemovableCoordinates(grid: Grid<Boolean>): List<GridCoordinate> {
    return grid.coordinates.filter { c ->
        grid.get(c) && grid.getOrdinalNeighbors(c).count { grid.get(it) } < 4
    }
}

fun getPart1Result(grid: Grid<Boolean>): Int {
    return getRemovableCoordinates(grid).size
}

fun getPart2Result(grid: Grid<Boolean>): Int {
    var removedCount = 0
    var removableCoordinates = getRemovableCoordinates(grid)
    while (removableCoordinates.isNotEmpty()) {
        removedCount += removableCoordinates.size
        getRemovableCoordinates(grid).forEach { c ->
            grid.set(c, false)
        }
        removableCoordinates = getRemovableCoordinates(grid)
    }
    return removedCount
}
