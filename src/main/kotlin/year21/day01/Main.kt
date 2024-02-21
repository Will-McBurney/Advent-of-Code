package year21.day01

import AoCResultPrinter
import Reader
const val year: Int = 21
const val day: Int = 1



fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
    val depths = lines.map { it.toInt() }

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(depths)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(depths)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getPart1Result(depths: List<Int>): Int {
    return depths.filterIndexed{index, _ -> index != 0 && depths[index] > depths[index -1]}
        .count()
}

fun getPart2Result(depths: List<Int>): Int {
    return (0 ..< (depths.size - 3)).count { index ->
        depths[index + 3] > depths[index]
    }
}
