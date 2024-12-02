package year24.day02

import AoCResultPrinter
import Reader
import kotlin.math.abs

const val year: Int = 24
const val day: Int = 2



fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
    val reports = lines.map{
        it.split(" ").map{
            it.toInt() }
    }

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(reports)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(reports)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getPart1Result(reports: List<List<Int>>): Int {
    return reports.count { isSafe(it) }
}

fun getPart2Result(reports: List<List<Int>>): Int {
    return reports.count { isSafe(it) || isNearlySafe(it) }
}

fun isNearlySafe(reports: List<Int>): Boolean {
    return reports.indices.any { excludedIndex ->
        isSafe(reports.filterIndexed{ i, _ -> i != excludedIndex})
    }
}

fun isSafe(report: List<Int>): Boolean {
    require(report.size > 1){"Report of size 1 or less: $report"}
    var increasing = false
    if (report[0] == report[1]) {
        return false
    }
    if (report[0] < report[1]) {
        increasing = true
    }
    (0 ..< report.size - 1).forEach { i ->
        if (increasing && report[i] >= report[i + 1]) {
            return false
        }
        if (!increasing && report[i] <= report[i + 1]) {
            return false
        }
        if (abs(report[i] - report[i + 1]) > 3) {
            return false
        }
    }
    return true
}


