package year25.day03

import AoCResultPrinter
import Reader
const val year: Int = 25
const val day: Int = 3



fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
    val batteryBanks = lines.map { line ->
        line.toCharArray().map { char -> char.code - '0'.code }
    }

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(batteryBanks)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(batteryBanks)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getJoltageRate(bank: List<Int>, digits: Int): Long {
    require(bank.size >= digits)
    var joltage = 0L
    var digitsRemaining = digits
    var startRange = -1
    while (digitsRemaining > 0) {
        val nextIndex = largestDigitIndexInRange(bank, startRange ..bank.size - digitsRemaining)
        joltage = joltage * 10 + bank[nextIndex]
        startRange = nextIndex + 1
        digitsRemaining--
    }
    return joltage
}

fun largestDigitIndexInRange(bank: List<Int>, range: IntRange): Int  {
    return bank.indices.filter { i -> range.contains(i) }
        .maxBy{ i -> bank[i]}
}

fun getPart1Result(batteryBanks: List<List<Int>>): Long {
    return batteryBanks.sumOf { bank -> getJoltageRate(bank, 2) }
}

fun getPart2Result(batteryBanks: List<List<Int>>): Long {
    return batteryBanks.sumOf { bank -> getJoltageRate(bank, 12) }
}
