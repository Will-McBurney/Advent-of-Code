package year24.day19

import AoCResultPrinter
import Reader
const val year: Int = 24
const val day: Int = 19


lateinit var towels: List<String>

var cache = mutableMapOf<Int, Long>()


fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
    towels = lines[0].split(", ").sortedBy { it.length }.reversed()

    val patterns = lines.subList(2, lines.size).map { it.trim() }

    printer.endSetup()

    val results = patterns.map {
        cache = mutableMapOf<Int, Long>()
        val result = search(it, 0)
        result
    }

    val part1 = patterns.count {
        secondaryCache = mutableMapOf<Int, Boolean>()
        searchPart1(it, 0)
    }

    //Do Part 1
    val part1Result = getPart1Result(results)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(results)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

var secondaryCache = mutableMapOf<Int, Boolean>()

fun searchPart1(target: String, startingIndex: Int): Boolean {
    if (startingIndex == target.length) return true
    if (secondaryCache.containsKey(startingIndex)) return false
    towels.forEach { towel ->
        if (target.substring(startingIndex).startsWith(towel) && searchPart1(target, startingIndex + towel.length)) {
            return true
        }
    }
    secondaryCache[startingIndex] = false
    return false
}


fun search(target: String, startingIndex: Int): Long {
    if (startingIndex == target.length) return 1
    if (cache.containsKey(startingIndex)) return cache[startingIndex]!!
    val sum = towels.sumOf { towel ->
        if (target.substring(startingIndex).startsWith(towel)) {
            search(target, startingIndex + towel.length)
        } else 0
    }
    cache[startingIndex] = sum
    return sum
}



fun getPart1Result(results: List<Long>): Int {
    return results.count { it > 0 }
}

fun getPart2Result(results: List<Long>): Long {
    return results.sum()
}
