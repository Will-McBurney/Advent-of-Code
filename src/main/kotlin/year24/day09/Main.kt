package year24.day09

import AoCResultPrinter
import Reader
import java.util.*

const val year: Int = 24
const val day: Int = 9



fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)

    val disc = lines[0].trim().toCharArray().map { it.toString().toInt() }

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(disc)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(disc)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getPart1Result(disc: List<Int>): Long {
    val discArray = Array<Int>(disc.sum()){-1}
    var fileID = 0
    var isFile = true
    var currentIndex = 0
    for (sectionLength in disc) {
        if (isFile) {
            (currentIndex ..< currentIndex + sectionLength).forEach {
                discArray[it] = fileID
            }
            fileID++
        }
        currentIndex += sectionLength
        isFile = !isFile
    }

    var firstEmptyIndex = discArray.indexOf(-1)
    var lastIndex = discArray.lastIndex
    while (lastIndex >= firstEmptyIndex) {
        if (discArray[lastIndex] != -1) {
            discArray[firstEmptyIndex] = discArray[lastIndex]
            discArray[lastIndex] = -1
        }
        while (discArray[firstEmptyIndex] != -1) {
            firstEmptyIndex++
        }
        while (discArray[lastIndex] == -1) {
            lastIndex--
        }
    }

    return discArray.filterNot{ it == -1}
        .mapIndexed{ind, it -> (ind * it).toLong()}
        .sum()
}

fun getPart2Result(disc: List<Int>): Long {
    // map fileID -> (index, length)
    val fileIndices = mutableListOf<Pair<Int, Int>>()

    // map gapLength -> List of indices in ascending order
    val gapIndexMap = mutableMapOf<Int, PriorityQueue<Int>>()
    (1 .. 9).forEach { gapIndexMap[it] = PriorityQueue() }

    val discArray = Array<Int>(disc.sum()){-1}
    var fileID = 0
    var isFile = true
    var currentIndex = 0
    for (sectionLength in disc) {
        if (isFile) {
            (currentIndex ..< currentIndex + sectionLength).forEach {
                discArray[it] = fileID
            }
            fileIndices.add(Pair(currentIndex, sectionLength))
            fileID++
        } else if (sectionLength != 0) {
            gapIndexMap[sectionLength]!!.add(currentIndex)
        }
        currentIndex += sectionLength
        isFile = !isFile
    }

    while (fileID > 0) {
        fileID--
        var startingIndex = fileIndices[fileID].first
        var length = fileIndices[fileID].second
        val leftMostFit = gapIndexMap
            .filter{ entry -> entry.key >= length }
            .filter{ entry -> entry.value.isNotEmpty() }
            .map { entry -> entry.key to entry.value }
            .minByOrNull { entry -> entry.second.peek() }

        if (leftMostFit == null) {continue}
        val gapLength = leftMostFit.first
        val gapIndex = leftMostFit.second.poll()
        leftMostFit.second.remove(gapIndex)
        if (gapIndex > startingIndex) {continue}
        fileIndices[fileID] = Pair(gapIndex, length)
        (gapIndex ..< gapIndex + length).forEach {
            discArray[it] = fileID
        }
        (startingIndex ..< startingIndex + length).forEach {
            discArray[it] = -1
        }

        val remainingGap = gapLength - length
        if (remainingGap > 0) {
            val newGapIndex = gapIndex + length
            gapIndexMap[remainingGap]!!.add(newGapIndex)
        }

    }


    return discArray.map{ if (it == -1) 0 else it}
        .mapIndexed{ind, it -> (ind * it).toLong()}
        .sum()
}
