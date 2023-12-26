package year23.day25

import AoCResultPrinter
import Reader
import java.util.*

const val year: Int = 23
const val day: Int = 25

fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
    val edges = getEdges(lines)

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(edges)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result()
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getEdges(lines: List<String>): Map<String, Set<String>> {
    val output: MutableMap<String, MutableSet<String>> = mutableMapOf()
    lines.forEach { line ->
        val v0 = line.substringBefore(":").trim()
        if (!output.containsKey(v0)) output[v0] = mutableSetOf()
        line.substringAfter(":").trim()
            .split(" ")
            .forEach { v1 ->
                if (!output.containsKey(v1)) output[v1] = mutableSetOf()
                output[v0]!!.add(v1)
                output[v1]!!.add(v0)
            }
    }
    return output
}



fun getPart1Result(edges: Map<String, Set<String>>): Int {
    val pathCounts: MutableMap<Pair<String, String>, Int> = mutableMapOf()
    var count = 0
    // used to find the most used edges, which I manually remove one at a time
    repeat(5000) {
        val startingVertex = edges.keys.random()
        val endingVertex = edges.keys.random()
        if (startingVertex == endingVertex) {
            count++
            return@repeat
        }
        val path = getPath(startingVertex, endingVertex, edges) ?: return@repeat
        count++
        path.forEach { pathCounts[it] = pathCounts.getOrDefault(it, 0) + 1}
    }
    println(pathCounts.entries.sortedByDescending { it.value })
    println(count) // if prints 5000, then the graphs are not disconnected

    repeat(10) {
        val startingVertex = edges.keys.random()
        println(getVisitablePoints(startingVertex, edges).size) //get the size of the two graphs
    }
    return 0

}

fun getPart2Result(): Int {
    return 0
}

fun getPath(startingVertex: String, endingVertex: String, edges: Map<String, Set<String>>): List<Pair<String, String>>? {
    val visited: MutableSet<String> = mutableSetOf(startingVertex)
    val queue: Queue<String> = LinkedList<String>()
    val paths: MutableMap<String, List<Pair<String, String>>> = mutableMapOf()

    queue.add(startingVertex)
    paths[startingVertex] = emptyList()

    while (queue.isNotEmpty()) {
        val next = queue.poll()

        edges[next]!!.filter { successor -> !visited.contains(successor) }
            .forEach { successor ->
                paths[successor] = paths[next]!! + Pair(next, successor)
                if (successor == endingVertex) {
                    return paths[successor]
                }
                visited.add(successor)
                queue.add(successor)
            }
    }
    return null
}

fun getVisitablePoints(startingVertex: String, edges: Map<String, Set<String>>): Set<String> {
    val visited: MutableSet<String> = mutableSetOf(startingVertex)
    val stack: Stack<String> = Stack<String>()
    stack.add(startingVertex)

    while (stack.isNotEmpty()) {
        val next = stack.pop()

        edges[next]!!.filter { successor -> !visited.contains(successor) }
            .forEach { successor ->
                visited.add(successor)
                stack.add(successor)
            }
    }

    return visited
}
