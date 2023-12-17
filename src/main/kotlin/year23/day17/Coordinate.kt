package year23.day17

import year23.day16.Direction

data class Coordinate(val row: Int, val column: Int) {
    fun getDirectionTo(coordinate: Coordinate): Direction {
        val dRow = coordinate.row - row
        val dCol = coordinate.column - column
        if (dRow <= -1 && dCol ==  0) return Direction.UP
        if (dRow >=  1 && dCol ==  0) return Direction.DOWN
        if (dRow ==  0 && dCol <= -1) return Direction.LEFT
        if (dRow ==  0 && dCol >=  1) return Direction.RIGHT
        throw IllegalArgumentException("Not Adjacent: ($row, $column) - $coordinate")
    }

    fun getPathCoordinates(coordinate: Coordinate): List<Coordinate> {
        var dRow = 0
        if (this.row > coordinate.row) { dRow = -1 }
        if (this.row < coordinate.row) { dRow = 1 }
        var dCol = 0
        if (this.column > coordinate.column) { dCol = -1 }
        if (this.column < coordinate.column) { dCol = 1 }
        val outList: MutableList<Coordinate> = mutableListOf()
        var r = this.row
        var c = this.column
        while (!(coordinate.row == r && coordinate.column == c)) {
            r += dRow
            c += dCol
            outList.add(Coordinate(r, c))
        }
        return outList
    }
}