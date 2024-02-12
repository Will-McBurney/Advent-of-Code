package year17.day06

import AoCResultPrinter

const val year: Int = 17
const val day: Int = 6

fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
    val startingState = getStartingState(lines[0])

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(startingState)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(startingState)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getStartingState(line: String): IntArray {
    return line.split("\\s".toRegex())
        .filter { s -> s.isNotEmpty() }
        .map { s -> s.toInt() }
        .toIntArray()
}

fun getPart1Result(startingState: IntArray): Int {
    val currentState = startingState.copyOf()
    val stateValues: MutableSet<List<Int>> = mutableSetOf()
    var steps = 0
    do {
        stateValues.add(currentState.toList())
        val max = currentState.max()
        var index = currentState.indexOf(max)
        currentState[index] = 0
        repeat(max) {
            index = (index + 1) % currentState.size
            currentState[index]++
        }
        steps++
    } while(!stateValues.contains(currentState.toList()))
    return steps
}

fun getPart2Result(startingState: IntArray): Int {
    val currentState = startingState.copyOf()
    val stateValues: MutableMap<List<Int>, Int> = mutableMapOf()
    var steps = 0
    do {
        stateValues[currentState.toList()] = steps
        val max = currentState.max()
        var index = currentState.indexOf(max)
        currentState[index] = 0
        repeat(max) {
            index = (index + 1) % currentState.size
            currentState[index]++
        }
        steps++
    } while(!stateValues.contains(currentState.toList()))
    return steps - stateValues[currentState.toList()]!!
}
