package year23.day18

data class Command(val direction: Direction, val distance: Long, val colorString: String) {
    fun getPart2Distance(): Long = colorString.substring(1, 6).toLong(16)
    fun getPart2Direction(): Direction {
        return when(colorString[6]) {
            '0' -> Direction.RIGHT
            '1' -> Direction.DOWN
            '2' -> Direction.LEFT
            '3' -> Direction.UP
            else -> throw IllegalArgumentException("bad color string - $colorString - invalid direction ${colorString[5]}")
        }
    }
}