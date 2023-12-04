package year23.day3

import java.util.NoSuchElementException
import kotlin.streams.asStream

lateinit var lines: List<String>
lateinit var grid: CharGrid
var lineLength: Int = -1
var lineCount: Int = -1

fun main() {
    val startTime = System.currentTimeMillis()
    val reader = object {}.javaClass.getResourceAsStream("input.txt")!!.bufferedReader()


    lines = reader.readLines()
    grid = CharGrid(lines)
    lineLength = grid.width
    lineCount = grid.height

    val getPart1Result = getPart1Result()
    val getPart2Result = getPart2Result()
    val endTime = System.currentTimeMillis()
    println(
        """Part One: $getPart1Result 
        |Part Two: $getPart2Result 
        |Calculation time - ${endTime - startTime}ms
        |""".trimMargin()
    )
}

fun getPart1Result(): Int {
    return lines.asSequence()
        .mapIndexed{ index, line -> getSpecNumbersFromLine(line, index) }
        .asStream().parallel()
        .flatMap (List<SpecNumber>::stream)
        .filter { hasAdjacentSymbol(it) }
        .mapToInt(SpecNumber::value)
        .sum()
}

const val numberMatchPattern = "[0-9]+"
fun getSpecNumbersFromLine(line: String, lineNumber: Int): List<SpecNumber> {
    var match = Regex(numberMatchPattern).find(line)
    val specNumbers = arrayListOf<SpecNumber>()
    while (match != null) {
        specNumbers.add(SpecNumber(lineNumber, match.range.first, match.range.last, match.value.toInt()))
        match = match.next()
    }
    return specNumbers.filter { hasAdjacentSymbol(it) }
}

fun hasAdjacentSymbol(specNumber: SpecNumber): Boolean {
    return specNumber.getNeighbors(grid)
        .map { grid.get(it.first, it.second) }
        .any { it != '.' }
}

fun getPart2Result(): Int {
    val lineSpecNumberMap = lines.asSequence()
        .mapIndexed{index, line -> getSpecNumbersFromLine(line, index)}
        .flatten()
        .groupBy(SpecNumber::lineNumber)


    return lines.asSequence()
        .mapIndexed{ lineNumber, line ->
            line.toCharArray().indices
                .filter { charIndex -> line[charIndex] == '*' }
                .sumOf { charIndex -> getGearRatio(lineNumber, charIndex, lineSpecNumberMap) }
        }
        .sum()
}

fun getGearRatio(row: Int, column: Int, lineSpecNumberMap: Map<Int, List<SpecNumber>>): Int {
    val adjacentSet = grid.getNeighborsCoordinates(row, column)
        .filter{isDigit(grid.get(it.first, it.second)) }
        .map{ getSpecNumber(it.first, it.second, lineSpecNumberMap) }
        .toSet()

    if (adjacentSet.size != 2) {
        return 0
    }
    return adjacentSet
        .map(SpecNumber::value)
        .reduce(Int::times)
}

fun getSpecNumber(row: Int, column: Int, lineSpecNumberMap: Map<Int, List<SpecNumber>>): SpecNumber {
    val lineSpecNumbers = lineSpecNumberMap[row]!!
    for (specNumber in lineSpecNumbers) {
        if (specNumber.containsIndex(row, column)) {
            return specNumber
        }
    }
    throw NoSuchElementException("row - $row, column - $column - ${lineSpecNumberMap[row]}")
}

fun isDigit(char: Char): Boolean {
    return char in '0'..'9'
}