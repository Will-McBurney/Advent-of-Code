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
        while (queue.isNotEmpty()) {
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
        throw IllegalStateException("Not possible")
    }
    
    fun getJoltagePresses(): Int =
        solveJoltage(this.buttons, this.joltageRequirements)
}

fun solveJoltage(buttons: List<Button>, goal: List<Int>): Int {
    val comboTotals = getCombinationTotals(buttons, goal)
    val comboParities = comboTotals.keys.associateWith { k ->
        comboTotals[k]!!.map { it % 2 }
    }
    return solveRecursive(goal, buttons, comboTotals, comboParities, 0, mutableMapOf()).sum()
}

fun solveRecursive(
    goal: List<Int>,
    buttons: List<Button>,
    comboTotals: Map<List<Button>, List<Int>>,
    comboParities: Map<List<Button>, List<Int>>,
    depth: Int,
    cache: MutableMap<List<Int>, List<Int>>
): List<Int> {
    if (cache[goal] != null) return cache[goal]!!
    if (goal.all { it == 0 }) return (Array(buttons.size) {0}).toList()
    if (goal.any { it < 0}) return (Array(buttons.size){100000}).toList()
    
    val parity = goal.map { it % 2 }
    val matchingButtonCombos = comboParities.keys.filter { comboParities[it]!! == parity }
    if (matchingButtonCombos.isEmpty()) return (Array(buttons.size){100000}).toList()
    val buttonPressCombos = matchingButtonCombos.map { combo ->
        val adjustedGoal = goal.mapIndexed { i, g -> (g - comboTotals[combo]!![i]) / 2 }
        solveRecursive(adjustedGoal, buttons, comboTotals, comboParities, depth + 1, cache).map { it * 2 }
            .mapIndexed { i, r -> if (buttons[i] in combo) r + 1 else r}
    }
    val bestResult = buttonPressCombos.minBy { it.sum() }
    cache[goal] = bestResult
    return bestResult
}

private fun getCombinationTotals(
    buttons: List<Button>,
    goal: List<Int>
): Map<List<Button>, List<Int>> {
    val combinations = getAllCombinations(buttons, 0)
    val comboParities = combinations.associateWith { combo ->
        val comboResult = Array(goal.size) { 0 }
        combo.forEach { button ->
            button.toggles.forEach { index ->
                comboResult[index] = (comboResult[index] + 1)
            }
        }
        comboResult.toList()
    }
    return comboParities
}

fun getAllCombinations(
    buttons: List<Button>,
    index: Int
): List<List<Button>> {
    if (index >= buttons.size) {
        return listOf(emptyList<Button>())
    }
    val combinations = getAllCombinations(buttons, index + 1)
    val newCombinations = combinations.map { it + buttons[index] }.toMutableList()
    newCombinations.addAll(combinations)
    newCombinations.add(listOf(buttons[index]))
    return newCombinations
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
    val part2Result = getPart2Result(machines)
    printer.endPart2()
    
    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getPart1Result(machines: List<Machine>): Int {
    return machines.sumOf { m -> m.getFewestPresses() }
}

fun getPart2Result(machines: List<Machine>): Int {
    return machines.sumOf { m -> m.getJoltagePresses() }
}
