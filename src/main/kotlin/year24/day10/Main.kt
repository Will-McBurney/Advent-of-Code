package year24.day10

import AoCResultPrinter
import Grid
import GridCoordinate
import Reader
import java.util.*

const val year: Int = 24
const val day: Int = 10



fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
    val grid = Grid(lines.map{
        it.toCharArray().map { c -> c.digitToInt() }.toMutableList()
    }.toMutableList())

    val trailHeads = grid.coordinates.filter { coordinate -> grid.get(coordinate) == 0 }

    println(grid)

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(grid, trailHeads)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(grid, trailHeads)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getPart1Result(
    grid: Grid<Int>,
    trailHeads: List<GridCoordinate>
): Int {
    return trailHeads.sumOf { trailhead ->
        val stack = Stack<GridCoordinate>()
        val destinationsSet = mutableSetOf<GridCoordinate>()

        stack.push(trailhead)
        while (stack.isNotEmpty()) {
            val node = stack.pop()
            if (grid.get(node) == 9) {
                destinationsSet.add(node)
                continue
            }
            grid.getCardinalNeighbors(node)
                .filter { neighbor -> grid.get(neighbor) == grid.get(node) + 1 }
                .forEach { neighbor -> stack.push(neighbor) }
        }
        destinationsSet.size
    }
}

fun getPart2Result(grid: Grid<Int>, trailHeads: List<GridCoordinate>): Int {
    return trailHeads.sumOf { trailhead ->
        val stack = Stack<GridCoordinate>()
        var destinationsReached = 0

        stack.push(trailhead)
        while (stack.isNotEmpty()) {
            val node = stack.pop()
            if (grid.get(node) == 9) {
                destinationsReached++
                continue
            }
            grid.getCardinalNeighbors(node)
                .filter { neighbor -> grid.get(neighbor) == grid.get(node) + 1 }
                .forEach { neighbor -> stack.push(neighbor) }
        }
        destinationsReached
    }
}
