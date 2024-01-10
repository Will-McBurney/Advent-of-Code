package year17.day1

import AoCResultPrinter
import Reader

const val year: Int = 17
const val day: Int = 1

fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
    val inputLine = lines.first()

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(inputLine)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(inputLine)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getPart1Result(inputLine: String): Int {
    val length = inputLine.length
    return inputLine.indices
        .filter { index -> inputLine[index] == inputLine[(index+1) % length] }
        .sumOf { index -> inputLine[index] - '0' }
}

fun getPart2Result(inputLine: String): Int {
    val length = inputLine.length
    return inputLine.indices
        .filter { index -> inputLine[index] == inputLine[(index+length/2) % length] }
        .sumOf { index -> inputLine[index] - '0' }
}