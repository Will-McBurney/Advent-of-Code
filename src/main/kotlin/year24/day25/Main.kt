package year24.day25

import AoCResultPrinter
import Reader
const val year: Int = 24
const val day: Int = 25



fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)

    val keys = mutableListOf<List<Int>>()
    val locks = mutableListOf<List<Int>>()
    
    lines.windowed(7, step = 8) { window ->
        println(window)
        if (window[0] == "#####") { // is lock
            locks.add(
                (0..4).map { index -> window.map { it[index] }.indexOf('.')-1 }
            )
        } else {
            keys.add(
                (0..4).map { index -> 6 - window.map { it[index] }.indexOf('#') }
            )
        }
    }
    println(locks)
    println(keys)
    
    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(locks, keys)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result()
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getPart1Result(locks: MutableList<List<Int>>, keys: MutableList<List<Int>>): Int {
    return locks.sumOf { lock ->
        keys.count { key ->
            (0..4).all { key[it] + lock[it] <= 5 }
        }
    }
}

fun getPart2Result(): Int {
    return 0
}
