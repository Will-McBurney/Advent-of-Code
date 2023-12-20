package year16.day02

class DiamondPad: EntryPad {

    private val entryPad: List<List<Char>> = listOf(
        listOf('.', '.', '1', '.', '.'),
        listOf('.', '2', '3', '4', '.'),
        listOf('5', '6', '7', '8', '9'),
        listOf('.', 'A', 'B', 'C', '.'),
        listOf('.', '.', 'D', '.', '.')
    )

    private var currentLocation = Coordinate(2, 0)

    fun isInBounds(coordinate: Coordinate): Boolean {
        return coordinate.row in entryPad.indices &&
                coordinate.col in entryPad[0].indices &&
                entryPad[coordinate.row][coordinate.col] != '.'
    }

    override fun getNextEntry(directions: List<Direction>): Char {
        directions.forEach { direction ->
            val nextLocation = currentLocation.move(direction)
            if (isInBounds(nextLocation)) {
                currentLocation = nextLocation
            }
        }
        return entryPad[currentLocation.row][currentLocation.col]
    }

}