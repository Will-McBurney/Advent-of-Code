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
    val discArray = IntArray(disc.sum()){-1}
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

    return getCheckSum(discArray)
}

fun getPart2Result(disc: List<Int>): Long {
    // map fileID -> (index, length)
    val fileIndices = mutableListOf<Pair<Int, Int>>()

    // map gapLength -> List of indices in ascending order
    val gapIndexMap = mutableMapOf<Int, PriorityQueue<Int>>()
    (1 .. 9).forEach { gapIndexMap[it] = PriorityQueue() }

    val discArray = IntArray(disc.sum()){-1}

    var fileId = 0
    var isFile = true
    var currentIndex = 0
    for (sectionLength in disc) {
        if (isFile) {
            (currentIndex ..< currentIndex + sectionLength).forEach {
                discArray[it] = fileId
            }
            fileIndices.add(Pair(currentIndex, sectionLength))
            fileId++
        } else if (sectionLength != 0) {
            gapIndexMap[sectionLength]!!.add(currentIndex)
        }
        currentIndex += sectionLength
        isFile = !isFile
    }

    while (fileId > 0) {
        fileId--
        var fileIndex = fileIndices[fileId].first
        var fileLength = fileIndices[fileId].second
        val leftMostFit = gapIndexMap
            .filter{ entry -> entry.key >= fileLength }
            .filter{ entry -> entry.value.isNotEmpty() }
            .map { entry -> entry.key to entry.value }
            .minByOrNull { entry -> entry.second.peek() }

        if (leftMostFit == null) {continue}
        val gapLength = leftMostFit.first
        val gapIndex = leftMostFit.second.poll()
        leftMostFit.second.remove(gapIndex)
        if (gapIndex > fileIndex) {continue}
        fileIndices[fileId] = Pair(gapIndex, fileLength)

        discArray.swap(fileIndex, gapIndex, fileLength)

        val remainingGap = gapLength - fileLength
        if (remainingGap > 0) {
            val newGapIndex = gapIndex + fileLength
            gapIndexMap[remainingGap]!!.add(newGapIndex)
        }
    }

    return getCheckSum(discArray)
}

fun getCheckSum(discArray: IntArray): Long {
    return discArray.map { if (it == -1) 0 else it }
        .mapIndexed { ind, it -> (ind * it).toLong() }
        .sum()
}

fun IntArray.swap(fileIndex: Int, gapIndex: Int, fileLength: Int = 1) {
    (gapIndex ..< gapIndex + fileLength).forEach {
        this[it] = this[fileIndex]
    }
    (fileIndex..< fileIndex + fileLength).forEach {
        this[it] = -1
    }
}
