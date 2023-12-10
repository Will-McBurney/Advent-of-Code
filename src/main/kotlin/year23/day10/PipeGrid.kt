package year23.day10

class PipeGrid(
    private val charGrid: MutableList<MutableList<Char>>
) {
    private val width = charGrid[0].size
    private val height = charGrid.size

    val pipeMap: Map<Char, Set<Direction>> = mapOf(
        '|' to setOf(Direction.UP, Direction.DOWN),
        '-' to setOf(Direction.LEFT, Direction.RIGHT),
        'L' to setOf(Direction.UP, Direction.RIGHT),
        'J' to setOf(Direction.UP, Direction.LEFT),
        '7' to setOf(Direction.DOWN, Direction.LEFT),
        'F' to setOf(Direction.DOWN, Direction.RIGHT),
        'S' to setOf(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT),
        '.' to setOf(),
        'O' to setOf(),
        'I' to setOf()
    )

    val pipeMapPart2: Map<Char, Set<Direction>> = mapOf(
        '|' to setOf(Direction.UP, Direction.DOWN),
        '-' to setOf(Direction.LEFT, Direction.RIGHT),
        'L' to setOf(Direction.DOWN, Direction.LEFT),
        'J' to setOf(Direction.DOWN, Direction.RIGHT),
        '7' to setOf(Direction.UP, Direction.RIGHT),
        'F' to setOf(Direction.UP, Direction.LEFT),
        'S' to setOf(),
        '.' to setOf(),
        'O' to setOf(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT),
    )

    val corners = "LJ7F"

    fun getCharAt(x: Int, y: Int): Char {
        return charGrid[x][y]
    }

    fun getStartingPoint(): Pair<Int, Int> {
        for (i in 0..<height) {
            for (j in 0..<width) {
                if (getCharAt(i, j) == 'S') {
                    return Pair(i, j)
                }
            }
        }
        throw IllegalStateException("Starting point not found")
    }

    fun getLoop(): Loop {
        val startingPoint = getStartingPoint()
        val loop = Loop(startingPoint.first, startingPoint.second)
        var currentX = startingPoint.first
        var currentY = startingPoint.second
        var currentChar = getCharAt(currentX, currentY)
        var nextDirection = Direction.entries.first { canMove(currentX, currentY, it) }

        val firstDirection = nextDirection

        do {
            currentX += nextDirection.dx
            currentY += nextDirection.dy

            loop.add(Pair(currentX, currentY))
            if (startingPoint == Pair(currentX, currentY)) {
                break
            }

            currentChar = getCharAt(currentX, currentY)
            val oppositeDirect = getOppositeDirect(nextDirection)
            nextDirection = pipeMap[currentChar]!!.first { it != oppositeDirect }
        } while (true)

        val lastDirection = getOppositeDirect(nextDirection)

        val targetSet = setOf(firstDirection, lastDirection)
        println(targetSet)

        val localPipeMap = mapOf(
            '|' to setOf(Direction.UP, Direction.DOWN),
            '-' to setOf(Direction.LEFT, Direction.RIGHT),
            'L' to setOf(Direction.UP, Direction.RIGHT),
            'J' to setOf(Direction.UP, Direction.LEFT),
            '7' to setOf(Direction.DOWN, Direction.LEFT),
            'F' to setOf(Direction.DOWN, Direction.RIGHT)
        )

        charGrid[startingPoint.first][startingPoint.second] = localPipeMap.keys.first { localPipeMap[it] == targetSet }
        println(this)

        return loop
    }

    private fun isInBounds(x: Int, y: Int): Boolean {
        return (x in 0..<height && y in 0..<width)
    }

    fun getNeighborCoordinates(x: Int, y: Int): List<Pair<Int, Int>> {
        return listOf(
            Pair(x, y + 1),
            Pair(x, y - 1),
            Pair(x - 1, y),
            Pair(x + 1, y)
        ).filter { isInBounds(it.first, it.second) }
    }

    fun canMove(x1: Int, y1: Int, direction: Direction): Boolean {
        if (!isInBounds(x1, y1)) {
            return false
        }
        if (!isValidDirection(x1, y1, direction)) return false
        val (x2, y2) = getNextCoordinates(x1, y1, direction)
        if (!isInBounds(x2, y2)) {
            return false
        }
        val oppositeDirection = getOppositeDirect(direction)
        return isValidDirection(x2, y2, oppositeDirection)
    }

    private fun getNextCoordinates(x1: Int, y1: Int, direction: Direction): Pair<Int, Int> {
        val x2 = x1 + direction.dx
        val y2 = y1 + direction.dy
        return Pair(x2, y2)
    }

    private fun getOppositeDirect(direction: Direction): Direction {
        return when (direction) {
            Direction.UP -> Direction.DOWN
            Direction.DOWN -> Direction.UP
            Direction.LEFT -> Direction.RIGHT
            Direction.RIGHT -> Direction.LEFT
        }
    }

    private fun isValidDirection(x1: Int, y1: Int, direction: Direction): Boolean {
        val x1y1Char = getCharAt(x1, y1)
        val possibleDirections = pipeMap[x1y1Char]!!
        return possibleDirections.contains(direction)
    }


    fun getNumEnclosedSpaces(loop: Loop): Int {
        var count = 0
        val targets = "LJ|"
        for (x in 0..<height) {
            for (y in 0..<width) {
                if (loop.contains(Pair(x, y))) {
                    continue
                }
                var cY = y
                var parity = false
                var direction = Direction.LEFT
                while (cY > 0) {
                    val next = getNextCoordinates(x, cY, direction)
                    cY = next.second
                    if (targets.contains(getCharAt(x, cY)) && loop.contains(Pair(x, cY))) {
                        parity = !parity
                    }
                }
                if (parity) {
                    charGrid[x][y] = 'I'
                    count++
                }
            }
        }
        println(this)
        return count

    }

fun getEmptyNeighbors(coordinate: Pair<Int, Int>): List<Pair<Int, Int>> {
    return getNeighborCoordinates(coordinate.first, coordinate.second)
        .filter { isInBounds(it.first, it.second) }
        .filter { getCharAt(it.first, it.second) == '.' }
}

fun getStringWithStar(coordinate: Pair<Int, Int>): String {
    val temp = charGrid[coordinate.first][coordinate.second]
    charGrid[coordinate.first][coordinate.second] = 'X'
    val out = this.toString()
    charGrid[coordinate.first][coordinate.second] = temp
    return out
}

override fun toString(): String {
    return charGrid.joinToString("\n") { it.joinToString("") }
}



}