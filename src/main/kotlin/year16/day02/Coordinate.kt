package year16.day02

data class Coordinate(
    val row: Int,
    val col: Int
) {
    fun move(direction: Direction): Coordinate {
        return Coordinate(row + direction.dRow,col + direction.dCol)
    }
}
