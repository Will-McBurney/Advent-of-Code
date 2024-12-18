package year24.day15

import AoCResultPrinter
import CardinalDirection
import Grid
import GridCoordinate
import Reader

const val year: Int = 24
const val day: Int = 15

const val EMPTY = '.'
const val BOX = 'O'
const val WALL = '#'

fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
    val grid = Grid(
        lines.subList(0, lines.indexOfFirst { it.trim().isEmpty() })
        .map{ line ->
            line.toCharArray().toMutableList()
        }.toMutableList()
    )

    val robotStart = grid.coordinates.single{ it -> grid.get(it) == '@'}

    val part2Grid = Grid(grid.rowIndices.map { rowIndex ->
        grid.colIndices.map { colIndex ->
            return@map when (grid.get(GridCoordinate(rowIndex, colIndex))) {
                '.' -> ".."
                '#' -> "##"
                'O' -> "[]"
                '@' -> "@."
                else -> throw IllegalArgumentException()
            }
        }.joinToString("").toCharArray().toMutableList()
    }.toMutableList())

    val part2RobotStart = part2Grid.coordinates.single{ it -> part2Grid.get(it) == '@'}

    val directions = lines.subList(lines.indexOfFirst { it.trim().isEmpty() } + 1, lines.size)
        .joinToString("")
        .map {
            return@map when(it) {
                '^' -> CardinalDirection.UP
                'v' -> CardinalDirection.DOWN
                '<' -> CardinalDirection.LEFT
                '>' -> CardinalDirection.RIGHT
                else -> throw IllegalArgumentException()
            }
        }

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(grid, robotStart, directions)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(part2Grid, part2RobotStart, directions)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getPart1Result(grid: Grid<Char>, robotStart: GridCoordinate, directions: List<CardinalDirection>): Long {
    var robotPosition = robotStart
    for (direction in directions) {
        val toMove = robotPosition.getMovement(direction)
        if (grid.get(toMove) == EMPTY) {
            swap(grid, toMove, robotPosition)
            robotPosition = toMove
        }
        if (grid.get(toMove) == BOX && pushBox(grid, toMove, direction)) {
            swap(grid, toMove, robotPosition)
            robotPosition = toMove
        }
    }

    return grid.coordinates.filter { grid.get(it) == BOX }
        .sumOf { it.row * 100L + it.col }
}

fun pushBox(grid: Grid<Char>, boxCoordinate: GridCoordinate, pushDirection: CardinalDirection): Boolean {
    val nextSpace = boxCoordinate.getMovement(pushDirection)
    if (grid.get(nextSpace) == EMPTY ) {
        swap(grid, nextSpace, boxCoordinate)
        return true
    } else if (grid.get(nextSpace) == BOX && pushBox(grid, nextSpace, pushDirection)) {
        swap(grid, nextSpace, boxCoordinate)
        return true
    }
    return false
}

fun getOtherBoxHalf(grid: Grid<Char>, boxHalf: GridCoordinate): GridCoordinate {
    return if (grid.get(boxHalf) == '[') {
        boxHalf.getMovement(CardinalDirection.RIGHT)
    } else if (grid.get(boxHalf) == ']') {
        boxHalf.getMovement(CardinalDirection.LEFT)
    } else {
        throw IllegalArgumentException()
    }
}

fun swap(grid: Grid<Char>, coordA: GridCoordinate, coordB: GridCoordinate) {
    val temp = grid.get(coordA)
    grid.set(coordA, grid.get(coordB))
    grid.set(coordB, temp)
}

val BOX_HALVES = listOf('[', ']')

fun getPart2Result(grid: Grid<Char>, robotStart: GridCoordinate, directions: List<CardinalDirection>): Long {
    var robotPosition = robotStart
    for (direction in directions) {
        val toMove = robotPosition.getMovement(direction)
        if (grid.get(toMove) == EMPTY) {
            swap(grid, toMove, robotPosition)
            robotPosition = toMove
        } else if (grid.get(toMove) in BOX_HALVES && pushBoxPart2(grid, toMove, direction)) {
            swap(grid, toMove, robotPosition)
            robotPosition = toMove
        }
        println(direction)
        println(grid)
    }

    return grid.coordinates.filter { grid.get(it) == '[' }
        .sumOf { it.row * 100L + it.col }
}

fun canMove(grid: Grid<Char>, start: GridCoordinate, direction: CardinalDirection): Boolean {
    var pos = start
    if (grid.get(pos) == EMPTY) { return true }
    if (grid.get(pos) == WALL) { return false }
    if (direction == CardinalDirection.LEFT || direction == CardinalDirection.RIGHT)
        return (canMove(grid, pos.getMovement(direction), direction))
    else {
        val otherBoxHalf = getOtherBoxHalf(grid, pos)
        return canMove(grid, otherBoxHalf.getMovement(direction), direction) &&
                canMove(grid, pos.getMovement(direction), direction)
    }
}

fun pushBoxPart2(grid: Grid<Char>, boxHalf: GridCoordinate, direction: CardinalDirection): Boolean {
    if (grid.get(boxHalf) == '.') { return true }
    if (grid.get(boxHalf) == '#') { return false }
    val otherBoxHalf = getOtherBoxHalf(grid, boxHalf)
    val leftHalf = if (grid.get(boxHalf) == '[') boxHalf else otherBoxHalf
    val rightHalf = if (grid.get(boxHalf) == ']') boxHalf else otherBoxHalf
    if (direction == CardinalDirection.RIGHT) {
        val toMove = rightHalf.getMovement(CardinalDirection.RIGHT)
        if (grid.get(toMove) == EMPTY) {
            swap(grid, rightHalf, toMove)
            swap(grid, leftHalf, rightHalf)
            return true
        } else if (grid.get(toMove)  == '[' && canMove(grid, toMove, direction)) {
            pushBoxPart2(grid, toMove, direction)
            swap(grid, rightHalf, toMove)
            swap(grid, leftHalf, rightHalf)
            return true
        }
    } else if (direction == CardinalDirection.LEFT) {
        val toMove = leftHalf.getMovement(CardinalDirection.LEFT)
        if (grid.get(toMove) == EMPTY) {
            swap(grid, leftHalf, toMove)
            swap(grid, rightHalf, leftHalf)
            return true
        } else if (grid.get(toMove) == ']' && canMove(grid, toMove, direction)) {
            pushBoxPart2(grid, toMove, direction)
            swap(grid, leftHalf, toMove)
            swap(grid, rightHalf, leftHalf)
            return true
        }
    } else {
        val toMoveLeft = leftHalf.getMovement(direction)
        val toMoveRight = rightHalf.getMovement(direction)
        if (canMove(grid, toMoveLeft, direction) && canMove(grid, toMoveRight, direction)) {
            pushBoxPart2(grid, toMoveLeft, direction)
            pushBoxPart2(grid, toMoveRight, direction)
            swap(grid, leftHalf, leftHalf.getMovement(direction))
            swap(grid, rightHalf, rightHalf.getMovement(direction))
            return true
        }
    }

    return false
}
