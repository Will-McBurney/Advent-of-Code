package year22.day03

import AoCResultPrinter
import Reader

const val year: Int = 22
const val day: Int = 3

fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
        .filterNot { it.isEmpty() }
        .map { it.trim() }

    val rucksacks = lines.map {
        Pair<String, String>(
            it.substring(0, it.length / 2),
            it.substring(it.length / 2)
        )
    }

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(rucksacks)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(lines)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun findCommonChar(a: String, b: String): Char = a.toSet().single { char -> b.contains(char) }

fun findCommonChar(a: String, b: String, c: String): Char {
    val aSet = a.toSet()
    val bSet = b.toSet()
    val cSet = c.toSet()
    return aSet.intersect(bSet).intersect(cSet).single()
}

fun priority(char: Char): Int {
    return when (char) {
        in 'a' .. 'z' -> char.code - 'a'.code + 1
        in 'A'..'Z' -> char.code - 'A'.code + 27
        else -> throw IllegalArgumentException()
    }
}

fun getPart1Result(rucksacks: List<Pair<String, String>>): Int {
    return rucksacks.map { pair ->  findCommonChar(pair.first, pair.second) }
        .sumOf { char -> priority(char) }
}

fun getPart2Result(lines: List<String>): Int {
    return (lines.indices step 3).map { index -> findCommonChar(lines[index], lines[index + 1], lines[index + 2]) }
        .sumOf { char -> priority(char) }
}
