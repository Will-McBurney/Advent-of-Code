package year17.day02

import AoCResultPrinter
import Reader

const val year: Int = 17
const val day: Int = 2

fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
    val numbers = getNumberGrid(lines)

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(numbers)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(numbers)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getNumberGrid(lines: List<String>): List<List<Int>> {
    return lines.map { it.trim() }
        .map{ it.split("\\s+".toRegex()).map {string -> string.toInt() }}
}

fun getPart1Result(numbers: List<List<Int>>): Int {
    return numbers.sumOf { line -> line.max() - line.min() }
}

fun getPart2Result(numbers: List<List<Int>>): Int {
    return numbers.sumOf { line -> getDivisionResult(line) }
}

fun getDivisionResult(numbers: List<Int>): Int {
    numbers.forEachIndexed() { indexA, a ->
        numbers.subList(indexA + 1, numbers.size).forEach { b ->
            if (isDivisibleBy(a, b)) return a / b
            if (isDivisibleBy(b, a)) return b / a
        }
    }
    throw IllegalArgumentException("No divisible pair in the list")
}

fun isDivisibleBy(number: Int, divisor: Int): Boolean = number % divisor == 0