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
    val input: Int = 361527

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(input)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result()
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getPart1Result(input: Int): Int {
    val rootRoundedDown = floor(sqrt(input.toDouble())).toInt()
    val lowerRoot = if (rootRoundedDown % 2 == 0) rootRoundedDown - 1 else rootRoundedDown
    val ring = lowerRoot/2 + 1
    println(ring)
    val firstRingValue = lowerRoot * lowerRoot

    println(lowerRoot)
    val diff = input - firstRingValue
    println(diff)
    val ringBottomRightX = ring
    val ringSideLength = ring * 2
    println("$ringBottomRightX, $ringSideLength")
    println("$diff, $ringSideLength, ${diff/ringSideLength}, ${diff % ringSideLength}")
    return ringBottomRightX + abs(diff % ringSideLength - ringSideLength/2)

}

fun getPart2Result(): Int {
    return 0
}
