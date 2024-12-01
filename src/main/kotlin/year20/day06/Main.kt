package year20.day06

import AoCResultPrinter
import Reader
const val year: Int = 20
const val day: Int = 6



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

class QuestionList(isExclusive: Boolean) {
    val questions = Array<Boolean>(26) { isExclusive }

    fun include(char: Char) {
        questions[char - 'a'] = true
    }

    fun exclude(char: Char) {
        questions[char - 'a'] = false
    }

    fun count(): Int = questions.count { it }
}

fun getGroupsPart1(lines: List<String>): List<QuestionList> {
    var currentQuestionList = QuestionList(false)
    val questionLists = mutableListOf<QuestionList>()
    lines.forEach { line ->
        if (line.isEmpty()) {
            questionLists.add(currentQuestionList)
            currentQuestionList = QuestionList(false)
            return@forEach
        }
        line.forEach { currentQuestionList.include(it) }
    }
    questionLists.add(currentQuestionList)
    return questionLists
}

const val letters = "abcdefghijklmnopqrstuvwxyz"

fun getGroupsPart2(lines: List<String>): List<QuestionList> {
    var currentQuestionList = QuestionList(true)
    val questionLists = mutableListOf<QuestionList>()
    lines.forEach { line ->
        if (line.isEmpty()) {
            questionLists.add(currentQuestionList)
            currentQuestionList = QuestionList(true)
            return@forEach
        }
        letters.filterNot { line.contains(it) }
            .forEach { currentQuestionList.exclude(it) }
    }
    questionLists.add(currentQuestionList)
    return questionLists
}

fun getPart1Result(lines: List<String>): Int {
    val groups = getGroupsPart1(lines)
    return groups.sumOf { it.count() }
}

fun getPart2Result(lines: List<String>): Int {

    val groups = getGroupsPart2(lines)
    return groups.sumOf { it.count() }
}
