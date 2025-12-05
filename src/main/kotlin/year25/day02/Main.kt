package year25.day02

import AoCResultPrinter
import Reader
const val year: Int = 25
const val day: Int = 2



fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val line = Reader.getLines(year, day, inputFilename)[0]
    val ranges = line.split(",")
        .map { it.split("-") }
        .map { LongRange(it[0].toLong(), it[1].toLong()) }

    printer.endSetup()

    println(isInvalidPart2(2121212118))
    println(isInvalidPart2(111))

    //Do Part 1
    val part1Result = getPart1Result(ranges)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(ranges)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getPart1Result(ranges: List<LongRange>): Long {
    return ranges.sumOf { range ->
        range.filter { number ->
            val numString = number.toString()
            if (numString.length % 2 == 1) return@filter false
            val firstHalf = numString.substring(0, numString.length / 2)
            val secondHalf = numString.substring(numString.length / 2)
            firstHalf == secondHalf
        }.sum()
    }
}

fun getPart2Result(ranges: List<LongRange>): Long {
    return ranges.sumOf { range ->
        range.filter { number ->
            isInvalidPart2(number)
        }.sum()
    }
}

fun isInvalidPart2(number: Long): Boolean {
    val numString = number.toString()
    return (1 .. numString.length / 2).any { size ->
        if (numString.length % size != 0) return@any false
        val partialString = numString.substring(0, size)
        numString.windowed(size, size).all { it  == partialString}
    }
}
