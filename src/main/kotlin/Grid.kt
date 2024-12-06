class Grid<E>(
    val grid: MutableList<MutableList<E>>
) : Iterable<E> {
    val numRows: Int
        get() = grid.size

    val rowIndices: IntRange
        get() = IntRange(0, numRows - 1)

    val numCols: Int
        get() = grid[0].size

    val colIndices: IntRange
        get() = IntRange(0, numCols - 1)

    val coordinates: List<GridCoordinate>
        get() = rowIndices.map { rowIndex ->
            colIndices.map { colIndex ->
                GridCoordinate(rowIndex, colIndex)
            }
        }.flatten()

    fun get(coordinate: GridCoordinate): E = grid[coordinate.row][coordinate.col]

    fun set(coordinate: GridCoordinate, value: E) {
        grid[coordinate.row][coordinate.col] = value
    }

    fun isInBounds(coordinate: GridCoordinate): Boolean {
        return coordinate.row in 0..<numRows && coordinate.col in 0..<numCols
    }

    fun getCardinalNeighbors(coordinate: GridCoordinate): List<GridCoordinate> {
        return coordinate.getCardinalNeighbors()
            .filter { c -> isInBounds(c) }
    }

    fun getOrdinalNeighbors(coordinate: GridCoordinate): List<GridCoordinate> {
        return coordinate.getOrdinalNeighbors()
            .filter { c -> isInBounds(c) }
    }

    fun copy(): Grid<E> {
        return Grid(grid.map{it.toMutableList()}.toMutableList())
    }

    override fun iterator(): Iterator<E> {
        return grid.flatten().iterator()
    }

    override fun toString(): String {
        return grid.joinToString("\n") { row -> row.joinToString("") }
    }
}