package year21.day06

import AoCResultPrinter
import Reader
const val year: Int = 21
const val day: Int = 6



fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
    val initialInput: List<Int> = lines[0].split(",").map { it.toInt() }
    // key - life stage - value - count
    val initialState: Map<Int, Long> = initialInput.fold(mutableMapOf<Int, Long>()) { map, item ->
        map[item] = 1L + map.getOrDefault(item, 0L)
        map
    }

    printer.endSetup()

    //Do Part 1
    val part1Result = getLanternFishCount(initialState, 80)
    printer.endPart1()

    //Do Part 2
    val part2Result = getLanternFishCount(initialState, 256)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getLanternFishCount(initialState: Map<Int, Long>, cycles: Int): Long {
    var currentState = initialState
    repeat(cycles) {
        val nextState: MutableMap<Int, Long> = mutableMapOf()
        val spawningFish = currentState[0] ?: 0
        nextState[6] = spawningFish
        nextState[8] = spawningFish
        (1..8).forEach { cycle ->
            nextState[cycle - 1] = (currentState[cycle] ?: 0) + (nextState[cycle - 1] ?: 0)
        }
        currentState = nextState
    }
    return currentState.values.sum()
}
