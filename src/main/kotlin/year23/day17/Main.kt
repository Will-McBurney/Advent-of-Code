package year23.day17

import year23.day16.Direction

fun main() {
    val startTime = System.currentTimeMillis()

    //Read input
    val reader = object {}.javaClass.getResourceAsStream("input.txt")!!.bufferedReader()
    val lines = reader.readLines()
    val grid = buildGrid(lines)
    val readEndTime = System.currentTimeMillis()

    //Do Part 1
    val part1Result = 0 //getPart1Result(grid)
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

data class Node(val coordinate: Coordinate, val direction: Direction)
fun getPart1Result(grid: Grid): Int {
    val startingPoint = Node(Coordinate(0, 0), Direction.NONE)
    val endingCoordinate = Coordinate(grid.numRows - 1, grid.numCols - 1)
    val out: MutableSet<Node> = mutableSetOf()
    val bestWeights: MutableMap<Node, Int> = mutableMapOf(startingPoint to 0)
    val previousPath: MutableMap<Node, List<Coordinate>> = mutableMapOf(startingPoint to emptyList<Coordinate>())
    val queue: MutableList<Node> = mutableListOf(startingPoint)
    while (queue.isNotEmpty()) {
        queue.sortBy { node -> bestWeights[node]!! }
        val node = queue.removeAt(0)
        if (node.coordinate == endingCoordinate) {
            return bestWeights[node]!!
        }
        val edges = grid.getEdgesPart1(node.coordinate)
        for (edge in edges) {
            if (edge.second in previousPath[node]!!) { continue }
            val newNodeDirection = node.coordinate.getDirectionTo(edge.second)
            val newNode = Node(edge.second, newNodeDirection)
            val newNodeWeight = bestWeights[node]!! + grid.getWeight(Pair(node.coordinate, newNode.coordinate)) + getDirectionWeight(node.direction, newNodeDirection)
            if (!bestWeights.containsKey(newNode) || bestWeights[newNode]!! > newNodeWeight) {
                bestWeights[newNode] = newNodeWeight
                previousPath[newNode] = previousPath[node]!! + node.coordinate.getPathCoordinates(newNode.coordinate)
                queue.add(newNode)
            }
        }
        out.add(node)
    }
    return -1
}
fun getDirectionWeight(a: Direction?, b: Direction): Int {
    return if (a == null) 0 else
        if (a == b) 100 else 0
}

fun getPart2Result(grid: Grid): Int {
    val startingPoint = Node(Coordinate(0, 0), Direction.NONE)
    val endingCoordinate = Coordinate(grid.numRows - 1, grid.numCols - 1)
    val out: MutableSet<Node> = mutableSetOf()
    val bestWeights: MutableMap<Node, Int> = mutableMapOf(startingPoint to 0)
    val previousPath: MutableMap<Node, List<Coordinate>> = mutableMapOf(startingPoint to emptyList<Coordinate>())
    val queue: MutableList<Node> = mutableListOf(startingPoint)
    while (queue.isNotEmpty()) {
        queue.sortBy { node -> bestWeights[node]!! }
        val node = queue.removeAt(0)
        if (node.coordinate == endingCoordinate) {
            return bestWeights[node]!!
        }
        val edges = grid.getEdgesPart2(node.coordinate)
        for (edge in edges) {
            if (edge.second in previousPath[node]!!) { continue }
            val newNodeDirection = node.coordinate.getDirectionTo(edge.second)
            val newNode = Node(edge.second, newNodeDirection)
            val newNodeWeight = bestWeights[node]!! + grid.getWeight(Pair(node.coordinate, newNode.coordinate)) + getDirectionWeight(node.direction, newNodeDirection)
            if (!bestWeights.containsKey(newNode) || bestWeights[newNode]!! > newNodeWeight) {
                bestWeights[newNode] = newNodeWeight
                previousPath[newNode] = previousPath[node]!! + node.coordinate.getPathCoordinates(newNode.coordinate)
                queue.add(newNode)
            }
        }
        out.add(node)
    }
    return -1
}
