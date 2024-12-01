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

    val list1 = ArrayList<Int>(lines.size)
    val list2 = ArrayList<Int>(lines.size)

    // split lines on whitespace and add to sorted lists
    lines.map{ it.split("\\s+".toRegex())}
        .forEachIndexed { index, pair ->
            list1.add(pair[0].toInt())
            list2.add(pair[1].toInt())
        }
    val list1Sorted = list1.sorted()
    val list2Sorted = list2.sorted()

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(list1Sorted, list2Sorted)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(list1Sorted, list2Sorted)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getPart1Result(list1Sorted: List<Int>, list2Sorted: List<Int>): Int {
    return list1Sorted.mapIndexed{ index, _ -> abs(list1Sorted[index] - list2Sorted[index]) }.sum()
}

fun getPart2Result(list1Sorted: List<Int>, list2Sorted: List<Int>): Int {
    val counts = Array<Int>(100000){0}
    list2Sorted.forEach { counts[it]++ }

    return list1Sorted.sumOf{ it * counts[it] }
}
