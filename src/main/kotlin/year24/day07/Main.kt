package year24.day07

import AoCResultPrinter
import Reader
const val year: Int = 24
const val day: Int = 7



fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)

    val answers = lines.map { it.substringBefore(":").trim().toLong()  }
    val operands = lines.map {
        it.substringAfter(":").trim()
            .split(" ")
            .map { it.toLong() }
    }

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(answers, operands)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(answers, operands)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getPart1Result(answers: List<Long>, operands: List<List<Long>>): Long {
    return answers.zip(operands)
        .filter {it -> isPossible(
            it.first,
            it.second,
            listOf(
                Long::plus,
                Long::times
            )
        )}
        .sumOf{it.first}
}

fun Long.concat(other: Long): Long {
    return (this.toString() + other.toString()).toLong()
}

fun getPart2Result(answers: List<Long>, operands: List<List<Long>>): Long {
    return answers.zip(operands)
        .filter {it -> isPossible(
            it.first,
            it.second,
            listOf(
                Long::plus,
                Long::times,
                Long::concat
            )
        )}
        .sumOf {it.first}
}

fun isPossible(
    answer: Long,
    operands: List<Long>,
    operations: List<(Long, Long) -> Long>
): Boolean {
    require(operands.isNotEmpty())
    return isPossible(
        answer,
        operands,
        operations,
        operands.first(),
        1
    )
}

private fun isPossible(
    answer: Long,
    operands: List<Long>,
    operations: List<(Long, Long) -> Long>,
    current: Long,
    index: Int,
): Boolean {
    if (current == answer && index == operands.size) {
        return true
    }
    if (current > answer.toLong()) {
        return false
    }
    if (index >= operands.size) {
        return false
    }
    return operations.map { f -> f(current, operands[index])  }
        .any { result ->  isPossible(
            answer, operands, operations, result, index + 1
        ) }
}