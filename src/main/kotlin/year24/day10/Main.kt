package year24.day10

import AoCResultPrinter
import Grid
import GridCoordinate
import Reader
import SearchNode
import SearchResult
import dfs

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
    val trailResultsList = getTrailResults(grid, trailHeads)

    val part1Result = trailResultsList.sumOf{ it.getPart1Result() }
    printer.endPart1()

    //Do Part 2
    val part2Result = trailResultsList.sumOf{ it.getPart2Result() }
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

data class AscendingGridNode(
    val gridCoordinate: GridCoordinate,
    val grid: Grid<Int>,
): SearchNode<GridCoordinate> {
    override fun getNode(): GridCoordinate {
        return gridCoordinate
    }

    override fun getNext(): List<AscendingGridNode> {
        val coordinateValue = grid.get(gridCoordinate)
        return grid.getCardinalNeighbors(gridCoordinate)
            .filter { grid.get(it) == coordinateValue + 1 }
            .map { AscendingGridNode(it, grid) }
    }

}

const val TARGET_HEIGHT = 9

class TrailResults(
    private val grid: Grid<Int>,
    private val destinationsReached: MutableSet<GridCoordinate> = mutableSetOf(),
    private var destinationCount: Int = 0
): SearchResult<Int> {
    override fun updateResult(node: SearchNode<*>) {
        require(node is AscendingGridNode)
        if (TARGET_HEIGHT == grid.get(node.gridCoordinate)) {
            destinationCount++
            destinationsReached.add(node.gridCoordinate)
        }
    }

    fun getPart1Result() = destinationsReached.size

    fun getPart2Result() = destinationCount
}


fun getTrailResults(
    grid: Grid<Int>,
    trailHeads: List<GridCoordinate>
): List<TrailResults> {
    return trailHeads.map { trailhead ->
        dfs(
            start = setOf(AscendingGridNode(trailhead, grid)),
            initialResult = TrailResults(grid),
            visitOnlyOnce = false
        ) as TrailResults
    }
}
