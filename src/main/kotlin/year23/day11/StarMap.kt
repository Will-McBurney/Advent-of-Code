package year23.day11

import kotlin.math.max
import kotlin.math.min

class StarMap(
    private val grid: List<List<Char>>,
    private val expansionFactor: Int
) {
    private val emptyRows: List<Int> = getEmptyRows()
    private val emptyColumns: List<Int> = getEmptyColumns()
    private val galaxyCoordinates: List<Coordinate> = getGalaxyPositions(grid)

    private fun getGalaxyPositions(preGrid: List<List<Char>>): List<Coordinate> {
        val galaxyCoordinates = mutableListOf<Coordinate>()
        preGrid.forEachIndexed { index, row ->
            row.forEachIndexed { rowIndex: Int, char: Char ->
                if (char == '#') galaxyCoordinates.add(Coordinate(rowIndex, index))
            }
        }
        return galaxyCoordinates
    }

    private fun getEmptyRows(): List<Int> {
        return grid.indices.filter { index -> !grid[index].contains('#') }
            .toList()
    }

    private fun getEmptyColumns(): List<Int> {
        return grid[0].indices.filter { isColumnEmpty(it) }
            .toList()
    }

    private fun isColumnEmpty(columnIndex: Int): Boolean {
        return grid.all { row -> row[columnIndex] == '.' }
    }

    fun getSumOfShortestDistance(): Long {
        return galaxyCoordinates.map { a ->
            galaxyCoordinates.filter { it != a }
                .sumOf { getDistance(a, it) }
        }.sum() / 2
    }

    private fun getDistance(a: Coordinate, b: Coordinate): Long {
        val minX = min(a.x, b.x)
        val maxX = max(a.x, b.x)
        val xDistance = (minX + 1..maxX).sumOf { rowIndex ->
            if (emptyColumns.contains(rowIndex)) expansionFactor.toLong() else 1L
        }

        val minY = min(a.y, b.y)
        val maxY = max(a.y, b.y)
        val yDistance = (minY + 1..maxY).sumOf { columnIndex ->
            if (emptyRows.contains(columnIndex)) expansionFactor.toLong() else 1L
        }
        return xDistance + yDistance
    }


    override fun toString(): String {
        return grid.joinToString("\n") { it.joinToString("") }
    }
}