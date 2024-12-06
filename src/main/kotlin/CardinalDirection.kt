enum class CardinalDirection(val dRow: Int, val dCol: Int) {
    UP(-1, 0),
    LEFT(0, -1),
    DOWN(1, 0),
    RIGHT(0, 1);

    val opposite: CardinalDirection
        get() = when(this) {
            UP -> DOWN
            LEFT -> RIGHT
            DOWN -> UP
            RIGHT -> LEFT
        }

    val clockwise: CardinalDirection
        get() = when(this) {
            UP -> RIGHT
            RIGHT -> DOWN
            DOWN -> LEFT
            LEFT -> UP
        }

    val counterClockwise: CardinalDirection
        get() = when(this) {
            UP -> LEFT
            RIGHT -> UP
            DOWN -> RIGHT
            LEFT -> DOWN
        }
}