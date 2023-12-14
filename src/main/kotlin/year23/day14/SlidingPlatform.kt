package year23.day14

class SlidingPlatform(
    val gridState: MutableList<MutableList<Char>>
) {
    override fun toString(): String {
        return "${gridState})"
    }

    fun cycle() {
        slideUp()
        slideLeft()
        slideDown()
        slideRight()
    }

    fun slideUp() {
        gridState.forEachIndexed { rowNumber, row ->
            row.forEachIndexed { columnNumber, char ->
                if (get(rowNumber, columnNumber) == 'O') slideUp(rowNumber, columnNumber)
            }
        }
    }

    fun slideDown() {
        gridState.indices.reversed().forEach { rowNumber ->
            gridState[rowNumber].forEachIndexed { columnNumber, _ ->
                if (get(rowNumber, columnNumber) == 'O') slideDown(rowNumber, columnNumber)
            }
        }
    }

    fun slideRight() {
        gridState[0].indices.reversed().forEach { columnNumber ->
            gridState.forEachIndexed { rowNumber, _ ->
                if (get(rowNumber, columnNumber) == 'O') slideRight(rowNumber, columnNumber)
            }
        }
    }

    fun slideRight(row: Int, column: Int) {
        if (get(row, column) != 'O') {
            return
        }
        var currentRow = row;
        var currentColumn = column;
        while (isInBounds(currentRow, currentColumn + 1) && get(currentRow, currentColumn + 1) == '.') {
            set(currentRow, currentColumn, '.')
            set(currentRow, currentColumn + 1, 'O')
            currentColumn++
        }
    }

    fun slideLeft() {
        gridState[0].indices.forEach { columnNumber ->
            gridState.forEachIndexed { rowNumber, _ ->
                if (get(rowNumber, columnNumber) == 'O') slideLeft(rowNumber, columnNumber)
            }
        }
    }

    fun slideLeft(row: Int, column: Int) {
        if (get(row, column) != 'O') {
            return
        }
        var currentRow = row;
        var currentColumn = column;
        while (isInBounds(currentRow, currentColumn - 1) && get(currentRow, currentColumn - 1) == '.') {
            set(currentRow, currentColumn, '.')
            set(currentRow, currentColumn - 1, 'O')
            currentColumn--
        }
    }

    fun get(row: Int, column: Int): Char {
        return gridState[row][column]
    }

    fun set(row: Int, column: Int, value: Char) {
        gridState[row][column] = value
    }

    fun isInBounds(row: Int, column: Int): Boolean {
        return (row in gridState.indices) && (column in gridState[0].indices)
    }

    fun getLoad(): Long {
        val gridHeight = gridState.size
        return gridState.mapIndexed { rowIndex, row ->
            (row.filter { it == 'O' }
                .size * (gridHeight - rowIndex)).toLong()
        }.sum()
    }

    fun slideUp(row: Int, column: Int) {
        if (get(row, column) != 'O') {
            return
        }
        var currentRow = row;
        var currentColumn = column;
        while (isInBounds(currentRow - 1, currentColumn) && get(currentRow - 1, currentColumn) == '.') {
            set(currentRow, currentColumn, '.')
            set(currentRow - 1, currentColumn, 'O')
            currentRow--
        }
    }

    fun slideDown(row: Int, column: Int) {
        if (get(row, column) != 'O') {
            return
        }
        var currentRow = row;
        var currentColumn = column;
        while (isInBounds(currentRow + 1, currentColumn) && get(currentRow + 1, currentColumn) == '.') {
            set(currentRow, currentColumn, '.')
            set(currentRow + 1, currentColumn, 'O')
            currentRow++
        }
    }


}