package year24.day17

import AoCResultPrinter
import Reader
import kotlin.math.pow

const val year: Int = 24
const val day: Int = 17



fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)

    val registerA = lines[0].substringAfter(": ").toLong()
    val registerB = lines[1].substringAfter(": ").toLong()
    val registerC = lines[2].substringAfter(": ").toLong()
    val program = lines[4].substringAfter(": ")
        .split(",")
        .map { it.toInt() }
        .toList()

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(registerA, registerB, registerC, program)
    printer.endPart1()

    println(part1Result)

    //Do Part 2
    val part2Result = 0 //getPart2Result(registerB, registerC, program)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

enum class OpCode {
    ADV, // division -> A / 2^combo -> A
    BXL, // bitwise XOR -> B xor literal -> B
    BST, // combo % 8 -> B
    JNZ, // if A != 0 -> literal -> JP  - nothing if A==0  -- don't increase IP after jump
    BXC, // Bitwise XOR -> B xor C -> B  - ignore operand
    OUT, // Print (combo %) 8 comma separated
    BDV, // division -> A / 2^combo -> B
    CDV, // division -> A / 2^combo -> C
}

fun getPart1Result(initialA: Long, initialB: Long, initialC: Long, program: List<Int>): List<Int> {
    var regA = initialA
    var (regB, regC) = listOf(initialB, initialC)
    var instructionPointer = 0
    val output = mutableListOf<Int>()

    fun getComboOperand(operand: Long): Long {
        if (operand == 4L) return regA
        if (operand == 5L) return regB
        if (operand == 6L) return regC
        return operand
    }

    while (instructionPointer < program.size) {
        val operation = program[instructionPointer]
        val literal = program[instructionPointer + 1].toLong()
        val combo = getComboOperand(literal)
        when (getOpCode(operation)) {
            OpCode.ADV -> { regA = regA / (2.0.pow(combo.toInt()).toInt())}
            OpCode.BXL -> { regB = regB xor literal }
            OpCode.BST -> { regB = combo % 8}
            OpCode.JNZ -> { if (regA != 0L) { instructionPointer = (literal - 2).toInt()} }
            OpCode.BXC -> { regB = regB xor regC }
            OpCode.OUT -> { output.add((combo % 8).toInt()) }
            OpCode.BDV -> { regB = regA / (2.0.pow(combo.toInt()).toInt()) % 8}
            OpCode.CDV -> { regC = regA / (2.0.pow(combo.toInt()).toInt()) % 8}
        }
        instructionPointer += 2
        //if (getOpCode(operation) == OpCode.ADV)
            println("%3s\t%2d\t%15d|\t%2d\t%20d\t%10d\t%10d".format(
                getOpCode(operation), literal, combo, instructionPointer, regA, regB, regC
            ))
    }

    return return output
}

fun getOpCode(instruction: Int): OpCode{
    return when (instruction) {
        0 -> OpCode.ADV
        1 -> OpCode.BXL
        2 -> OpCode.BST
        3 -> OpCode.JNZ
        4 -> OpCode.BXC
        5 -> OpCode.OUT
        6 -> OpCode.BDV
        7 -> OpCode.CDV
        else -> throw IllegalArgumentException("Invalid op code $instruction")
    }
}



fun getPart2Result(registerB: Long, registerC: Long, program: List<Int>): Int {
    return 0

    (0 ..< Int.MAX_VALUE).forEach { i ->
        if (i % 1000000 == 0) {
            println("%12d".format(i))
        }
        if (getPart1Result(i.toLong(), registerB, registerC, program) == program) {
            return i
        }
    }
    return 0
}
