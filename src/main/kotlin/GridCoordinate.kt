import kotlin.math.abs

data class GridCoordinate(
    val row: Int,
    val col: Int
) {
    fun getMovement(direction: CardinalDirection): GridCoordinate {
        return GridCoordinate(row + direction.dRow, col + direction.dCol)
    }

    fun getMovement(direction: OrdinalDirection): GridCoordinate {
        return GridCoordinate(row + direction.dRow, col + direction.dCol)
    }

    fun getCardinalNeighbors(): List<GridCoordinate> {
        return CardinalDirection.entries.map { d -> this.getMovement(d) }
    }

    fun getOrdinalNeighbors(): List<GridCoordinate> {
        return OrdinalDirection.entries.map { d -> this.getMovement(d) }
    }

    fun manhattanDistanceTo(other: GridCoordinate) = abs(row - other.row) + abs(col - other.col)
}
