package year22.day04

import AoCResultPrinter
import Reader

const val year: Int = 22
const val day: Int = 4

fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
    val workPairs = getWorkPairs(lines)

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(workPairs)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(workPairs)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getWorkPairs(lines: List<String>): List<Pair<IntRange, IntRange>> {
    return lines.filterNot { it.isEmpty() }
        .map { it.trim() }
        .map { it.split(",") }
        .map { rangeString ->
            rangeString.map {
                IntRange(it.substringBefore("-").toInt(), it.substringAfter("-").toInt())
            }
        }
        .map { intRanges -> intRanges[0] to intRanges[1] }
}

fun getPart1Result(workPairs: List<Pair<IntRange, IntRange>>): Int {
    return workPairs.count { pair -> isInsideOf(pair.first, pair.second) || isInsideOf(pair.second, pair.first) }
}

fun isInsideOf(outer: IntRange, inner: IntRange): Boolean {
    return inner.first in outer && inner.last in outer
}

fun overlaps(a: IntRange, b: IntRange): Boolean = a.toSet().intersect(b.toSet()).isNotEmpty()

fun getPart2Result(workPairs: List<Pair<IntRange, IntRange>>): Int {
    return workPairs.count { pair -> overlaps(pair.first, pair.second) }
}
