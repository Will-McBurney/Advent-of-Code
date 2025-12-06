package year25.day06

import AoCResultPrinter
import Reader
const val year: Int = 25
const val day: Int = 6

class MathProblem{
    val numbers: MutableList<Long> = mutableListOf()
    var operation: Char = '.'
    
    val result: Long
        get() = when(operation) {
            '+' -> numbers.reduce(Long::plus)
            '*' -> numbers.reduce(Long::times)
            else -> throw IllegalStateException("Bad operator: $operation")
        }
}

fun main() {
    val printer = AoCResultPrinter(year, day)
    
    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
    val numProblems = lines.first().trim().replace("\\s+".toRegex(), " ").split(" ").size
    val problems = Array(numProblems){ MathProblem() }
    lines.subList(0, lines.size - 1).forEach { line ->
        line.trim().replace("\\s+".toRegex(), " ")
            .split(" ")
            .forEachIndexed { i, str ->
                problems[i].numbers.add(str.toLong())
            }
    }
    lines.last().trim().replace("\\s+".toRegex(), " ").split(" ")
        .forEachIndexed { i, str ->
            problems[i].operation = str[0]
        }

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(problems)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result()
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getPart1Result(problems: Array<MathProblem>): Long {
    return problems.sumOf { it.result }.also { println(it) }
}

fun getPart2Result(): Int {
    return 0
}
