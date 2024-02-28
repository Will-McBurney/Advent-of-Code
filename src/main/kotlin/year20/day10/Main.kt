package year20.day10

import AoCResultPrinter
import Reader
import kotlin.math.abs

const val year: Int = 20
const val day: Int = 10



fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
    val cycles = getCycles(lines)
    println(cycles)

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(cycles)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(cycles)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

private fun getCycles(lines: List<String>): MutableList<Int> {
    val cycles = mutableListOf<Int>()
    lines.forEach { line ->
        if (line.startsWith("noop")) {
            cycles.add(0)
        } else {
            cycles.add(0)
            val inc = line.substringAfter("addx ").toInt()
            cycles.add(inc)
        }
    }
    return cycles
}

fun getPart1Result(cycles: List<Int>): Int {
    var xRegister = 1
    var signalStrength = 0
    cycles.forEachIndexed { index, inc ->
        val cycle = index + 1
        if (cycle % 40 == 20 && cycle <= 220) {
            signalStrength += xRegister * cycle
        }
        xRegister += inc
    }
    return signalStrength
}



fun getPart2Result(cycles: MutableList<Int>): Int {
    var spritePosition = 1
    cycles.forEachIndexed { index, inc ->
        if (index % 40 == 0) {
            println()
        }
        if (abs(spritePosition - (index % 40)) <= 1) {
            print("#")
        } else
            print(".")
        spritePosition += inc
    }
    println()
    return 0
}
