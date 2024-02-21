package year22.day09

import AoCResultPrinter
import CardinalDirection
import GridCoordinate
import Reader

const val year: Int = 22
const val day: Int = 9


fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
    val headMoves = getHeadMoves(lines)

    printer.endSetup()

    //Do Part 1
    val part1Result = getTailLocationsCount(headMoves, 2)
    printer.endPart1()

    //Do Part 2
    val part2Result = getTailLocationsCount(headMoves, 10)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

data class HeadMove(val cardinalDirection: CardinalDirection, val distance: Int)

fun getHeadMoves(lines: List<String>): List<HeadMove> {
    return lines.map { line -> getHeadMove(line) }
}

fun getHeadMove(line: String): HeadMove {
    val tokens: List<String> = line.split(" ")
    val cardinalDirection = when (tokens[0]) {
        "U" -> CardinalDirection.UP
        "L" -> CardinalDirection.LEFT
        "D" -> CardinalDirection.DOWN
        "R" -> CardinalDirection.RIGHT
        else -> throw IllegalArgumentException("Bad line format - incorrect direction: $line")
    }
    val distance: Int = tokens[1].toInt()
    return HeadMove(cardinalDirection, distance)
}


fun getTailLocationsCount(headMoves: List<HeadMove>, ropeLength: Int): Int {
    val startingCoordinate = GridCoordinate(0, 0)
    val ropeCoordinates: MutableList<GridCoordinate> = mutableListOf()
    repeat(ropeLength) {
        ropeCoordinates.add(startingCoordinate)
    }

    val tailHistory: MutableSet<GridCoordinate> = mutableSetOf(startingCoordinate)

    headMoves.forEach { move ->
        repeat(move.distance) {
            ropeCoordinates[0] = ropeCoordinates[0].getMovement(move.cardinalDirection)

            for (followerIndex in 1..<ropeCoordinates.size) {
                val newFollowerCoordinates = simulateRopeFollowing(
                    leader = ropeCoordinates[followerIndex  - 1],
                    follower = ropeCoordinates[followerIndex]
                )
                if (ropeCoordinates[followerIndex] == newFollowerCoordinates) { // if this knot didn't move
                    break // no later knows moved
                }
                ropeCoordinates[followerIndex] = newFollowerCoordinates
            }
            tailHistory.add(ropeCoordinates.last())
        }
    }
    return tailHistory.size
}

private fun simulateRopeFollowing(leader: GridCoordinate, follower: GridCoordinate): GridCoordinate {
    return if (follower == leader || follower.getOrdinalNeighbors().contains(leader)) {
        follower
    } else if (follower.row + 2 == leader.row && follower.col == leader.col) {
        follower.getMovement(CardinalDirection.DOWN)
    } else if (follower.row - 2 == leader.row && follower.col == leader.col) {
        follower.getMovement(CardinalDirection.UP)
    } else if (follower.row == leader.row && follower.col + 2 == leader.col) {
        follower.getMovement(CardinalDirection.RIGHT)
    } else if (follower.row == leader.row && follower.col - 2 == leader.col) {
        follower.getMovement(CardinalDirection.LEFT)
    } else if (leader.row > follower.row && leader.col > follower.col) {
        follower.getMovement(OrdinalDirection.DOWN_RIGHT)
    } else if (leader.row < follower.row && leader.col > follower.col) {
        follower.getMovement(OrdinalDirection.UP_RIGHT)
    } else if (leader.row > follower.row && leader.col < follower.col) {
        follower.getMovement(OrdinalDirection.DOWN_LEFT)
    } else if (leader.row < follower.row && leader.col < follower.col) {
        follower.getMovement(OrdinalDirection.UP_LEFT)
    } else {
        throw IllegalStateException("Not possible")
    }
}


