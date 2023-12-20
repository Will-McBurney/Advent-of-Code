package year16.day02
class NumberPad: EntryPad {
    private val entryPad = listOf(
        listOf('1','2','3'),
        listOf('4','5','6'),
        listOf('7','8','9')
    )

    private var currentLocation = Coordinate(1, 1)

    private fun isInBounds(coordinate: Coordinate):Boolean {
        return coordinate.row in entryPad.indices && coordinate.col in entryPad[0].indices
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