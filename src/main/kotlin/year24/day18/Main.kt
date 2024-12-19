package year24.day18

import AoCResultPrinter
import Grid
import GridCoordinate
import Reader
import java.util.PriorityQueue

const val year: Int = 24
const val day: Int = 18

const val GRID_SIZE = 71
val numBytes = 1024

lateinit var grid: Grid<Char>

fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)


    val gridList = mutableListOf<MutableList<Char>>();
    repeat(GRID_SIZE) {
        gridList.add(Array<Char>(GRID_SIZE){'.'}.toMutableList())
    }
    grid = Grid(gridList)

    val coordinates = lines.filter { it.isNotEmpty() }
        .map { it -> it.split(",") }
        .map {GridCoordinate(it[1].toInt(), it[0].toInt()) }

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(GridCoordinate(0,0), GridCoordinate(GRID_SIZE - 1, GRID_SIZE - 1), coordinates, numBytes)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(GridCoordinate(0,0), GridCoordinate(GRID_SIZE - 1, GRID_SIZE - 1), coordinates, numBytes)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

data class Node(
    val coordinate: GridCoordinate,
    val path: List<GridCoordinate>
)

fun getPart1Result(
    start: GridCoordinate,
    goal: GridCoordinate,
    coordinates: List<GridCoordinate>,
    numBytes: Int
): Int {
    (0..<numBytes).forEach { index -> grid.set(coordinates[index], '#') }

    val queue = PriorityQueue<Node>(compareBy<Node> { it.path.size })
    val bestDistances = mutableMapOf<GridCoordinate, Int>()
    var bestPath: List<GridCoordinate>? = null
    queue.add(Node(start, listOf()))
    bestDistances[start] = 0
    while (queue.isNotEmpty()) {
        val nextNode = queue.poll()
        if (nextNode.path.size >= (bestPath?.size ?: Int.MAX_VALUE)) {
            break
        }
        if (nextNode.coordinate == goal) {
            bestPath = nextNode.path
            continue
        }
        val neighbors = grid.getCardinalNeighbors(nextNode.coordinate)
            .filterNot { nextNode.coordinate in coordinates.subList(0, numBytes + 1) }
            .filterNot { nextNode.path.contains(it) }
        for (neighbor in neighbors) {
            if (!bestDistances.contains(neighbor)) {
                bestDistances[neighbor] = bestDistances[nextNode.coordinate]!! + 1
                queue.add(Node(neighbor, nextNode.path + neighbor))
            }
        }
    }
    return bestPath?.size ?: -1
}

fun getPart2Result(start: GridCoordinate,
                   goal: GridCoordinate,
                   coordinates: List<GridCoordinate>,
                   numBytes: Int): Pair<Int, Int> {
    val firstBadIndex = (numBytes ..< coordinates.size).first { n ->
        val result = getPart1Result(start, goal, coordinates, n)
        result < 0
    }
    return coordinates[firstBadIndex].col to coordinates[firstBadIndex].row
}
