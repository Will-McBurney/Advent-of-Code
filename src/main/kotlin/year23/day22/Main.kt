package year23.day22

import AoCResultPrinter
import Reader
import year16.day04.day

const val year: Int = 23
const val day: Int = 22

fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "test_input.txt"
    val lines = Reader.getLines(year, day, inputFilename)

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result()
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result()
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getPart1Result(): Int {
    return 0
}

fun getPart2Result(): Int {
    return 0
}