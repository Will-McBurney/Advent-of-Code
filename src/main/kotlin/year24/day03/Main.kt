package year24.day03

import AoCResultPrinter
import Reader
const val year: Int = 24
const val day: Int = 3



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
