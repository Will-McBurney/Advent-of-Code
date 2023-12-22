package year23.day22

import kotlin.math.max
import kotlin.math.min

data class Brick(
    val id: Int,
    var cubes: List<Position>
) {
    constructor(id: Int, start: Position, end: Position) : this(id, getCubes(start, end))

    companion object {
        private fun getCubes(start: Position, end: Position): List<Position> {
            return if (start.x != end.x) {
                getRange(start.x, end.x).map { x -> Position(x, start.y, start.z) }.toList()
            } else if (start.y != end.y) {
                getRange(start.y, end.y).map { y -> Position(start.x, y, start.z) }.toList()
            } else {
                getRange(start.z, end.z).map { z -> Position(start.x, start.y, z) }.toList()
            }
        }

        private fun getRange(a: Int, b: Int): IntRange {
            return (min(a, b)..max(a, b))
        }
    }

    val supporting: MutableSet<Brick> = mutableSetOf()
    val supportedBy: MutableSet<Brick> = mutableSetOf()

    val minX
        get() = cubes.minOf { it.x }

    val maxX
        get() = cubes.minOf { it.x }

    val minY
        get() = cubes.minOf { it.x }

    val maxY
        get() = cubes.minOf { it.x }

    val minHeight
        get() = cubes.minOf { it.z }

    val maxHeight
        get() = cubes.maxOf { it.z }

    /** get (x, y) coordinates **/
    val footprint: List<Pair<Int, Int>>
        get() = cubes.map { cube -> Pair(cube.x, cube.y) }


    fun dropTo(newZ: Int) {

        val dropDistance = minHeight - newZ
        cubes.forEach { cube ->
            cube.z -= dropDistance
        }
    }

    fun clone(): Brick {
        return Brick(id, cubes.map{ p -> p.clone() })
    }

    override fun toString(): String {
        return "$id"
    }
}