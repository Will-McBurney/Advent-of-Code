package year23.day16

enum class Direction(
    val dRow: Int,
    val dColumn: Int
) {
    UP(-1, 0),
    LEFT(0, -1),
    DOWN(1, 0),
    RIGHT(0, 1),
    NONE(0, 0)
}