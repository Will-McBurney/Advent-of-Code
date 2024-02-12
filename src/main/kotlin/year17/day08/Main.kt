package year17.day08

import AoCResultPrinter

const val year: Int = 17
const val day: Int = 8

fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
    val instructions = getInstructions(lines)

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(instructions)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result()
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getInstructions(lines: List<String>): List<Instruction> {
    return lines.filterNot { line -> line.isEmpty() }
        .map { line -> line.trim().split(" ") }
        .map { list ->
            Instruction(
                list[0],
                list[1].equals("inc"),
                list[2].toInt(),
                list[4],
                Condition.getCondition(list[5]),
                list[6].toInt()
            )
        }
}

data class Instruction(
    val changeVariable: String,
    val isIncrement: Boolean,
    val changeAmount: Int,
    val conditionVariable: String,
    val condition: Condition,
    val conditionalValue: Int

)

enum class Condition(val eval: (Int, Int) -> Boolean) {
    GREATER_THAN({ x, y -> x > y }),
    GREATER_THAN_OR_EQUAL_TO({ x, y -> x >= y }),
    EQUAL_TO({ x, y -> x == y }),
    LESS_THAN({ x, y -> x < y }),
    LESS_THAN_OR_EQUAL_TO({ x, y -> x <= y }),
    NOT_EQUAL_TO({ x, y -> x != y });

    companion object {
        fun getCondition(operationString: String): Condition {
            return when (operationString) {
                ">" -> GREATER_THAN
                ">=" -> GREATER_THAN_OR_EQUAL_TO
                "==" -> EQUAL_TO
                "<" -> LESS_THAN
                "<=" -> LESS_THAN_OR_EQUAL_TO
                "!=" -> NOT_EQUAL_TO
                else -> throw IllegalArgumentException()
            }
        }
    }
}

var maxRegisterValue = 0

fun getPart1Result(instructions: List<Instruction>): Int {
    val variableValues: MutableMap<String, Int> = mutableMapOf()

    instructions.forEach { inst ->
        if (!variableValues.containsKey(inst.changeVariable)) {
            variableValues[inst.changeVariable] = 0
        }
        if (!variableValues.containsKey(inst.conditionVariable)) {
            variableValues[inst.conditionVariable] = 0
        }

        if (inst.condition.eval(variableValues[inst.conditionVariable]!!, inst.conditionalValue)) {
            if (inst.isIncrement) {
                variableValues[inst.changeVariable] = variableValues[inst.changeVariable]!! + inst.changeAmount
            } else {
                variableValues[inst.changeVariable] = variableValues[inst.changeVariable]!! - inst.changeAmount
            }
            if(variableValues[inst.changeVariable]!! > maxRegisterValue) {
                maxRegisterValue = variableValues[inst.changeVariable]!!
            }
        }
    }

    return variableValues.values.max()
}

fun getPart2Result(): Int {
    return maxRegisterValue
}
