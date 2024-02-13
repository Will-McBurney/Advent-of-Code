package year22.day02

import AoCResultPrinter
import Reader

const val year: Int = 22
const val day: Int = 2

fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
        .filterNot { it.isEmpty() }
        .map { it.trim() }

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(lines)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(lines)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}



fun getPart1Result(lines: List<String>): Int {
    return lines.map { line -> line.split(" ") }
        .sumOf { tokens: List<String> ->
            val opponentThrow = RPSThrow.getRPSThrow(tokens[0].single())
            val playerThrow = RPSThrow.getPart1Throw(tokens[1].single())
            return@sumOf playerThrow.getTotalScore(opponentThrow)
        }
}

fun getPart2Result(lines: List<String>): Int {
    return lines.map { line -> line.split(" ") }
        .sumOf { tokens: List<String> ->
            val opponentThrow = RPSThrow.getRPSThrow(tokens[0].single())
            val playerThrow = when(tokens[1].single()) {
                'X' -> opponentThrow.beats()
                'Y' -> opponentThrow
                'Z' -> opponentThrow.beatenBy()
                else -> throw IllegalArgumentException()
            }
            return@sumOf playerThrow.getTotalScore(opponentThrow)
        }
}
