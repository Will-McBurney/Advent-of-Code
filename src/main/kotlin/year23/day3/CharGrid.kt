package year23.day3

class CharGrid(
    val height: Int,
    val width: Int
) {
    private val contents = Array(height) { Array(width) { 0.toChar() } }
    constructor(lines: List<String>): this(lines.size, lines[0].length){
        lines.forEachIndexed { lineNumber, line ->
            line.toCharArray().forEachIndexed {
                columnNumber, char -> contents[lineNumber][columnNumber] = char
            }
        }
    }

    fun get(row: Int, column: Int): Char {
        if (!isInBounds(row, column)) {
            throw IndexOutOfBoundsException("Out of bounds - row: $row - col: $column - size $height x $width")
        }
        return contents[row][column]
    }

    fun set(row: Int, column: Int, char: Char) {
        if (!isInBounds(row, column)) {
            throw IndexOutOfBoundsException("Out of bounds - row: $row - col: $column - size $height x $width")
        }
        contents[row][column] = char
    }

    private fun isInBounds(row: Int, column: Int) = (row in 0..< height) && (column in 0 ..< width)

    fun getNeighborsCoordinates(row: Int, column: Int): List<Pair<Int, Int>> {
        if (!isInBounds(row, column)) {
            throw IndexOutOfBoundsException("Out of bounds - row: $row - col: $column - size $height x $width")
        }
        val neighbors = mutableListOf<Pair<Int, Int>>()
        for (i in row-1..row+1) {
            for (j in column - 1..column + 1) {
                if (i == row && j == column) continue
                if (isInBounds(i, j)) neighbors.add(Pair(i, j))
            }
        }
        return neighbors
    }

}
