package year23.day3

import java.io.BufferedReader
import java.util.NoSuchElementException
import java.util.stream.Collectors
import kotlin.streams.asStream

const val inputFilename = "input.txt"
var lines = emptyList<String>()
var lineLength = -1
var lineCount = -1

fun main() {
    val startTime = System.currentTimeMillis()
    val reader = object {}.javaClass.getResourceAsStream("input.txt")!!.bufferedReader()


    lines = reader.readLines()
    lineLength = lines[0].length
    lineCount = lines.size

    val getPart1Result = getPart1Result(reader)
    val getPart2Result = getPart2Result()
    val endTime = System.currentTimeMillis()
    println(
        """Part One: $getPart1Result 
        |Part Two: $getPart2Result 
        |Calculation time - ${endTime - startTime}ms
        |""".trimMargin()
    )
}

fun getPart1Result(reader: BufferedReader): Int {
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
    var match = Regex(numberMatchPattern).find(line);
    val specNumbers = arrayListOf<SpecNumber>()
    while (match != null) {
        specNumbers.add(SpecNumber(lineNumber, match.range.first, match.range.last, match.value.toInt()))
        match = match.next()
    }
    return specNumbers.filter { hasAdjacentSymbol(it) };
}

fun hasAdjacentSymbol(specNumber: SpecNumber): Boolean {
    val targetLineNumber = specNumber.lineNumber;
    val startingIndex =  (specNumber.startingIndex - 1).coerceAtLeast(0)
    val endingIndex = (specNumber.endingIndex + 1).coerceAtMost(lineLength - 1)

    //check left and right
    if (specNumber.startingIndex != 0 &&
        lines[targetLineNumber][specNumber.startingIndex - 1] != '.') {
        return true
    }

    if (specNumber.endingIndex != lineLength - 1 &&
        lines[targetLineNumber][specNumber.endingIndex + 1] != '.') {
        return true
    }

    if(specNumber.lineNumber != 0) {
        val above = lines[specNumber.lineNumber - 1]
        for (i in startingIndex .. endingIndex) {
            if (above[i] != '.') {
                return true;
            }
        }
    }

    if(specNumber.lineNumber != lineCount  - 1) {
        val below = lines[specNumber.lineNumber + 1]
        for (i in startingIndex .. endingIndex) {
            if (below[i] != '.') {
                return true;
            }
        }
    }
    return false;
}

fun getPart2Result(): Int {
    val lineSpecNumberMap = lines.asSequence()
        .mapIndexed{index, line -> getSpecNumbersFromLine(line, index)}
        .flatten()
        .groupBy(SpecNumber::lineNumber)

    var sum = 0;
    for(lineNumber in lines.indices) {
        val lineArray = lines[lineNumber].toCharArray()
        for (charIndex in lineArray.indices) {
            if (lineArray[charIndex] == '*') {
                sum += getGearRatio(lineNumber, charIndex, lineSpecNumberMap)
            }
        }
    }
    return sum;
}

fun getGearRatio(row: Int, column: Int, lineSpecNumberMap: Map<Int, List<SpecNumber>>): Int {
    val adjacentSpecNumbers = hashSetOf<SpecNumber>()
    //check left
    if (column != 0 && isDigit(lines[row][column - 1])) {
        adjacentSpecNumbers.add(getSpecNumber(row, column - 1, lineSpecNumberMap))
    }
    if (column != lineLength - 1 && isDigit(lines[row][column + 1])) {
        adjacentSpecNumbers.add(getSpecNumber(row, column + 1, lineSpecNumberMap))
    }

    val startingIndex =  (column - 1).coerceAtLeast(0)
    val endingIndex = (column + 1).coerceAtMost(lineLength - 1)

    if(row != 0) {
        val above = lines[row - 1]
        for (i in startingIndex .. endingIndex) {
            if (isDigit(above[i])) {
                adjacentSpecNumbers.add(getSpecNumber(row - 1, i, lineSpecNumberMap))
            }
        }
    }

    if(row != lineCount  - 1) {
        val below = lines[row + 1]
        for (i in startingIndex .. endingIndex) {
            if (isDigit(below[i])) {
                adjacentSpecNumbers.add(getSpecNumber(row + 1, i, lineSpecNumberMap))
            }
        }
    }

    if (adjacentSpecNumbers.size != 2) {
        return 0
    }
    return adjacentSpecNumbers.map(SpecNumber::value).reduce(Int::times)
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