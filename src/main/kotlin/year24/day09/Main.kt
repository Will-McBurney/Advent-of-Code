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
    val fileIndexMap = mutableMapOf<Int, Pair<Int, Int>>()

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
            fileIndexMap[fileID] = Pair(currentIndex, sectionLength)
            fileID++
        } else if (sectionLength != 0) {
            gapIndexMap[sectionLength]!!.add(currentIndex)
        }
        currentIndex += sectionLength
        isFile = !isFile
    }

    while (fileID > 0) {
        fileID--
        var startingIndex = fileIndexMap[fileID]!!.first
        var length = fileIndexMap[fileID]!!.second
        val leftMostFit = gapIndexMap
            .filter{ entry -> entry.key >= length }
            .filter{ entry -> entry.value.isNotEmpty() }
            .map { entry -> entry.key to entry.value.poll() }
            .minByOrNull { entry -> entry.second }

        if (leftMostFit == null) {continue}
        val (gapLength, gapIndex) = leftMostFit
        if (gapIndex > startingIndex) {continue}
        fileIndexMap[fileID] = Pair(gapIndex, length)
        (gapIndex ..< gapIndex + length).forEach {
            discArray[it] = fileID
        }
        gapIndexMap[gapLength]!!.remove(gapIndex)

        val remainingGap = gapLength - length
        if (remainingGap > 0) {
            val newGapIndex = gapIndex + length
            gapIndexMap[remainingGap]!!.add(newGapIndex)
        }

        var leftGapStart = startingIndex
        var rightGapStart = startingIndex + length
        var leftGapLength = 0
        var rightGapLength = 0
        while (leftGapStart > 0 && discArray[leftGapStart - 1] == -1) {
            leftGapLength++
            leftGapStart--
        }
        while (rightGapStart + rightGapLength < discArray.size  && discArray[rightGapStart + rightGapLength] == -1) {
            rightGapLength++
        }
        if (leftGapLength > 0) gapIndexMap[leftGapLength]!!.remove(leftGapStart)
        if (rightGapLength > 0) gapIndexMap[rightGapLength]!!.remove(rightGapStart)

        val fullGapLength = length + leftGapLength + rightGapLength
        for (i in leftGapStart until leftGapStart + fullGapLength) {
            discArray[i] = -1
        }
        gapIndexMap.putIfAbsent(fullGapLength, PriorityQueue())
        gapIndexMap[fullGapLength]!!.add(leftGapStart)
    }


    return discArray.map{ if (it == -1) 0 else it}
        .mapIndexed{ind, it -> (ind * it).toLong()}
        .sum()
}
