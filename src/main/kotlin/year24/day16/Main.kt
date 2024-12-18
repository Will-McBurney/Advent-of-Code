package year24.day16

import AoCResultPrinter
import CardinalDirection
import Grid
import GridCoordinate
import Reader
import java.util.PriorityQueue
import kotlin.math.sign

const val year: Int = 24
const val day: Int = 16

const val WALL = '#'

lateinit var grid: Grid<Char>

fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
    grid = Grid(lines.map { it.toCharArray().toMutableList() }.toMutableList())

    val start = grid.coordinates.single{ c -> grid.get(c) == 'S'}
    val end = grid.coordinates.single{ c -> grid.get(c) == 'E'}
    val startingDirection = CardinalDirection.RIGHT

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(start, end, startingDirection)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result()
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

data class Node(
    val coordinate: GridCoordinate,
    val direction: CardinalDirection,
    val path: List<GridCoordinate>,
    val weight: Long
)

const val FORWARD_COST = 1
const val TURN_COST = 1000
var part2Answer = -1


fun getPart1Result(start: GridCoordinate, end: GridCoordinate, startingDirection: CardinalDirection): Long {
    val weights = mutableMapOf<Pair<GridCoordinate, CardinalDirection>, Long>()
    val queue = PriorityQueue<Node> { a, b -> (a.weight - b.weight).sign }
    val startNode = Node(start, startingDirection, listOf(start), 0)
    weights[start to startingDirection] = 0
    queue.add(startNode)
    val bestPathCoordinates: MutableSet<GridCoordinate> = mutableSetOf()
    var bestWeight = Long.MAX_VALUE


    while (queue.isNotEmpty() && queue.peek().weight < bestWeight) {
        val nextNode = queue.poll()
        // if no longer the best weight, then stop this path
        if (nextNode.weight > weights[nextNode.coordinate to nextNode.direction]!!) {
            continue
        }
        if (nextNode.coordinate == end) {
            bestPathCoordinates.addAll(nextNode.path)
            bestWeight = weights[nextNode.coordinate to nextNode.direction]!!
            continue
        }
        val forward = nextNode.coordinate.getMovement(nextNode.direction)
        if (grid.get(forward) != WALL) {
            val forwardNode = Node(forward, nextNode.direction, nextNode.path + forward, nextNode.weight + FORWARD_COST)
            if (!weights.containsKey(forwardNode.coordinate to forwardNode.direction) ||
                weights[forwardNode.coordinate to forwardNode.direction]!! >= forwardNode.weight)
            {
                weights[forwardNode.coordinate to forwardNode.direction] = forwardNode.weight
                queue.add(forwardNode)
            }
        }
        val turns = listOf(nextNode.direction.clockwise, nextNode.direction.counterClockwise)
        for (turn in turns) {
            val turnNode = Node(nextNode.coordinate, turn, nextNode.path, nextNode.weight + TURN_COST)
            if (!weights.containsKey(turnNode.coordinate to turnNode.direction) ||
                weights[turnNode.coordinate to turnNode.direction]!! >= turnNode.weight)
            {
                weights[turnNode.coordinate to turnNode.direction] = turnNode.weight
                queue.add(turnNode)
            }
        }
    }

    part2Answer = bestPathCoordinates.size
    return bestWeight
}

fun getPart2Result(): Int {
    return part2Answer
}
