package year19.day02

import AoCResultPrinter
import Reader
const val year: Int = 19
const val day: Int = 2

const val STOP = 99
const val ADD = 1
const val MULT = 2

fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
    val numbers: List<Int> = lines[0].split(",")
        .map { it.toInt() }

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(numbers, 12, 2)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(numbers, 19690720)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getPart1Result(numbers: List<Int>, instr1: Int, instr2: Int): Int {
    var memory = numbers.toMutableList()
    memory[1] = instr1
    memory[2] = instr2
    var pointer = 0
    while (memory[pointer] != 99) {
        when (memory[pointer]) {
            ADD -> memory[memory[pointer + 3]] = memory[memory[pointer + 1]] + memory[memory[pointer + 2]]
            MULT -> memory[memory[pointer + 3]] = memory[memory[pointer + 1]] * memory[memory[pointer + 2]]
        }
        pointer += 4
    }
    return memory[0]
}

fun getPart2Result(numbers: List<Int>, target: Int): Int {
    repeat(10000) {time ->
        val result = getPart1Result(numbers, time / 100, time % 100)
        if (result == target) {
            return time
        }
    }
    throw IllegalStateException()
}