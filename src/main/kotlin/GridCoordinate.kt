data class GridCoordinate(
    val row: Int,
    val col: Int
) {
    fun getMovement(direction: CardinalDirection): GridCoordinate {
        return GridCoordinate(row + direction.dRow, col + direction.dCol)
    }
}
