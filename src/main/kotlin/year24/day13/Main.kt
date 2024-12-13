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
) {
    fun solve(): Pair<Long, Long>? {
        val inverseDeterminant = aX*bY - aY*bX
        if (inverseDeterminant == 0L) {
            return null
        }

        val cInv = listOf(listOf(bY, -bX), listOf(-aY, aX))

        val aPresses = (cInv[0][0] * targetX + cInv[0][1] * targetY)/inverseDeterminant
        val bPresses = (cInv[1][0] * targetX + cInv[1][1] * targetY)/inverseDeterminant

        if (aPresses * aX + bPresses * bX == targetX && aPresses * aY + bPresses * bY == targetY) {
            return aPresses to bPresses
        }
        return null
    }

}

const val A_TOKEN_COST = 3
const val B_TOKEN_COST = 1

fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
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

    println(machines[0].solve())

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
    return machines.map { machine ->
        machine.solve() ?: 0L to 0L
    }.sumOf { pair -> pair.first * A_TOKEN_COST + pair.second * B_TOKEN_COST }
}

const val PART_2_TARGET_CHANGE = 10000000000000L

fun getPart2Result(machines: List<ClawMachine>): Long {
    val bigMachines = machines.map { machine ->
        machine.copy(
            targetX = machine.targetX + PART_2_TARGET_CHANGE,
            targetY = machine.targetY + PART_2_TARGET_CHANGE
        )
    }
    return getPart1Result(bigMachines)
}
