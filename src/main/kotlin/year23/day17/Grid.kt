package year23.day17

import kotlin.math.max
import kotlin.math.min


class Grid(
    private val grid: List<List<Int>>
) {
    val numRows: Int
        get() = grid.size

    val numCols: Int
        get() = grid[0].size

    private fun getCellValue(coordinate: Coordinate): Int {
        return grid[coordinate.row][coordinate.column]
    }

    private fun isInBounds(coordinate: Coordinate): Boolean {
        return coordinate.row in 0..<numRows && coordinate.column in 0..<numCols
    }

    override fun toString(): String {
        return grid.joinToString("\n") { it.joinToString("") }
    }

    fun getEdges(coordinate: Coordinate, minEdgeLength: Int, maxEdgeLength: Int): Set<Pair<Coordinate, Coordinate>> {
        val row = coordinate.row
        val column = coordinate.column
        return (minEdgeLength..maxEdgeLength).map { step ->
            listOf(
                Pair(coordinate, Coordinate(row + step, column)),
                Pair(coordinate, Coordinate(row - step, column)),
                Pair(coordinate, Coordinate(row, column + step)),
                Pair(coordinate, Coordinate(row, column - step))
            ).filter { isInBounds(it.second) }.toSet()
        }.flatten().toSet()
    }

    fun getWeight(edge: Pair<Coordinate, Coordinate>): Int {
        return (min(edge.first.row, edge.second.row) .. max(edge.first.row, edge.second.row)).sumOf {row ->
            (min(edge.first.column, edge.second.column) .. max(edge.first.column, edge.second.column)).sumOf {col ->
                getCellValue(Coordinate(row, col))
            }
        } - getCellValue(edge.first) //don't count the "starting" point.
    }
}