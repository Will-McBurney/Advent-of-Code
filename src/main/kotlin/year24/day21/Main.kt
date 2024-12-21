package year24.day21

import AoCResultPrinter
import Reader
import kotlin.collections.map
import kotlin.collections.windowed

const val year: Int = 24
const val day: Int = 21

val numPadMap: Map<Char, Pair<Int, Int>> = mapOf(
    '7' to (0 to 0),
    '8' to (0 to 1),
    '9' to (0 to 2),
    '4' to (1 to 0),
    '5' to (1 to 1),
    '6' to (1 to 2),
    '1' to (2 to 0),
    '2' to (2 to 1),
    '3' to (2 to 2),
    '0' to (3 to 1),
    'A' to (3 to 2)
)

val directionPadMap: Map<Char, Pair<Int, Int>> = mapOf(
    '^' to (0 to 1),
    'A' to (0 to 2),
    '<' to (1 to 0),
    'v' to (1 to 1),
    '>' to (1 to 2),
)

fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)

    printer.endSetup()

    //Do Part 1
    val part1Result = getComplexitySum(lines, 2)
    printer.endPart1()

    //Do Part 2
    val part2Result = getComplexitySum(lines, 25)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun printNextSteps(directions: String) {
    var result = directions
    repeat(3) { i ->
        result = "A" + result.windowed(2, 1).map { getArrowPadDirections(directionPadMap[it[0]]!!, directionPadMap[it[1]]!!) }
                .joinToString("")
        //println("${i+1}: ${result.length}\t$result")
    }
}

var cache = mutableMapOf<Pair<String, Int>, Long>()

fun getComplexitySum(lines: List<String>, numRobots: Int): Long {
    return lines.sumOf { line -> getComplexity(line, numRobots) }
}

fun getComplexity(line:String, numRobots: Int): Long {
    val digits = ("A$line").toCharArray().toList()
    val numPadDirections = getNumberPadDirections(digits)
    val totalLengthOfHumanEntry = getDirectionsLength(numPadDirections, numRobots)
    val lineNumber = line.slice(0 .. 2).toInt() // 029A -> 29
    return totalLengthOfHumanEntry.toLong() * lineNumber
}

fun getNumberPadDirections(digits: List<Char>) =
    "A" + digits.windowed(2, 1)
        .map { getNumberPadDirections(numPadMap[it[0]]!!, numPadMap[it[1]]!!) }
        .joinToString("")

fun getDirectionsLength(directions: String, numRobots: Int): Long {
    if (numRobots == 0) return directions.length.toLong() - 1L
    if (cache.containsKey(directions to numRobots)) return cache[directions to numRobots]!!

    val totalInstructionLength = directions.windowed(2, 1)
        .map { "A" + getArrowPadDirections(directionPadMap[it[0]]!!, directionPadMap[it[1]]!!) }
        .sumOf { getDirectionsLength(it, numRobots - 1) }

    cache[directions to numRobots] = totalInstructionLength
    return totalInstructionLength
}

fun getUpDown(from: Pair<Int, Int>, to: Pair<Int, Int>):String =
    if (from.first > to.first) "^".repeat(from.first - to.first)
        else "v".repeat(to.first - from.first)

fun getLeftRight(from: Pair<Int, Int>, to: Pair<Int, Int>):String =
    if (from.second > to.second) "<".repeat(from.second - to.second)
    else ">".repeat(to.second - from.second)


fun getNumberPadDirections(from: Pair<Int, Int>, to: Pair<Int, Int>): String{
    val upDown = getUpDown(from, to)
    val leftRight = getLeftRight(from, to)

    return if (from.second == 0 && to.first == 3) {
        leftRight + upDown + "A"
    } else if ((from.first == 3 && to.second == 0) ||
        (leftRight.contains('>')))
        upDown + leftRight + "A"
    else leftRight + upDown + "A"
}

fun getArrowPadDirections(from: Pair<Int, Int>, to: Pair<Int, Int>): String {
    val upDown = getUpDown(from, to)
    val leftRight = getLeftRight(from, to)

    return if ((from.second == 0)) {
        leftRight + upDown + "A"
    } else if ((to.second == 0) || leftRight.contains('>')) {
        upDown + leftRight + "A"
    } else leftRight + upDown + "A"
}
