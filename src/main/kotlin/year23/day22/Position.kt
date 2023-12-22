package year23.day22

class Position (
    var x: Int,
    var y: Int,
    var z: Int
) {
    fun clone(): Position {
        return Position(x, y, z)
    }
    override fun toString(): String {
        return "Position(x=$x, y=$y, z=$z)"
    }
}