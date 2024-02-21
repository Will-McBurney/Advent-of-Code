package year21.day04

private const val BINGO_WIDTH = 5

class BingoCard {
    inner class BingoSquare(
        val number: Int,
        var marked: Boolean = false
    )

    private var squares: List<BingoSquare>
    private var values: Set<Int>
    private var isWinner: Boolean = false //cache for isWinner()
    constructor(lines: List<String>) {
        assert(lines.size == BINGO_WIDTH)
        squares = lines.map { line ->
            line.trim().split("\\s".toRegex())
                .filter { token -> token.isNotEmpty() }
                .map {token ->  BingoSquare(token.toInt()) }
        }.flatten()
        assert(squares.size == 25)
        values = squares.map { square -> square.number }.toSet()
        assert(values.size == 25)
    }

    fun contains(newValue: Int): Boolean = values.contains(newValue)

    fun handleNewValue(newValue: Int) {
        if (!contains(newValue)) {
            return
        }

        squares.single{ square -> square.number == newValue}
            .apply { marked = true }
    }

    /**
     * Returns -1 if no winner.
     */
    fun isWinner(): Boolean {
        if (isWinner) return true
        (0..<BINGO_WIDTH).forEach { i ->
            val row = (0..<BINGO_WIDTH).map { j -> squares[i * BINGO_WIDTH + j] }
            if (row.all { square -> square.marked }) {
                isWinner = true
                return true
            }
            val col = (0 ..<BINGO_WIDTH).map { j -> squares[j * BINGO_WIDTH + i] }
            if (col.all { square -> square.marked }) {
                isWinner = true
                return true
            }
        }
        return false
    }

    fun getWinnerScore(): Int {
        if (!isWinner()) {
            throw IllegalStateException("This Card has not won, cannot get Winner score")
        }
        return getUnmarkedSquareSum()
    }

    private fun getUnmarkedSquareSum() = squares.filter { square -> !square.marked }
        .sumOf { square -> square.number }

    override fun toString(): String {
        return values.toString()
    }
}

