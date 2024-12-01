package year24.day01

import AoCResultPrinter
import Reader
import kotlin.math.abs

const val year: Int = 24
const val day: Int = 1



fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)

    val l1 = mutableListOf<Int>()
    val l2 = mutableListOf<Int>()
    val pairs = lines.map{ it.split("\\s+".toRegex())}
        .forEach { pair ->
            l1.add(pair[0].toInt())
            l2.add(pair[1].toInt())
        }

    val l1s = l1.sorted()
    val l2s = l2.sorted()

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(l1s, l2s)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(l1s, l2s)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getPart1Result(l1: List<Int>, l2: List<Int>): Int {
    return l1.mapIndexed{index, _ -> abs(l1[index] - l2[index]) }.sum()
}

fun getPart2Result(l1: List<Int>, l2: List<Int>): Int {
    val countMap = l2.groupingBy { it }.eachCount()

    return l1.sumOf{ it * (countMap[it] ?: 0) }
}
