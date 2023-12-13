package year23.day13

class Pattern(
    val grid: List<String>
) {

    fun findHorizontalReflection(): Int {
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



    fun findVerticalReflection(): Int {
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

    fun exactlyOneDifference(a: String, b: String): Boolean {
        if (a.length != b.length) {
            throw IllegalArgumentException()
        }
        var count = 0
        for (index in a.indices) {
            if (a[index] != b[index]) {
                count++
                if (count > 1) {
                    return false
                }
            }
        }
        return count == 1
    }

    fun findHorizontalReflectionSplitOnSmudge(): Int {
        for (row in 0..< grid.size - 1) {
            if (exactlyOneDifference(grid[row], grid[row + 1])) {
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

    fun findVerticalReflectionSplitOnSmudge(): Int {
        for (column in 0..< grid[0].length - 1) {
            if (exactlyOneDifference(getColumn(column), getColumn(column + 1))) {
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

    fun findHorizontalReflectionSplitOffSmudge(): Int {
        for (row in 0..< grid.size - 1) {
            if (grid[row] == grid[row + 1]) {
                var match = true
                var oneDiffFound = false
                var lowRow = row - 1
                var highRow = row + 2
                while (lowRow >= 0 && highRow < grid.size) {
                    if (exactlyOneDifference(grid[lowRow], grid[highRow])) {
                        if (oneDiffFound) {
                            break
                        }
                        oneDiffFound = true
                    }
                    else if (grid[lowRow] != grid[highRow]) {
                        match = false
                        break
                    }
                    lowRow--
                    highRow++
                }
                if (match && oneDiffFound) return row + 1
            }
        }
        return -1
    }

    fun findVerticalReflectionSplitOffSmudge(): Int {
        for (column in 0..< grid[0].length - 1) {
            if (getColumn(column) == getColumn(column+1)) {
                var match = true
                var oneDiffFound = false
                var lowColumn = column - 1
                var highColumn = column + 2
                while (lowColumn >= 0 && highColumn < grid[0].length) {
                    if (exactlyOneDifference(getColumn(lowColumn), getColumn(highColumn))) {
                        if (oneDiffFound) {
                            break
                        }
                        oneDiffFound = true
                    }
                    else if (getColumn(lowColumn) != getColumn(highColumn)) {
                        match = false
                        break
                    }
                    lowColumn--
                    highColumn++
                }
                if (match && oneDiffFound) return column + 1
            }
        }
        return -1
    }

    override fun toString(): String {
        return "Pattern(grid=\n${grid.joinToString("\n") { it.toString() }})"
    }




}