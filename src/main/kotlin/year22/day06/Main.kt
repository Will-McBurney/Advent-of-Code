package year22.day06

import AoCResultPrinter
import Reader

const val year: Int = 22
const val day: Int = 6

fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val line: String = Reader.getLines(year, day, inputFilename)[0]

    printer.endSetup()

    val charArray = line.toCharArray()

    //Do Part 1
    val part1Result = getPart1Result(charArray)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(charArray)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getPart1Result(line: CharArray): Int {
    return getStartOfMessageIndex(line, 4)
}

fun getPart2Result(line: CharArray): Int {
    return getStartOfMessageIndex(line, 14)
}

private fun getStartOfMessageIndex(line: CharArray, markerSize: Int): Int {
    var currentIndex = markerSize
    while (currentIndex < line.size) {
        var loopIndex = currentIndex - 1
        val chars = CharArray(markerSize)
        while (loopIndex >= currentIndex - markerSize) {
            val loopChar = line[loopIndex]
            if (chars.contains(loopChar)) {
                break
            }
            chars[currentIndex - 1 - loopIndex] = loopChar
            loopIndex--
        }
        if (loopIndex == currentIndex - markerSize - 1) {
            return currentIndex
        }
        currentIndex = loopIndex + markerSize + 1
    }
    return -1
}


