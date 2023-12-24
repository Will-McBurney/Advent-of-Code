package year23.day24

import AoCResultPrinter
import Reader

const val year: Int = 23
const val day: Int = 24

fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
    val hailstones: List<Hailstone> = getHailstones(lines)
    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(hailstones, 200000000000000L, 400000000000000L)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result()
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getHailstones(lines: List<String>): List<Hailstone> {
    return lines.map { line -> line.split("@") }
        .map {
            println(it)
            val position = it[0].split(",").map(String::trim).map(String::toLong)
            val velocity = it[1].split(",").map(String::trim).map(String::toLong)
            return@map Hailstone(SpaceCoordinate(position[0], position[1], position[2]),
                LinearVelocity(velocity[0], velocity[1], velocity[2]))
        }
}

data class SpaceCoordinate(val x: Long, val y: Long, val z: Long) {

}

data class LinearVelocity (val dx: Long, val dy: Long, val dz: Long)  {

}

data class Hailstone(
    val location: SpaceCoordinate,
    val velocity: LinearVelocity
) {
    val slopeXY = velocity.dy / velocity.dx.toDouble()
    val a = -slopeXY
    val c = -slopeXY * location.x + location.y

    fun getIntersectionXY(other: Hailstone): Pair<Double, Double>? {
        val determinant = this.a - other.a
        if (0.0 == determinant) { return null }
        return Pair((this.c - other.c)/determinant,
            (this.a * other.c - other.a * this.c)/determinant)
    }

    fun isFutureXY(xy: Pair<Double, Double>): Boolean {
        return ((xy.first - location.x) * velocity.dx) >= 0  && ((xy.second - location.y) * velocity.dy >= 0)
    }
}

fun getPart1Result(hailstones: List<Hailstone>, low: Long, high: Long): Int {
    var count = 0
    for (i in hailstones.indices) {
        for (j in i+1 ..< hailstones.size) {
            val hailstone1 = hailstones[i]
            val hailstone2 = hailstones[j]
            if (hailstone1.slopeXY == hailstone2.slopeXY) {continue}
            val intersect = hailstone1.getIntersectionXY(hailstone2)!!
            if (hailstone1.isFutureXY(intersect) && hailstone2.isFutureXY(intersect) &&
                low <= intersect.first && intersect.first <= high &&
                low <= intersect.second && intersect.second <= high) {
                count++
            }
        }
    }
    return count
}

fun getPart2Result(): Int {
    return 0
}
