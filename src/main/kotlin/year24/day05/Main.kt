package year24.day05

import AoCResultPrinter
import Reader
const val year: Int = 24
const val day: Int = 5



fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
    //ordered pairs
    val orderedPairs = mutableMapOf<Int, Set<Int>>()
    var lineNumber = 0
    while (lines[lineNumber].isNotBlank()) {
        val pageBefore = lines[lineNumber].substringBefore("|").toInt()
        val pageAfter = lines[lineNumber].substringAfter("|").toInt()
        orderedPairs[pageBefore] = (orderedPairs[pageBefore] ?: mutableSetOf<Int>()) + pageAfter
        if (!orderedPairs.containsKey(pageAfter)) {
            orderedPairs[pageAfter] = mutableSetOf<Int>()
        }
        lineNumber++
    }

    lineNumber++
    val updates = mutableListOf<List<Int>>()
    while (lineNumber < lines.size) {
        lines[lineNumber].trim()
            .split(",")
            .map(String::toInt)
            .also(updates::add)
        lineNumber++
    }

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(updates, orderedPairs)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(updates, orderedPairs)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun topographicalSort(orderedPairs: Map<Int, Set<Int>>): List<Int> {
    val map = orderedPairs.toMutableMap()
    val sortedKeys = mutableListOf<Int>()
    while (map.isNotEmpty()) {
        for (page in map.keys) {
            if (map.values.all { !it.contains(page) }) {
                sortedKeys.add(page)
                println(page)
            }
        }
        map.remove(sortedKeys.last())
    }
    return sortedKeys
}

fun getPart1Result(updates: MutableList<List<Int>>, orderedPairs: MutableMap<Int, Set<Int>>): Int {
    return updates.filter{ update -> isInOrder(update, orderedPairs) }
        .sumOf { update -> update[update.size / 2] }
}

fun getPart2Result(
    updates: MutableList<List<Int>>,
    orderedPairs: MutableMap<Int, Set<Int>>
): Int {
    val incorrectUpdates = updates.filterNot{ update -> isInOrder(update, orderedPairs) }
    return incorrectUpdates.map { update ->
        topographicalSort(orderedPairs.filter{ it.key in update})
    }
        .sumOf { update -> update[update.size / 2] }
}

fun isInOrder(
    update: List<Int>,
    orderedPairs: MutableMap<Int, Set<Int>>
): Boolean {
    for (i in 1 ..< update.size) {
        if (orderedPairs.containsKey(update[i])) {
            val afterPages = orderedPairs[update[i]]!!
            for (page in update.subList(0, i)) {
                if (afterPages.contains(page)) {
                    return false
                }
            }
        }
    }
    return true
}
