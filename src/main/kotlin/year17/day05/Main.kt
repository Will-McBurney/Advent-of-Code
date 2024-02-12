package year17.day05

import AoCResultPrinter

const val year: Int = 17
const val day: Int = 5

fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
    val instructions = getInstructions(lines)

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(instructions)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(instructions)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getInstructions(lines: List<String>): List<Int> {
    return lines.filterNot {line -> line.isEmpty()}
        .map { line -> line.trim().toInt() }
}

fun getPart1Result(instructions: List<Int>): Int {
    val currentInstructions = instructions.toMutableList()
    var currentIndex = 0
    var steps = 0
    while (currentIndex in instructions.indices) {
        val nextIndex = currentIndex + currentInstructions[currentIndex]
        currentInstructions[currentIndex]++
        currentIndex = nextIndex
        steps++
    }
    return steps
}

fun getPart2Result(instructions: List<Int>): Int {
    val currentInstructions = instructions.toMutableList()
    var currentIndex = 0
    var steps = 0
    while (currentIndex in instructions.indices) {
        val nextIndex = currentIndex + currentInstructions[currentIndex]
        if (currentInstructions[currentIndex] >= 3) {
            currentInstructions[currentIndex]--
        } else {
            currentInstructions[currentIndex]++
        }
        currentIndex = nextIndex
        steps++
    }
    return steps
}
