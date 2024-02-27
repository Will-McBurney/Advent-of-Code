package year21.day09

import AoCResultPrinter
import Grid
import GridCoordinate
import Reader
const val year: Int = 21
const val day: Int = 9



fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
    val grid: Grid<Int> = Grid(
        lines.map { line ->
            line.toCharArray().map {
                it.digitToInt()
            }.toMutableList()
        }.toMutableList())

    printer.endSetup()

    val lowPoints = getLowPoints(grid)

    //Do Part 1
    val part1Result = getPart1Result(lowPoints, grid)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(lowPoints, grid)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getPart1Result(lowPoints: List<GridCoordinate>, grid: Grid<Int>) =
    lowPoints.sumOf { c: GridCoordinate -> grid.get(c) + 1 }

fun getLowPoints(grid: Grid<Int>): List<GridCoordinate> {
    return grid.coordinates.filter { c: GridCoordinate ->
        grid.getCardinalNeighbors(c).all { neighbor: GridCoordinate ->
            grid.get(c) < grid.get(neighbor)
        }
    }
}

fun getPart2Result(lowPoints: List<GridCoordinate>, grid: Grid<Int>): Int {
    return lowPoints.map { c -> getBasinSize(c, grid) }
        .sortedDescending()
        .take(3)
        .reduce(Int::times)
}

fun getBasinSize(lowPoint: GridCoordinate, grid: Grid<Int>): Int {
    val visited: MutableSet<GridCoordinate> = mutableSetOf()
    val queue: MutableList<GridCoordinate> = mutableListOf(lowPoint)

    while (queue.isNotEmpty()) {
        val next = queue.removeAt(0)
        visited.add(next)

        val newNeighbors = next.getCardinalNeighbors()
            .filter(grid::isInBounds)
            .filterNot {grid.get(it) == 9}
            .filterNot(visited::contains)
            .filterNot(queue::contains)
        queue.addAll(newNeighbors)
    }
    return visited.size
}
