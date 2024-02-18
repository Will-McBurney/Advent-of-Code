package year22.day08

import AoCResultPrinter
import CardinalDirection
import Grid
import GridCoordinate
import Reader

const val year: Int = 22
const val day: Int = 8

fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
    val treeGrid = getTreeGrid(lines)
    printer.endSetup()

    //Do Part 1
    setTreeGridVisibility(treeGrid)
    val part1Result = getPart1Result(treeGrid)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(treeGrid)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

private fun setTreeGridVisibility(treeGrid: Grid<Tree>) {
    CardinalDirection.entries.forEach { direction ->
        setVisibility(treeGrid, direction)
    }
}

data class Tree(
    val height: Int,
    var isVisible: Boolean = false
)

fun getTreeGrid(lines: List<String>): Grid<Tree> {
    return Grid(
        lines.map { row ->
            row.map { char -> Tree(charToInt(char)) }
                .toMutableList()
        }.toMutableList()
    )
}

fun charToInt(char: Char): Int {
    return char.code - '0'.code
}

fun getPart1Result(treeGrid: Grid<Tree>): Int {
    return treeGrid.count { tree -> tree.isVisible }
}

fun setVisibility(treeGrid: Grid<Tree>, direction: CardinalDirection) {
    var rowRange = treeGrid.rowIndices
    var colRange = treeGrid.colIndices
    when (direction) {
        CardinalDirection.UP -> rowRange = IntRange(treeGrid.rowIndices.last, treeGrid.rowIndices.last)
        CardinalDirection.LEFT -> colRange = IntRange(treeGrid.colIndices.last, treeGrid.colIndices.last)
        CardinalDirection.DOWN -> rowRange = IntRange(0, 0)
        CardinalDirection.RIGHT -> colRange = IntRange(0, 0)
    }

    rowRange.forEach { rowIndex ->
        colRange.forEach { colIndex ->
            var lastHeight = -1
            var coordinate = GridCoordinate(rowIndex, colIndex)
            do {
                val currentTree = treeGrid.get(coordinate)
                if (currentTree.height > lastHeight) {
                    currentTree.isVisible = true
                    lastHeight = currentTree.height
                }
                coordinate = coordinate.getMovement(direction)
            } while (treeGrid.isInBounds(coordinate))
        }
    }
}

fun getPart2Result(treeGrid: Grid<Tree>): Int =
    treeGrid.coordinates.maxOf { coordinate -> getScenicScore(treeGrid, coordinate) }

fun getScenicScore(treeGrid: Grid<Tree>, coordinate: GridCoordinate): Int {
    val scenicScore = CardinalDirection.entries.map { direction ->
        return@map getVisibleTrees(coordinate, direction, treeGrid)
    }.reduce(Int::times)

    return scenicScore
}

private fun getVisibleTrees(
    startingCoordinate: GridCoordinate,
    direction: CardinalDirection,
    treeGrid: Grid<Tree>
): Int {
    val treeHeight = treeGrid.get(startingCoordinate).height
    var visibleTrees = 0
    var currentCoordinate = startingCoordinate.getMovement(direction)
    while (treeGrid.isInBounds(currentCoordinate)) {
        visibleTrees++
        if (treeGrid.get(currentCoordinate).height >= treeHeight) {
            return visibleTrees
        }
        currentCoordinate = currentCoordinate.getMovement(direction)
    }
    return visibleTrees
}
