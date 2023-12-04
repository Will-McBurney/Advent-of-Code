package year23.day3

data class SpecNumber(
    val lineNumber: Int,
    val startingIndex: Int,
    val endingIndex: Int,
    val value: Int,
) {
    fun containsIndex(row: Int, column: Int): Boolean {
        return lineNumber == row && startingIndex <= column && column <= endingIndex
    }

    private fun getCoordinates(): Set<Pair<Int, Int>> {
        return (startingIndex .. endingIndex).map {
            Pair(lineNumber, it)
        }.toSet()
    }

    fun getNeighbors(grid: CharGrid): Set<Pair<Int, Int>> {
        val coordinates = getCoordinates()
        return coordinates.asSequence()
            .map { grid.getNeighborsCoordinates(it.first, it.second) }
            .flatten()
            .filterNot(coordinates::contains)
            .toSet()
    }
}