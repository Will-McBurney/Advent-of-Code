package year23.day21

import java.util.*

fun main() {
    val startTime = System.nanoTime()

    //Read input
    val reader = object {}.javaClass.getResourceAsStream("input.txt")!!.bufferedReader()
    val lines = reader.readLines()
    val grid = getGrid(lines)

    val readEndTime = System.nanoTime()

    val distanceMap = getGridDistancesMap(grid, grid.startingPosition)
    //Do Part 1
    val part1Result = getPart1Result(distanceMap, 64)
    val part1EndTime = System.nanoTime()

    //Part 2 assertions, as this doesn't work for a general solution
    //starting row and column must be empty, and edges and corners must be empty

    assert(grid.numRows == grid.numCols) //square

    //edges are empty
    assert(grid.getEmptyRows().contains(0))
    assert(grid.getEmptyRows().contains(grid.numRows - 1))
    assert(grid.getEmptyCols().contains(0))
    assert(grid.getEmptyCols().contains(grid.numRows - 1))

    //starting row/column empty
    assert(grid.getEmptyRows().contains(grid.startingPosition.row))
    assert(grid.getEmptyCols().contains(grid.startingPosition.column))

    //Do Part 2
    val part2Result = getPart2Result(grid.numRows, distanceMap, 26501365)
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

fun getGridDistancesMap(grid: Grid, start: Coordinate): Map<Coordinate, Int> {
    val queue: Queue<Coordinate> = LinkedList<Coordinate>(listOf(start))
    val visited: MutableSet<Coordinate> = mutableSetOf()
    val nextStepSet: MutableSet<Coordinate> = mutableSetOf()

    var stepCounter = 0

    while (queue.isNotEmpty()) {
        stepCounter += 1
        visited.addAll(queue)
        while (queue.isNotEmpty()) {
            val coordinate = queue.poll()
            val newNeighbors = grid.getEmptyNeighbors(coordinate)
                .filterNot(visited::contains)
            newNeighbors.forEach {
                grid.distanceMap[it] = stepCounter
            }
            nextStepSet.addAll(newNeighbors)
        }
        queue.addAll(nextStepSet)
        nextStepSet.clear()
    }
    return grid.distanceMap
}

fun getPart1Result(distanceMap: Map<Coordinate, Int>, steps: Int): Int {
    return distanceMap.values.filter { distance -> distance % 2 == steps % 2 }
        .count { distance -> distance <= steps }
}

// See: https://github.com/villuna/aoc23/wiki/A-Geometric-solution-to-advent-of-code-2023,-day-21 for explanation
fun getPart2Result(gridSize: Int, distanceMap: Map<Coordinate, Int>, steps: Long): Long {
    val halfStep = gridSize/2
    val wholeStep = gridSize
    assert(steps % gridSize == halfStep.toLong())

    val n = (steps - halfStep)/wholeStep

    val fullEvenParity = distanceMap.values.count{ d -> d % 2 == 0}
    val fullOddParity = distanceMap.values.count{ d -> d % 2 == 1}

    val cornersEvenParity = distanceMap.values.count{ d -> d > halfStep && d % 2 == 0}
    val cornersOddParity = distanceMap.values.count{ d -> d > halfStep && d % 2 == 1}

    val oddBoxes = (n+1) * (n+1)
    val evenBoxes = n * n

    return evenBoxes * fullEvenParity + oddBoxes * fullOddParity - (n+1) * cornersOddParity + n * cornersEvenParity
}