package year24.day09

import AoCResultPrinter
import Reader
import java.util.*
import kotlin.concurrent.thread

const val year: Int = 24
const val day: Int = 9



fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)

    val disc = lines[0].trim().toCharArray().map { it.toString().toInt() }.toIntArray()

    printer.endSetup()

    var part1Result = -1L
    var part2Result = -1L

    //Do Part 1
    val part1Thread = thread {
        part1Result = getPart1Result(disc)
    }


    //Do Part 2
    val part2Thread = thread {
        part2Result = getPart2Result(disc)
    }

    part1Thread.join()
    printer.endPart1()

    part2Thread.join()
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}



fun getPart1Result(disc: IntArray): Long {
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

    return discArray.map { if (it == -1) 0 else it }
        .mapIndexed { ind, it -> (ind * it).toLong() }
        .sum()
}

data class FileSection(
    val id: Int,
    var firstIndex: Int,
    val length: Int,
)

fun getPart2Result(disc: IntArray): Long {
    // list of file sections
    val fileSections = mutableListOf<FileSection>()

    // map gapLength -> priorityQueue of indices for gaps of that size
    val gapIndexMap = mutableMapOf<Int, PriorityQueue<Int>>()
    (1 .. 9).forEach { gapIndexMap[it] = PriorityQueue() }

    // loop forward getting File Sections and Gaps
    var fileId = 0
    var isFile = true
    var currentIndex = 0
    for (sectionLength in disc) {
        if (isFile) {
            fileSections.add(FileSection(fileId, currentIndex, sectionLength))
            fileId++
        } else if (sectionLength != 0) {
            gapIndexMap[sectionLength]!!.add(currentIndex)
        }
        currentIndex += sectionLength
        isFile = !isFile
    }

    // loop backwards through FileSections, moving them as left as possible without fragmenting
    while (fileId > 0) {
        fileId--
        val fileIndex = fileSections[fileId].firstIndex
        val fileLength = fileSections[fileId].length

        val bestGap = gapIndexMap
            .filter{ entry -> entry.key >= fileLength }
            .filter{ entry -> entry.value.isNotEmpty() }
            .map { entry -> entry.key to entry.value }
            .minByOrNull { entry -> entry.second.peek() }

        if (bestGap == null) {continue}
        val gapLength = bestGap.first
        val gapIndex = bestGap.second.peek()
        if (gapIndex > fileIndex) {continue}
        bestGap.second.poll()

        fileSections[fileId].apply { firstIndex = gapIndex }

        val remainingGap = gapLength - fileLength
        if (remainingGap > 0) {
            val newGapIndex = gapIndex + fileLength
            gapIndexMap[remainingGap]!!.add(newGapIndex)
        }
    }

    return fileSections.sumOf { f ->
        (0 ..< f.length).sumOf { index ->
            (f.id.toLong() * (f.firstIndex + index))
        }
    }
}
