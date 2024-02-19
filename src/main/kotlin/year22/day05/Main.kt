package year22.day05

import AoCResultPrinter
import Reader

const val year: Int = 22
const val day: Int = 5

fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
    val emptyLineIndex = lines.indexOf("")

    val stackCount = lines[emptyLineIndex - 1].split("\\s".toRegex())
        .filter { token -> token.trim().isNotEmpty() }
        .maxOf { string -> string.toInt() }

    val boxStacksPart1 = BoxStacks(stackCount)
    stackBoxes(boxStacksPart1, lines.subList(0, emptyLineIndex-1))

    val boxStacksPart2 = BoxStacks(stackCount)
    stackBoxes(boxStacksPart2, lines.subList(0, emptyLineIndex-1))

    val instructions: List<Instruction> = getInstructions(lines.subList(emptyLineIndex+1, lines.size))

    instructions.forEach { boxStacksPart1.movePart1(it) }
    instructions.forEach { boxStacksPart2.movePart2(it) }

    printer.endSetup()

    //Do Part 1
    val part1Result = boxStacksPart1.getMessage()
    printer.endPart1()

    //Do Part 2
    val part2Result = boxStacksPart2.getMessage()
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun stackBoxes(boxStacks: BoxStacks, boxLines: List<String>) {
    boxLines.reversed().forEach {line ->
        (0..< boxStacks.stackCount).forEach { boxIndex ->
            val charIndex = 1 + 4 * boxIndex
            if (charIndex < line.length) {
                val char = line[charIndex]
                if (char != ' ') {
                    boxStacks.push(boxIndex + 1, char)
                }
            }
        }
    }
}

fun getInstructions(instructionsList: List<String>): List<Instruction> {
    return instructionsList.map { line ->
        Instruction(
            line.substringAfter("move ").substringBefore(" from").toInt(),
            line.substringAfter("from ").substringBefore(" to").toInt(),
            line.substringAfter("to ").toInt()
        )
    }
}

data class Instruction(
    val boxCount: Int,
    val startStack: Int,
    val endStack: Int
)


fun getPart1Result(): Int {
    return 0
}

fun getPart2Result(): Int {
    return 0
}
