package year23.day3

data class SpecNumber(
    val lineNumber: Int,
    val startingIndex: Int,
    val endingIndex: Int,
    val value: Int
) {
    fun containsIndex(row: Int, column: Int): Boolean {
        return lineNumber == row && startingIndex <= column && column <= endingIndex
    }
}