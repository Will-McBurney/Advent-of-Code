package year23.day17

import java.util.*

fun main() {
    val startTime = System.currentTimeMillis()

    //Read input
    val reader = object {}.javaClass.getResourceAsStream("input.txt")!!.bufferedReader()
    val lines = reader.readLines()
    val grid = buildGrid(lines)
    val readEndTime = System.currentTimeMillis()

    //Do Part 1
    val part1Result = getPart1Result(grid)
    val part1EndTime = System.currentTimeMillis()

    //Do Part 2
    val part2Result = getPart2Result(grid)
    val part2EndTime = System.currentTimeMillis()

    //Display output
    println(
        """
        |Read Time: %10d ms
        |
        |Part One:  %10d - Time %6d ms
        |Part Two:  %10d - Time %6d ms
        |
        |Total time - ${part2EndTime - startTime}ms
        |""".trimMargin().format(
            readEndTime - startTime,
            part1Result,
            part1EndTime - readEndTime,
            part2Result,
            part2EndTime - part1EndTime
        )
    )
}

fun buildGrid(lines: List<String>): Grid {
    return Grid(
        lines.map { line ->
            line.map { char -> char - '0' }
        }
    )
}


fun getPart1Result(grid: Grid): Int {
    return djikstra(grid, 1, 3)
}


fun getPart2Result(grid: Grid): Int  {
    return  djikstra(grid, 4, 10)
}

data class Node(val coordinate: Coordinate, val direction: Direction)

fun djikstra(grid: Grid, minEdgeLength: Int, maxEdgeLength: Int): Int {
    val startingPoint = Node(Coordinate(0, 0), Direction.NONE)
    val endingCoordinate = Coordinate(grid.numRows - 1, grid.numCols - 1)

    val bestWeights: MutableMap<Node, Int> = mutableMapOf(startingPoint to 0)
    val previousPath: MutableMap<Node, List<Coordinate>> = mutableMapOf(startingPoint to emptyList())

    val queue: PriorityQueue<Node> = PriorityQueue<Node>(Comparator.comparing { node -> bestWeights[node]!! })
    queue.add(startingPoint)

    while (queue.isNotEmpty()) {
        val nextNode = queue.poll()
        if (nextNode.coordinate == endingCoordinate) {
            return bestWeights[nextNode]!!
        }
        val nextEdges = grid.getEdges(nextNode.coordinate, minEdgeLength, maxEdgeLength)
        for (edge in nextEdges) {
            if (edge.second in previousPath[nextNode]!!) { continue } //skip if our path has already visited this node
            val newNodeDirection = nextNode.coordinate.getDirectionTo(edge.second)
            val newNode = Node(edge.second, newNodeDirection)
            val newNodeWeight = bestWeights[nextNode]!! +
                    grid.getWeight(Pair(nextNode.coordinate, newNode.coordinate)) +
                    getDirectionWeight(nextNode.direction, newNodeDirection)
            if (!bestWeights.containsKey(newNode) || bestWeights[newNode]!! > newNodeWeight) {
                bestWeights[newNode] = newNodeWeight
                previousPath[newNode] = previousPath[nextNode]!! +
                        nextNode.coordinate.getPathCoordinates(newNode.coordinate)
                queue.add(newNode)
            }
        }
    }
    return -1
}

const val VERY_LARGE_WEIGHT = 10000
fun getDirectionWeight(a: Direction?, b: Direction): Int {
    if (a == null) return 0
    if (a == b) return VERY_LARGE_WEIGHT
    return 0
}
