package year20.day01

import AoCResultPrinter
import Reader
const val year: Int = 20
const val day: Int = 1



fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
    val expenses: List<Int> = lines.map { line -> line.toInt() }

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(expenses)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(expenses)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getPart1Result(expenses: List<Int>): Int {
    expenses.indices.forEach { i ->
        (i+1 ..< expenses.size).forEach { j ->
            if (expenses[i] + expenses[j] == 2020)
                return expenses[i] * expenses[j]
        }
    }
    throw IllegalArgumentException("No matching numbers")
}

fun getPart2Result(expenses: List<Int>): Int {
    expenses.indices.forEach { i ->
        (i+1 ..< expenses.size).forEach { j ->
            (j+1 ..< expenses.size).forEach { k ->
                if (expenses[i] + expenses[j] + expenses[k] == 2020)
                    return expenses[i] * expenses[j] * expenses [k]
            }
        }
    }
    throw IllegalArgumentException("No matching numbers")
}
