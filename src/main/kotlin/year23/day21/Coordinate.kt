package year23.day21

data class Coordinate(val row: Int, val column: Int) {
    fun isEvenParity(): Boolean {
        return (row + column) % 2 == 0
    }
}