package year23.day08

fun main() {
    val startTime = System.currentTimeMillis()

    //Read input
    val reader = object {}.javaClass.getResourceAsStream("input.txt")!!.bufferedReader()
    val lines = reader.readLines()
    val directions = getDirections(lines[0])
    val nodes = getNodes(lines.subList(2, lines.size))
    val readEndTime = System.currentTimeMillis()

    //Do Part 1
    val part1Result = getPart1Result(directions, nodes)
    val part1EndTime = System.currentTimeMillis()

    //Do Part 2
    val part2Result = getPart2Result(directions, nodes)
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


enum class Direction { LEFT, RIGHT }

fun getDirections(firstLine: String): List<Direction> {
    return firstLine.toCharArray()
        .map { if (it == 'L') Direction.LEFT else Direction.RIGHT }
        .toList()
}

data class Node(
    val name: String,
    val leftNode: String,
    val rightNode: String
) {
    fun getNode(direction: Direction, nodes: Map<String, Node>): Node {
        return when(direction) {
            Direction.LEFT -> nodes[leftNode]!!
            Direction.RIGHT -> nodes[rightNode]!!
        }
    }
}

val linePattern = "([A-Z]{3}) = \\(([A-Z]{3}), ([A-Z]{3})\\)".toRegex()
fun getNodes(nodeLines: List<String>): Map<String, Node> {
    return nodeLines.filter { it.isNotEmpty() }
        .map { linePattern.find(it)!!.groups }
        .fold(mutableMapOf<String, Node>()) { map, it ->
            map[it[1]!!.value] = Node(it[1]!!.value, it[2]!!.value, it[3]!!.value)
            return@fold map
        }
}


fun getPart1Result(directions: List<Direction>, nodes: Map<String, Node>): Int {
    var directionsCounter = 0
    var currentNode = nodes["AAA"]!!
    while (currentNode != nodes["ZZZ"]) {
        currentNode = currentNode.getNode(directions[directionsCounter % directions.size], nodes)
        directionsCounter++
    }
    return directionsCounter
}

/**
 * Each starting node can only ever visit 1 Z node, and does so on a fixed period, so you use LCM here.
 */
fun getPart2Result(directions: List<Direction>, nodes: Map<String, Node>): Long {
    val startingNodes = nodes.values.filter { it.name.endsWith("A") }.toMutableList()
    var directionsCounter = 0
    var totalCounter = 0
    val firstZIndex = startingNodes.map{ -1 }.toMutableList()
    while (firstZIndex.any { it == -1 }) {
        val nextDirection = directions[(directionsCounter % directions.size)]
        directionsCounter = (directionsCounter + 1) % directions.size
        totalCounter++
        for (index in startingNodes.indices) {
            startingNodes[index] = startingNodes[index].getNode(nextDirection, nodes)
        }
        startingNodes.forEachIndexed{ index, item ->
            if (item.name.endsWith("Z") && firstZIndex[index] == -1) { firstZIndex[index] = totalCounter }}
    }
    return lcm(firstZIndex.map(Int::toLong).toList())
}

fun gcd(a: Long, b: Long): Long {
    return if (a == 0L) b else gcd(b % a, a)
}

fun lcm(numbers: List<Long>): Long {
    return numbers.fold(1L) {acc, item -> acc * (item / gcd(acc, item))}
}

