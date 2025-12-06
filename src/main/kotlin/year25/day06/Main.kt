package year25.day06

import AoCResultPrinter
import Reader
const val year: Int = 25
const val day: Int = 6

class MathProblem{
    val numbers: MutableList<Long> = mutableListOf()
    var operation: Char = '.'
    
    val result: Long
        get() = when(operation) {
            '+' -> numbers.reduce(Long::plus)
            '*' -> numbers.reduce(Long::times)
            else -> throw IllegalStateException("Bad operator: $operation")
        }
}

fun main() {
    val printer = AoCResultPrinter(year, day)
    
    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
   

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(lines)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(lines)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getPart1Result(lines: List<String>): Long {
    val numProblems = lines.first().trim().replace("\\s+".toRegex(), " ").split(" ").size
    val problems = Array(numProblems){ MathProblem() }
    lines.subList(0, lines.size - 1).forEach { line ->
        line.trim().replace("\\s+".toRegex(), " ")
            .split(" ")
            .forEachIndexed { i, str ->
                problems[i].numbers.add(str.toLong())
            }
    }
    lines.last().trim().replace("\\s+".toRegex(), " ").split(" ")
        .forEachIndexed { i, str ->
            problems[i].operation = str[0]
        }
    return problems.sumOf { it.result }.also { println(it) }
}

fun getPart2Result(lines: List<String>): Long {
    val grid = lines.subList(0, lines.size - 1).map { line ->
        line.toList()
    }
    
    val columnSplits = grid.first().indices.filter { columnIndex ->
        isColumnEmpty(columnIndex, grid)
    } + grid.maxOf { it.size }
    
    val problems = Array(columnSplits.size){ MathProblem() }
    
    var startingColumn = 0
    columnSplits.forEachIndexed { i, nextSplit ->
        (startingColumn..< nextSplit).forEach { columnIndex ->
            problems[i].numbers.add(getNumberFromColumn(columnIndex, grid))
            startingColumn = nextSplit + 1
        }
    }
    val operations = lines.last().trim().replace("\\s+".toRegex(), " ")
        .split(" ")
        .map{ it[0] }
    operations.forEachIndexed { i, op -> problems[i].operation = op }
    
    return problems.sumOf { it.result }.also { println(it) }
}

fun isColumnEmpty(columnIndex: Int, grid: List<List<Char>>) =
    grid.all { it[columnIndex] == ' ' }

fun getNumberFromColumn(columnIndex: Int, grid: List<List<Char>>): Long {
    return grid.indices.map { rowIndex -> grid[rowIndex][columnIndex] }
        .joinToString("") { it.toString() }
        .trim()
        .toLong()
}