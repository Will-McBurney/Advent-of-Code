package year21.day03

import AoCResultPrinter
import Reader
const val year: Int = 21
const val day: Int = 3



fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)

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
    val gammaString = getMostCommonBitString(lines)
    val epsilonString = getOppositeBitString(gammaString)

    val gamma = bitStringToInt(gammaString)
    val epsilon = bitStringToInt(epsilonString)

    return gamma * epsilon
}

fun getPart2Result(lines: List<String>): Int {
    val oxygenGeneratorRating = getIntByBitCriteria(lines, ::mostCommonBitAt)
    val co2ScrubberRating = getIntByBitCriteria(lines, ::leastCommonBitAt)
    return oxygenGeneratorRating * co2ScrubberRating
}

private fun getIntByBitCriteria(lines: List<String>, bitFinder: (Int, List<String>) -> Char): Int {
    var bitStrings = lines.toList()
    var currentIndex = 0;

    while (bitStrings.size > 1) {
        val targetBit = bitFinder(currentIndex, bitStrings);
        bitStrings = bitStrings.filter { bitString -> bitString[currentIndex] == targetBit }
        currentIndex++
    }

    return bitStringToInt(bitStrings.single())
}

fun bitStringToInt(gammaString: String): Int {
    return gammaString.toInt(2)
}

private fun getMostCommonBitString(bitStrings: List<String>): String {
    val gammaBitString = StringBuilder(bitStrings[0].length)

    for (index in bitStrings[0].indices) {
        val mostCommonBit = mostCommonBitAt(index, bitStrings)
        gammaBitString.append(mostCommonBit)
    }
    return gammaBitString.toString();
}

private fun mostCommonBitAt(index: Int, bitStrings: List<String>): Char {
    var ones = 0
    var zeroes = 0
    bitStrings.forEach { str ->
        if (str[index] == '1') ones++ else zeroes++
    }
    val mostCommonBit = if (ones >= zeroes) '1' else '0'
    return mostCommonBit
}

fun getOppositeBitString(gammaBitString: String): String {
    return gammaBitString.map { bit -> if (bit == '1')  '0' else  '1' }.joinToString("")
}

private fun leastCommonBitAt(index: Int, bitStrings: List<String>): Char {
    return if (mostCommonBitAt(index, bitStrings) == '1') '0' else '1'
}
