package year19.day04

import AoCResultPrinter

const val year: Int = 19
const val day: Int = 4



fun main() {
    val printer = AoCResultPrinter(year, day)

    val arrays = (108457..562041).map{ it.toString().toCharArray() }

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(arrays)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(arrays)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getPart1Result(arrays: List<CharArray>) =
    arrays.count { isIncreasing(it) && hasDoubleDigits(it) }

fun getPart2Result(arrays: List<CharArray>) =
    arrays.count { isIncreasing(it) && hasDoubleDigits(it) && hasAtLeastOneDoubleDigits(it) }

fun isIncreasing(chars: CharArray): Boolean {
    for (i in 0..(chars.size - 2)) {
        if (chars[i] > chars[i + 1]) {
            return false
        }
    }
    return true
}

fun hasDoubleDigits(chars: CharArray): Boolean {
    for (i in 0..(chars.size - 2)) {
        if (chars[i] == chars[i + 1]) {
            return true
        }
    }
    return false
}

fun hasAtLeastOneDoubleDigits(chars: CharArray): Boolean {
    var index = 0
    while (index < chars.size - 1)
    {
        val targetChar = chars[index]
        if (targetChar == chars[index + 1]) {
            if (index == chars.size - 2) {
                return true
            }
            if (index < chars.size - 2 && targetChar != chars[index + 2]) {
                return true
            }
            while (targetChar == chars[index] && index < chars.size - 1) {
                index++
            }
            continue
        }
        index++
    }
    return false
}


