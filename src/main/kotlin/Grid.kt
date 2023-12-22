class Grid<E>(
    val grid: MutableList<MutableList<E>>
) {
    val rows: Int
        get() = grid.size

    val cols: Int
        get() = grid[0].size

    fun get(coordinate: GridCoordinate): E = grid[coordinate.row][coordinate.col]

    fun set(coordinate: GridCoordinate, value: E) {
        grid[coordinate.row][coordinate.col] = value
    }

    fun isInBounds(coordinate: GridCoordinate): Boolean {
        return coordinate.row in 0 ..< rows && coordinate.col in 0 ..< cols
    }

    fun getCardinalNeighbors(coordinate: GridCoordinate):List<GridCoordinate> {
        return CardinalDirection.entries.map { d -> coordinate.getMovement(d) }
            .filter { c ->  isInBounds(c) }
    }

    override fun toString(): String {
        return grid.joinToString("\n") { row -> row.joinToString("") }
    }
}