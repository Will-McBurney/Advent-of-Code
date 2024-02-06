package year17.day04

import AoCResultPrinter

const val year: Int = 17
const val day: Int = 4

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
    return lines.map{ line -> line.split(" ")}
        .filterNot{ tokens -> hasDuplicates(tokens) }
        .count()
}

fun <T: Any> hasDuplicates(tokens: List<T>): Boolean {
    tokens.forEachIndexed { index, token ->
        if (tokens.subList(index + 1, tokens.size).contains(token)) {
            return true
        }
    }
    return false
}

fun getPart2Result(lines: List<String>): Int {
    return lines.map{ line -> line.split(" ")}
        .filterNot{ tokens -> hasDuplicates(tokens) }
        .map {list ->
            list.map { token -> getLetterCounts(token) }
        }
        .filterNot { mapList -> hasDuplicates(mapList) }
        .count()
}

fun getLetterCounts(token: String): Map<Char, Int> {
    return token.toCharArray()
        .fold(mutableMapOf<Char, Int>()) { map, item ->
            map[item] = map.getOrDefault(item, 0) + 1
            return@fold map
        }
}
