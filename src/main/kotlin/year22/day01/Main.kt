package year22.day01

import AoCResultPrinter
import Reader

const val year: Int = 22
const val day: Int = 1

fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename).map { it.trim() }
    val caloriesList: List<Int> = getCalorieTotals(lines)

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(caloriesList)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(caloriesList)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getCalorieTotals(lines: List<String>): List<Int> {
    val caloriesList: MutableList<Int> = mutableListOf()
    var currentCalories = 0
    for (line in lines) {
        if (line.isEmpty()) {
            caloriesList.add(currentCalories)
            currentCalories = 0
        } else {
            currentCalories += line.toInt()
        }
    }
    caloriesList.add(currentCalories)
    return caloriesList
}
fun getPart1Result(caloriesList: List<Int>): Int {
    return caloriesList.max()
}

fun getPart2Result(caloriesList: List<Int>): Int {
    return caloriesList.sortedDescending()
        .take(3)
        .sum()
}