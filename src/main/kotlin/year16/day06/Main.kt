package year16.day06

import AoCResultPrinter
import Reader
const val year: Int = 16
const val day: Int = 6

class LetterFrequencies {
    val letterFrequencies = mutableMapOf<Char, Int>()

    fun addLetter(ch: Char) {
        letterFrequencies[ch] = letterFrequencies.getOrDefault(ch, 0) + 1
    }

    fun getMostFrequentLetter(): Char {
        return letterFrequencies.maxBy { it.value }.key
    }

    fun getLeastFrequentLetter(): Char {
        return letterFrequencies.minBy { it.value }.key
    }
}

fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)

    val lineLength = lines[0].trim().length
    val letterFrequencies = List<LetterFrequencies>(lineLength){ LetterFrequencies() }
    lines.map { line -> line.trim().toCharArray() }
        .forEach { array ->
            array.forEachIndexed { index, char -> letterFrequencies[index].addLetter(char) }
        }

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(letterFrequencies)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(letterFrequencies)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getPart1Result(letterFrequencies: List<LetterFrequencies>): String {
    return letterFrequencies.map { it.getMostFrequentLetter() }
        .joinToString("")
}

fun getPart2Result(letterFrequencies: List<LetterFrequencies>): String {
    return letterFrequencies.map { it.getLeastFrequentLetter() }
        .joinToString("")
}
