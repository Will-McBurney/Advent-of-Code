package year25.day07

import AoCResultPrinter
import CardinalDirection
import Grid
import GridCoordinate
import Reader
import java.util.*

const val year: Int = 25
const val day: Int = 7

const val START = 'S'
const val SPLITTER = '^'
const val EMPTY = '.'


fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
    
    val grid = Grid(
        lines.map { line -> line.toCharArray().toMutableList() }
        .toMutableList()
    )
    
    val startingPoint = grid.coordinates.first { grid.get(it) == START }

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(grid, startingPoint)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(grid, startingPoint)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getPart1Result(grid: Grid<Char>, startingPoint: GridCoordinate): Int {
    val visited = mutableSetOf(startingPoint)
    val stack = Stack<GridCoordinate>()
    stack.push(startingPoint)
    var splitCount = 0
    
    while (stack.isNotEmpty()) {
        val next = stack.pop()
        if (grid.get(next) == SPLITTER) splitCount++
        val neighbors = if (grid.get(next) != SPLITTER) listOf(next.getMovement(CardinalDirection.DOWN))
            else listOf(
                next.getMovement(CardinalDirection.LEFT),
                next.getMovement(CardinalDirection.RIGHT)
            )
        neighbors.filter { c -> grid.isInBounds(c) && !visited.contains(c) }
            .forEach { c ->
                visited.add(c)
                stack.push(c)
            }
    }
    
    return splitCount
}

fun getPart2Result(grid: Grid<Char>, startingPoint: GridCoordinate): Long {
    return search(grid, startingPoint)
}

val searchCache = mutableMapOf<GridCoordinate, Long>()
fun search(grid: Grid<Char>, coordinate: GridCoordinate): Long {
    if (searchCache.containsKey(coordinate)) return searchCache[coordinate]!!
    val result = if (!grid.isInBounds(coordinate)) 1L
        else if (grid.get(coordinate) != SPLITTER) search(grid, coordinate.getMovement(CardinalDirection.DOWN))
        else search(grid, coordinate.getMovement(CardinalDirection.LEFT)) + search(grid, coordinate.getMovement(CardinalDirection.RIGHT))
    searchCache[coordinate] = result
    return result
}
