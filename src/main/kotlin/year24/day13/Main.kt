package year24.day13

import AoCResultPrinter
import Reader

const val year: Int = 24
const val day: Int = 13


data class ClawMachine(
    val aX: Long,
    val aY: Long,
    val bX: Long,
    val bY: Long,
    val targetX: Long,
    val targetY: Long
)

const val A_TOKEN_COST = 3
const val B_TOKEN_COST = 1

fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "test_input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
    val machines = lines.windowed(3, step = 4).map { machineText ->
        ClawMachine(
            machineText[0].substringAfter("X+").substringBefore(",").toLong(),
            machineText[0].substringAfter("Y+").toLong(),
            machineText[1].substringAfter("X+").substringBefore(",").toLong(),
            machineText[1].substringAfter("Y+").toLong(),
            machineText[2].substringAfter("X=").substringBefore(",").toLong(),
            machineText[2].substringAfter("Y=").toLong(),
        )
    }


    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(machines)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(machines)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getPart1Result(machines: List<ClawMachine>): Long {
    return machines.sumOf { machine ->
        var bestTokenCost: Long? = null
        val aMax = listOf(machine.targetX / machine.aX, machine.targetY / machine.aY).min()
        for (aPresses in 0..aMax) {
            val diffX = machine.targetX - aPresses * machine.aX
            if (diffX % machine.bX != 0L) continue
            val bPresses = diffX / machine.bX
            if (machine.targetY != aPresses * machine.aY + bPresses * machine.bY) continue
            val tokenCost = aPresses * A_TOKEN_COST + bPresses * B_TOKEN_COST
            if (tokenCost < (bestTokenCost ?: Long.MAX_VALUE)) {
                bestTokenCost = tokenCost
            }
        }
        bestTokenCost ?: 0
    }
}

const val PART_2_TARGET_CHANGE = 10000000000000L

fun getPart2Result(machines: List<ClawMachine>): Long {
    return 0
    val bigMachines = machines.map { machine ->
        machine.copy(
            targetX = machine.targetX + PART_2_TARGET_CHANGE,
            targetY = machine.targetY + PART_2_TARGET_CHANGE
        )
    }
    return getPart1Result(bigMachines)
}
