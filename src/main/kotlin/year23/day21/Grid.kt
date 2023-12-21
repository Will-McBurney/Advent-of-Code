package year23.day21


class Grid(
    private val grid: List<List<Char>>
) {
    val numRows: Int
        get() = grid.size

    val numCols: Int
        get() = grid[0].size

    var startingPosition: Coordinate

    val distanceMap: MutableMap<Coordinate, Int> = mutableMapOf()

    init {
        val startingRowIndex = grid.indexOfFirst { row -> row.contains('S') }
        val startingColumnIndex = grid[startingRowIndex].indexOf('S')
        startingPosition = Coordinate(startingRowIndex, startingColumnIndex)
        distanceMap[startingPosition] = 0
    }

    fun getEmptyRows(): List<Int> {
        return grid.indices.filterNot {rowIndex -> grid[rowIndex].contains('#')}

    }

    fun getEmptyCols(): List<Int> {
        return grid[0].indices.filterNot {
            colIndex: Int -> grid.any {
                row: List<Char> -> row[colIndex] == '#'
            }
        }
    }

    private fun getCellValue(coordinate: Coordinate): Char {
        return grid[positiveMod(coordinate.row, numRows)][positiveMod(coordinate.column, numCols)]
    }

    private fun positiveMod(number: Int, divisor: Int): Int {
        var output = number % divisor
        if (output < 0) {
            output += divisor
        }
        return output
    }

    private fun isInBounds(coordinate: Coordinate): Boolean {
        return coordinate.row in 0..<numRows && coordinate.column in 0..<numCols
    }

    fun getEmptyNeighbors(coordinate: Coordinate): List<Coordinate> {
        return Direction.entries.map {
            d: Direction -> Coordinate(coordinate.row + d.dRow, coordinate.column + d.dCol)
        }
            .filter { c: Coordinate -> isInBounds(c) }
            .filterNot { c: Coordinate ->  getCellValue(c) == '#'}
    }

    override fun toString(): String {
        return grid.joinToString("\n") { it.joinToString("") }
    }
}