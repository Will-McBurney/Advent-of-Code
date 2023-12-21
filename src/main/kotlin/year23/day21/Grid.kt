package year23.day21


class Grid(
    private val grid: List<List<Char>>
) {
    val numRows: Int
        get() = grid.size

    val numCols: Int
        get() = grid[0].size

    var startingPosition: Coordinate

    init {
        val startingRowIndex = grid.indexOfFirst { row -> row.contains('S') }
        val startingColumnIndex = grid[startingRowIndex].indexOf('S')
        startingPosition = Coordinate(startingRowIndex, startingColumnIndex)
    }

    private fun getCellValue(coordinate: Coordinate): Char {
        return grid[coordinate.row][coordinate.column]
    }

    private fun isInBounds(coordinate: Coordinate): Boolean {
        return coordinate.row in 0..<numRows && coordinate.column in 0..<numCols
    }

    fun getEmptyNeighbors(coordinate: Coordinate): List<Coordinate> {
        return Direction.entries.map {d: Direction ->
            Coordinate(coordinate.row + d.dRow, coordinate.column + d.dCol)
        }
            .filter { c: Coordinate -> isInBounds(c) }
            .filterNot { c: Coordinate ->  getCellValue(c) == '#'}
    }

    override fun toString(): String {
        return grid.joinToString("\n") { it.joinToString("") }
    }
}