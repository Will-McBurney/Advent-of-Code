package year15.day18

class Grid(
    private var grid: List<List<Char>>
) {
    fun get(coordinate: Coordinate): Char {
        return grid[coordinate.row][coordinate.column]
    }

    fun isCorner(coordinate: Coordinate): Boolean {
        return coordinate.row == 0 && coordinate.column == 0 ||
                coordinate.row == 0 && coordinate.column == grid[0].size - 1 ||
                coordinate.row == grid.size - 1 && coordinate.column == 0 ||
                coordinate.row == grid.size - 1 && coordinate.column == grid[0].size - 1
    }

    private fun isOn(coordinate: Coordinate): Boolean {
        if (isCorner(coordinate)) {
            return true
        }
        if (!isInBounds(coordinate)) {
            return false
        }
        return get(coordinate) == '#'
    }

    fun isInBounds(coordinate: Coordinate): Boolean {
        return coordinate.row in grid.indices && coordinate.column in grid[0].indices
    }

    private fun getNeighbors(coordinate: Coordinate): List<Coordinate> {
        return listOf(
            Coordinate(coordinate.row - 1, coordinate.column - 1),
            Coordinate(coordinate.row - 1, coordinate.column),
            Coordinate(coordinate.row - 1, coordinate.column + 1),
            Coordinate(coordinate.row, coordinate.column - 1),
            Coordinate(coordinate.row, coordinate.column + 1),
            Coordinate(coordinate.row + 1, coordinate.column - 1),
            Coordinate(coordinate.row + 1, coordinate.column),
            Coordinate(coordinate.row + 1, coordinate.column + 1)
        )
    }

    fun advance() {
        val newGridList: MutableList<MutableList<Char>> = mutableListOf()
        grid.indices.forEach() { rowIndex ->
            val newRow = mutableListOf<Char>()
            grid[0].indices.forEach {columnIndex ->
                val coordinate = Coordinate(rowIndex, columnIndex)
                if (isCorner(coordinate)) {
                    newRow.add('#')
                    return@forEach
                }
                val neighborsOn = getNeighbors(coordinate).count{ isOn(it) }
                if (neighborsOn == 3) {
                    newRow.add('#')
                } else if (isOn(coordinate) && neighborsOn == 2) {
                    newRow.add('#')
                } else {
                    newRow.add('.')
                }
            }
            newGridList.add(newRow)
        }
        grid = newGridList
    }

    fun getOnCount(): Int {
        return grid.flatten()
            .count{ it == '#'}
    }

    override fun toString(): String {
        return grid.joinToString("\n") { it.joinToString("") }
    }
}