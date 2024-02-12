package year17.day03

import AoCResultPrinter
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.sqrt

const val year: Int = 17
const val day: Int = 3

fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val input = 361527

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(input)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(input)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getPart1Result(input: Int): Int {
    val rootRoundedDown = floor(sqrt(input.toDouble())).toInt()
    val lowerRoot = if (rootRoundedDown % 2 == 0) rootRoundedDown - 1 else rootRoundedDown
    val ring = lowerRoot/2 + 1
    val firstRingValue = lowerRoot * lowerRoot
    val diff = input - firstRingValue
    val ringBottomRightX = ring
    val ringSideLength = ring * 2
    return ringBottomRightX + abs(diff % ringSideLength - ringSideLength/2)

}

data class Coordinate(
    val x: Int,
    val y: Int
) {
    fun getNeighbors(): List<Coordinate> {
        return (-1 .. 1).map {dx ->
            (-1 .. 1).map {dy ->
                Coordinate(x + dx, y + dy)
            }
        }
            .flatten()
            .filterNot{ coordinate -> coordinate.x == this.x && coordinate.y == this.y }
    }

    fun getNextCoordinate(direction: Direction): Coordinate {
        return Coordinate(this.x + direction.dx, this.y + direction.dy)
    }
}

enum class Direction(
    val dx: Int,
    val dy: Int,
) {
    UP(0, 1),
    DOWN(0, -1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    fun counterClockwise(): Direction {
        return when (this) {
            UP -> LEFT
            LEFT -> DOWN
            DOWN -> RIGHT
            RIGHT -> UP
        }
    }
}

fun getPart2Result(input: Int): Int {
    val valueMap: MutableMap<Coordinate, Int> = mutableMapOf()

    var currentCoordinate = Coordinate(0, 0)
    valueMap[currentCoordinate] = 1

    var currentDirection = Direction.RIGHT
    var stepsRemaining = 1
    var nextStepsRemaining = 1
    var increaseStepsNextTime = false
    while(valueMap[currentCoordinate]!! <= input) {
        currentCoordinate = currentCoordinate.getNextCoordinate(currentDirection)
        stepsRemaining--

        val sum = currentCoordinate.getNeighbors()
            .sumOf { coordinate -> valueMap.getOrDefault(coordinate, 0) }

        valueMap[currentCoordinate] = sum
        println("$currentCoordinate - $sum")

        if (stepsRemaining == 0) {
            currentDirection = currentDirection.counterClockwise()
            if (increaseStepsNextTime) {
                nextStepsRemaining += 1
            }
            increaseStepsNextTime = !increaseStepsNextTime
            stepsRemaining = nextStepsRemaining
        }
    }

    return valueMap[currentCoordinate]!!
}


