package year23.day21

enum class Direction(val dRow: Int, val dCol: Int) {
    UP(-1, 0),
    LEFT(0, -1),
    DOWN(1, 0),
    RIGHT(0, 1)
}