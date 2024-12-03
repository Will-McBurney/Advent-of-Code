package year24.day03

import AoCResultPrinter
import Reader

const val year: Int = 24
const val day: Int = 3



fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val input = Reader.getLines(year, day, inputFilename).joinToString(separator = "\n")

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(input)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(input)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

val patternPart1 = """mul\(([0-9]{1,3}),([0-9]{1,3})\)""".toRegex()

val patternPart2 = """(do\(\)|don't\(\)|mul\(([0-9]{1,3}),([0-9]{1,3})\))""".toRegex()


fun getPart1Result(input: String): Long {
    return patternPart1.findAll(input)
        .sumOf{ match ->
            match.groups[1]!!.value.toLong() * match.groups[2]!!.value.toLong()
        }
}

fun getPart2Result(input: String): Long {
    var enabled = true
    var sum = 0L
    patternPart2.findAll(input).forEach{ match ->
        val str = match.groups[0]!!.value
        if (str.startsWith("do(")) {
            enabled = true
        }
        if (str.startsWith("don't(")) {
            enabled = false
        }
        if (enabled && str.startsWith("mul(")){
            sum += match.groups[2]!!.value.toLong() * match.groups[3]!!.value.toLong()
        }
    }
    return sum
}
