package year18.day01

import AoCResultPrinter

const val year: Int = 18
const val day: Int = 1

fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename).filter { it.isNotEmpty() }
    val frequencies = lines.map { line -> line.trim().removePrefix("+").toInt() }

    printer.endSetup()

    //Do Part 1
    val part1Result = frequencies.sum()
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(frequencies)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}


fun getPart2Result(frequencies: List<Int>): Int {
    val frequencyHistory: MutableSet<Int> = mutableSetOf()
    var currentIndex = 0
    var lastValue = 0
    do {
        frequencyHistory.add(lastValue)
        lastValue += frequencies[currentIndex]
        currentIndex++
        if (currentIndex == frequencies.size) currentIndex = 0
    } while (!frequencyHistory.contains(lastValue))

    return lastValue
}