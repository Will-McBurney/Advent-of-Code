package year24.day04

import AoCResultPrinter
import CardinalDirection
import Grid
import GridCoordinate
import OrdinalDirection
import Reader

const val year: Int = 24
const val day: Int = 4



fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
    val grid: Grid<Char> = Grid(lines.map {
        it.toCharArray().toMutableList()
    }.toMutableList())

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(grid)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(grid)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getPart1Result(grid: Grid<Char>): Int {
    return grid.coordinates.sumOf { coordinate ->
        OrdinalDirection.entries.count{ direction ->
            isXMas(coordinate, direction, grid)
        }
    }
}

fun isXMas(coordinate: GridCoordinate, direction: OrdinalDirection, grid: Grid<Char>): Boolean {
    return try {
        grid.get(coordinate) == 'X' &&
                grid.get(coordinate.getMovement(direction)) == 'M' &&
                grid.get(coordinate.getMovement(direction).getMovement(direction)) == 'A' &&
                grid.get(coordinate.getMovement(direction).getMovement(direction).getMovement(direction)) == 'S'
    } catch (e: IndexOutOfBoundsException) {
        false
    }


}

fun getPart2Result(grid: Grid<Char>): Int {
    return grid.coordinates.sumOf { coordinate ->
        CardinalDirection.entries.count{ direction ->
            isMasX(coordinate, direction, grid)
        }
    }
}

fun isMasX(coordinate: GridCoordinate, direction: CardinalDirection, grid: Grid<Char>): Boolean {
    if (grid.get(coordinate) != 'A') {
        return false
    }
    try {
        val (mA, mB, sA, sB) = when (direction) {
            CardinalDirection.UP -> listOf(
                grid.get(GridCoordinate(coordinate.row - 1, coordinate.col + 1)),
                grid.get(GridCoordinate(coordinate.row - 1, coordinate.col - 1)),
                grid.get(GridCoordinate(coordinate.row + 1, coordinate.col + 1)),
                grid.get(GridCoordinate(coordinate.row + 1, coordinate.col - 1))
            )

            CardinalDirection.LEFT -> listOf(
                grid.get(GridCoordinate(coordinate.row + 1, coordinate.col - 1)),
                grid.get(GridCoordinate(coordinate.row - 1, coordinate.col - 1)),
                grid.get(GridCoordinate(coordinate.row + 1, coordinate.col + 1)),
                grid.get(GridCoordinate(coordinate.row - 1, coordinate.col + 1))
            )

            CardinalDirection.DOWN -> listOf(
                grid.get(GridCoordinate(coordinate.row + 1, coordinate.col + 1)),
                grid.get(GridCoordinate(coordinate.row + 1, coordinate.col - 1)),
                grid.get(GridCoordinate(coordinate.row - 1, coordinate.col + 1)),
                grid.get(GridCoordinate(coordinate.row - 1, coordinate.col - 1))
            )

            CardinalDirection.RIGHT -> listOf(
                grid.get(GridCoordinate(coordinate.row + 1, coordinate.col + 1)),
                grid.get(GridCoordinate(coordinate.row - 1, coordinate.col + 1)),
                grid.get(GridCoordinate(coordinate.row + 1, coordinate.col - 1)),
                grid.get(GridCoordinate(coordinate.row - 1, coordinate.col - 1))
            )
        }
        return (mA == 'M' && mB == 'M' && sA == 'S' && sB == 'S')
    } catch (ex: IndexOutOfBoundsException) {
        return false
    }
}
