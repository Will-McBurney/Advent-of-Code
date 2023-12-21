package year23.day21

import java.util.*

fun main() {
    val startTime = System.nanoTime()

    //Read input
    val reader = object {}.javaClass.getResourceAsStream("input.txt")!!.bufferedReader()
    val lines = reader.readLines()
    val grid = getGrid(lines)

    val readEndTime = System.nanoTime()

    //Do Part 1
    val part1Result = getPart1Result(grid, 5000)
    val part1EndTime = System.nanoTime()

    //Do Part 2
    val part2Result = getPart2Result()
    val part2EndTime = System.nanoTime()

    //Display output
    println("Input read time: ${elapsedMicroSeconds(startTime, readEndTime)} μs\n")
    println("Part 1: %15d - Time %10d  μs"
        .format(part1Result, elapsedMicroSeconds(readEndTime, part1EndTime)))
    println("Part 2: %15d - Time %10d  μs\n"
        .format(part2Result, elapsedMicroSeconds(part1EndTime, part2EndTime)))
    println("Total time - ${elapsedMicroSeconds(startTime, part2EndTime)} μs")
}

fun getGrid(lines: List<String>): Grid {
    return Grid(lines.map { line -> line.toList() })
}

fun elapsedMicroSeconds(start: Long, end: Long): Long = (end - start) / 1000

fun getPart1Result(grid: Grid, steps: Int): Int {
    val evenParityCoordinates: MutableSet<Coordinate> = mutableSetOf()
    val oddParityCoordinates: MutableSet<Coordinate> = mutableSetOf()

    var isEvenParity = grid.startingPosition.isEvenParity()
    if (isEvenParity) {
        evenParityCoordinates.add(grid.startingPosition)
    } else {
        oddParityCoordinates.add(grid.startingPosition)
    }

    val queue: Queue<Coordinate> = LinkedList<Coordinate>(listOf(grid.startingPosition))
    val nextStepSet: MutableSet<Coordinate> = mutableSetOf()

    var stepCounter = 1

    while (stepCounter <= steps) {
        isEvenParity = !isEvenParity
        while (queue.isNotEmpty()) {
            val coordinate = queue.poll()
            val newNeighbors = grid.getEmptyNeighbors(coordinate)
                .filterNot (evenParityCoordinates::contains)
                .filterNot (oddParityCoordinates::contains)
            if (isEvenParity) {
                evenParityCoordinates.addAll(newNeighbors)
            } else {
                oddParityCoordinates.addAll(newNeighbors)
            }
            nextStepSet.addAll(newNeighbors)
        }
        queue.addAll(nextStepSet)
        nextStepSet.clear()

        stepCounter += 1
    }

    return if (isEvenParity)
        evenParityCoordinates.size
    else
        oddParityCoordinates.size
}

fun getPart2Result(): Int {
    return 0
}