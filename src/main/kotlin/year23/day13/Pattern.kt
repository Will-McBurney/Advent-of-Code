package year23.day13

class Pattern(
    val grid: List<String>
) {

    fun findVerticalReflection(): Int {
        for (row in 0..< grid.size - 1) {
            if (grid[row] == grid[row + 1]) {
                var match = true
                var lowRow = row - 1
                var highRow = row + 2
                while (lowRow >= 0 && highRow < grid.size) {
                    if (grid[lowRow] != grid[highRow]) {
                        match = false
                        break
                    }
                    lowRow--
                    highRow++
                }
                if (match) return row + 1
            }
        }
        return -1
    }

    private fun getColumn(index: Int): String {
        return grid.map { it[index] }.joinToString("")
    }
    fun findHorizontalReflection(): Int {
        for (column in 0..< grid[0].length - 1) {
            if (getColumn(column) == getColumn(column+1)) {
                var match = true
                var lowColumn = column - 1
                var highColumn = column + 2
                while (lowColumn >= 0 && highColumn < grid[0].length) {
                    if (getColumn(lowColumn) != getColumn(highColumn)) {
                        match = false
                        break
                    }
                    lowColumn--
                    highColumn++
                }
                if (match) return column + 1
            }
        }
        return -1
    }

    override fun toString(): String {
        return "Pattern(grid=${grid.joinToString("\n") { it.toString() }})"
    }


}