package year23.day16

class Beam(
    private var position: Coordinate,
    var direction: Direction
) {
    private val visited: MutableList<Coordinate> = mutableListOf(position)

    fun getPosition(): Coordinate {
        return position
    }

    fun stopBeam() {
        direction = Direction.NONE
    }

    private fun setPosition(newPosition: Coordinate) {
        this.position = newPosition
        visited.add(newPosition)
    }

    fun getVisited(): List<Coordinate> {
        return visited
    }

    fun updateDirection(mirrorCell: Char) {
        direction = when (mirrorCell) {
            '/' -> when (direction) {
                Direction.UP -> Direction.RIGHT
                Direction.LEFT -> Direction.DOWN
                Direction.DOWN -> Direction.LEFT
                Direction.RIGHT -> Direction.UP
                Direction.NONE -> Direction.NONE
            }

            '\\' -> when (direction) {
                Direction.UP -> Direction.LEFT
                Direction.LEFT -> Direction.UP
                Direction.DOWN -> Direction.RIGHT
                Direction.RIGHT -> Direction.DOWN
                Direction.NONE -> Direction.NONE
            }

            else -> direction
        }
    }

    fun isSplitBeam(mirrorCell: Char): Boolean {
        return when (mirrorCell) {
            '-' -> when (direction) {
                Direction.UP -> true
                Direction.DOWN -> true
                else -> false
            }

            '|' -> when (direction) {
                Direction.LEFT -> true
                Direction.RIGHT -> true
                else -> false
            }
            else -> false
        }
    }

    fun advance() {
        setPosition(Coordinate(position.row + direction.dRow, position.column + direction.dColumn))
        visited.add(position)
    }
}