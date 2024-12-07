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
        .filter {it -> isPossible_P1(it.first, it.second)}
        .sumOf {it.first}
}

fun isPossible_P1(answer: Long, operands: List<Long>): Boolean {
    require(operands.isNotEmpty())
    return isPossible_P1(
        answer,
        operands,
        operands.first(),
        1
    )
}

private fun isPossible_P1(answer: Long, operands: List<Long>, current: Long, index: Int): Boolean {
    if (current == answer && index == operands.size) {
        return true
    }
    if (current > answer.toLong()) {
        return false
    }
    if (index >= operands.size) {
        return false
    }
    val sum = current + operands[index]
    val product = current * operands[index]
    return (isPossible_P2(answer, operands, product, index + 1) ||
            isPossible_P2(answer, operands, sum, index + 1))
}


fun getPart2Result(answers: List<Long>, operands: List<List<Long>>): Long {
    return answers.zip(operands)
        .filter {it -> isPossible_P2(it.first, it.second)}
        .sumOf {it.first}
}


fun isPossible_P2(answer: Long, operands: List<Long>): Boolean {
    require(operands.isNotEmpty())
    return isPossible_P2(
        answer,
        operands,
        operands.first(),
        1
    )
}

private fun isPossible_P2(answer: Long, operands: List<Long>, current: Long, index: Int): Boolean {
    if (current == answer && index == operands.size) {
        return true
    }
    if (current > answer.toLong()) {
        return false
    }
    if (index >= operands.size) {
        return false
    }
    val sum = current + operands[index]
    val product = current * operands[index]
    val concat = (current.toString() + operands[index].toString()).toLong()
    return (isPossible_P2(answer, operands, concat, index + 1)) ||
            isPossible_P2(answer, operands, product, index + 1) ||
            isPossible_P2(answer, operands, sum, index + 1)
}