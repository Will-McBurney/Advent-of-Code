package year15.day03

class Santa(
    private var xPosition: Int = 0,
    private var yPosition: Int = 0
) {
    private val history: HashSet<Coordinate> = hashSetOf(Coordinate(xPosition, yPosition))

    fun move(move: Char) {
        when(move) {
            '^' -> yPosition++
            '>' -> xPosition++
            'v' -> yPosition--
            '<' -> xPosition--
            else -> return
        }
        history.add(getPosition())
    }

    fun getPosition() = Coordinate(xPosition, yPosition)

    fun getHousesVisited() = history.size
}