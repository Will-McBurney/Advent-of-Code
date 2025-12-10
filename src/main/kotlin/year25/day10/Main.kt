package year25.day10

import AoCResultPrinter
import Reader
import java.util.*

const val year: Int = 25
const val day: Int = 10

enum class LightStatus {
    ON,
    OFF;
    
    fun toggle() = when (this) {
        ON -> OFF
        OFF -> ON
    }
    
    override fun toString(): String =
        when (this) {
            ON -> "#"
            OFF -> "."
        }
}

fun getLightStatus(ch: Char): LightStatus =
    when (ch) {
        '.' -> LightStatus.OFF
        '#' -> LightStatus.ON
        else -> throw IllegalArgumentException("Bad light status: $ch")
    }

class Machine(
    val indicatorLightGoal: List<LightStatus>,
    val buttons: List<Button>,
    val joltageRequirements: List<Int>


) {
    override fun toString(): String {
        return "Machine(indicatorLightGoal=${indicatorLightGoal.joinToString("") { it.toString() }}, " +
            "buttons=$buttons, " +
            "joltageRequirements=$joltageRequirements)"
    }
    
    fun getFewestPresses(): Int {
        data class SearchNode(
            val lightStatus: List<LightStatus>,
            val buttonPresses: Int
        )
        
        val startNode = SearchNode(
            Array(indicatorLightGoal.size) { LightStatus.OFF }.toList(),
            0
        )
        val queue = LinkedList<SearchNode>()
        queue.add(startNode)
        val visited = hashSetOf<List<LightStatus>>()
        visited.add(startNode.lightStatus)
        while (true) {
            val node = queue.poll()
            if (node.lightStatus == this.indicatorLightGoal) return node.buttonPresses
            this.buttons.map { button ->
                SearchNode(
                    lightStatus = node.lightStatus.indices.map { i ->
                        if (button.toggles.contains(i)) {
                            node.lightStatus[i].toggle()
                        } else {
                            node.lightStatus[i]
                        }
                    },
                    buttonPresses = node.buttonPresses + 1
                )
            }
                .filterNot { it.lightStatus in visited }
                .forEach {
                    visited.add(it.lightStatus)
                    queue.add(it)
                }
        }
    }
    
    fun getJoltagePresses(): Long {
        data class SearchNode(
            val joltageLevels: List<Int>,
            val buttonPresses: Int
        )
        return 0L
    }
}

class Button(
    val toggles: List<Int>
) {
    override fun toString(): String =
        "(${toggles.joinToString(",") { it.toString() }})"
}

fun main() {
    val printer = AoCResultPrinter(year, day)
    
    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
    val machines = lines.map { line ->
        val lightStatusGoal = line.substring(1, line.indexOf(']'))
            .map { getLightStatus(it) }
        val buttons = line.substring(line.indexOf('('), line.lastIndexOf(')') + 1)
            .split(" ")
            .map { s ->
                s.substring(1, s.lastIndex)
                    .split(",")
                    .map { it.toInt() }
            }
            .map { it -> Button(it) }
        val joltageRequirements = line.substring(line.indexOf('{') + 1, line.lastIndex)
            .split(",")
            .map { it.toInt() }
        Machine(lightStatusGoal, buttons, joltageRequirements)
    }
    
    printer.endSetup()
    
    //Do Part 1
    val part1Result = getPart1Result(machines)
    printer.endPart1()
    
    //Do Part 2
    val part2Result = getPart2Result()
    printer.endPart2()
    
    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getPart1Result(machines: List<Machine>): Int {
    return machines.sumOf { m -> m.getFewestPresses() }
}

fun getPart2Result(): Int {
    return 0
}
