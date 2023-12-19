package year23.day01

import java.io.BufferedReader



private val digitMap = mapOf(
    "1" to 1,
    "2" to 2,
    "3" to 3,
    "4" to 4,
    "5" to 5,
    "6" to 6,
    "7" to 7,
    "8" to 8,
    "9" to 9,
    "one" to 1,
    "two" to 2,
    "three" to 3,
    "four" to 4,
    "five" to 5,
    "six" to 6,
    "seven" to 7,
    "eight" to 8,
    "nine" to 9
)

fun getFirstDigit(inputString: String): Int {
    var stringCopy = inputString;
    var currentMatch: Int? = null
    for (key in digitMap.keys) {
        val startingIndex = stringCopy.indexOf(key)
        if (startingIndex == 0) return digitMap[key]!!
        if (startingIndex == -1) continue
        currentMatch = digitMap[key]!!
        stringCopy = stringCopy.slice(0.. startingIndex)
    }
    return currentMatch!!
}

fun getLastDigit(inputString: String): Int {
    var stringCopy = inputString;
    var currentMatch: Int? = null
    for (key in digitMap.keys) {
        val startingIndex = stringCopy.lastIndexOf(key)
        if (startingIndex == -1) continue
        if (startingIndex + key.length == stringCopy.length) return digitMap[key]!!
        currentMatch = digitMap[key]!!
        stringCopy = stringCopy.substring(startingIndex + 1)
    }
    return currentMatch!!
}

fun getCalibrationNumber(inputString: String) = 10 * getFirstDigit(inputString) + getLastDigit(inputString)

fun getSum(reader: BufferedReader): Int {
    return reader.lines()
        .mapToInt { getCalibrationNumber(it) }
        .sum()
}

fun main() {
    val startTime = System.currentTimeMillis();
    val reader = object {}.javaClass.getResourceAsStream("day1_input.txt")!!.bufferedReader()
    val sum = getSum(reader)
    val endTime = System.currentTimeMillis();
    print("Answer: $sum - Calculation time - ${endTime - startTime}ms")
}


