package year23.day11

import kotlin.math.max
import kotlin.math.min

class GalaxyMap(
    private val grid: List<List<Char>>,
    private val expansionFactor: Int
) {
    private val emptyRows: List<Int> = getEmptyRows(grid)
    private val emptyColumns: List<Int> = getEmptyColumns(grid)
    private val galaxyCoordinates: List<Coordinate> = getGalaxyPositions(grid)

    private fun getGalaxyPositions(grid: List<List<Char>>): List<Coordinate> {
        val galaxyCoordinates = mutableListOf<Coordinate>()
        grid.forEachIndexed { index, row ->
            row.forEachIndexed { rowIndex: Int, char: Char ->
                if (char == '#') galaxyCoordinates.add(Coordinate(rowIndex, index))
            }
        }
        return galaxyCoordinates
    }

    private fun getEmptyRows(grid: List<List<Char>>): List<Int> {
        return grid.indices.filter { index -> grid[index].contains('#') }
            .toList()
    }

    private fun getEmptyColumns(grid: List<List<Char>>): List<Int> {
        return grid[0].indices.filter { isColumnEmpty(grid, it) }
            .toList()
    }

    private fun isColumnEmpty(grid: List<List<Char>>, columnIndex: Int): Boolean {
        return grid.all { row -> row[columnIndex] == '.' }
    }

    fun getSumOfShortestDistance(): Long {
        return galaxyCoordinates.indices.sumOf { a ->
            (a + 1 ..< galaxyCoordinates.size )
                .sumOf { getDistance(galaxyCoordinates[a], galaxyCoordinates[it]) }
        }
    }

    private fun getDistance(a: Coordinate, b: Coordinate): Long {
        return  getLinearDistance(a.x, b.x, emptyColumns) +
                getLinearDistance(a.y, b.y, emptyRows)

    }

    private fun getLinearDistance(start: Int, end: Int, emptyList: List<Int>): Long {
        val minIndex = min(start, end)
        val maxIndex = max(start, end)
        return (minIndex + 1..maxIndex).sumOf { columnIndex ->
            if (emptyList.contains(columnIndex)) expansionFactor.toLong() else 1L
        }
    }


    override fun toString(): String {
        return grid.joinToString("\n") { it.joinToString("") }
    }
}