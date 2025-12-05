package year25.day05

import AoCResultPrinter
import Reader
import kotlin.math.max

const val year: Int = 25
const val day: Int = 5

fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename).map { it.trim() }

    val splitPoint = lines.indexOf("")

    val reducedRanges = reduceRanges(
        lines.subList(0, splitPoint).map { line -> LongRange(
            line.substringBefore("-").toLong(),
            line.substringAfter("-").toLong())
        }
    )

    val ingredients = lines.subList(splitPoint + 1, lines.size)
        .map { line -> line.toLong() }


    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(ingredients, reducedRanges)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(reducedRanges)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

/**
 * Reduces a list of LongRanges by combining overlapping ranges.
 * @return ascending sorted list of non-overlapping [LongRange]s
 */
fun reduceRanges(ranges: List<LongRange>): List<LongRange> {
    val ranges = ranges.sortedBy { it.first }
    val reducedRanges = mutableListOf<LongRange>()
    var currentRange = ranges.first()
    for (i in 1..ranges.lastIndex) {
        if (currentRange.last >= ranges[i].first) {
            currentRange = currentRange.first .. max(currentRange.last, ranges[i].last)
        } else {
            reducedRanges.add(currentRange)
            currentRange = ranges[i]
        }
    }
    reducedRanges.add(currentRange)
    return reducedRanges
}

/**
 * Returns the number of ingredients in any range.
 * Weirdly, using binary search seemed slower than linear search here.
 */
fun getPart1Result(ingredients: List<Long>, ranges: List<LongRange>) =
    ingredients.count { ingredient ->
        ranges.any { range -> ingredient in range }
    }


fun getPart2Result(ranges: List<LongRange>) =
    ranges.sumOf { range -> range.last - range.first + 1 }
