package year19.day01

import AoCResultPrinter
import Reader
const val year: Int = 19
const val day: Int = 1



fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
    val numbers = lines.map { it.toInt() }

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(numbers)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(numbers)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getPart1Result(numbers: List<Int>): Int = numbers.sumOf { it / 3  - 2}

fun getPart2Result(numbers: List<Int>): Int {
    return numbers.sumOf(::getFuelCost)
}

fun getFuelCost(initialWeight: Int): Int {
    var totalWeight = 0
    var currentWeight = initialWeight / 3 - 2
    while (currentWeight > 0) {
        totalWeight += currentWeight
        currentWeight = currentWeight / 3 - 2
    }
    return totalWeight
}
