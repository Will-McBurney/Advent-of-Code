package year24.day11

import AoCResultPrinter
import Reader

const val year: Int = 24
const val day: Int = 11



fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val stones = Reader.getLines(year, day, inputFilename)[0].trim()
        .split(" ")
        .map { it.toLong() }

    printer.endSetup()

    //Do Part 1
    val part1Result = getStoneCount(stones.toList(), 25)
    printer.endPart1()

    //Do Part 2
    val part2Result = getStoneCount(stones.toList(), 75)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)

}

fun processStone(stone: Long): List<Long> {
    if (stone == 0L) return listOf(1L)
    if (stone.toString().length % 2 == 0) {
        val length = stone.toString().length
        return listOf(
            stone.toString().substring(0, length / 2).toLong(),
            stone.toString().substring(length / 2).toLong(),
        )
    }
    return listOf(stone * 2024L)
}

fun getStoneCount(stones: List<Long>, blinks: Int): Long {
    return stones.sumOf { getStoneCountHelper(it, blinks) }
}

// memo for getStoneCountHelper
val cache = mutableMapOf<Pair<Long, Int>, Long>()

fun getStoneCountHelper(stone: Long, blinksRemaining: Int): Long {
    if (blinksRemaining == 0) {
        return 1L
    }
    if (cache.containsKey(stone to blinksRemaining)) {
        return cache[stone to blinksRemaining]!!
    }

    val results = processStone(stone).sumOf { getStoneCountHelper(it, blinksRemaining - 1) }
    cache[stone to blinksRemaining] = results
    return results
}
