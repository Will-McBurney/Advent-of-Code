package year25.day08

import AoCResultPrinter
import Reader
import kotlin.math.sqrt

const val year: Int = 25
const val day: Int = 8

data class JunctionBox(
    val x: Long,
    val y: Long,
    val z: Long
) {
    fun distanceTo(other: JunctionBox): Double {
        return sqrt(
            (deltaSquared(this.x, other.x) +
                    deltaSquared(this.y, other.y) +
                    deltaSquared(this.z, other.z)).toDouble()
        )
    }

    private fun deltaSquared(a: Long, b: Long): Long = (b - a) * (b - a)
}

fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)

    val junctionBoxes = lines.map { it.trim().split(",")}
        .map { JunctionBox(it[0].toLong(), it[1].toLong(), it[2].toLong()) }

    val adjacency = mutableMapOf<Pair<Int, Int>, Double>()
    for (i in junctionBoxes.indices) {
        for (j in i + 1 ..< junctionBoxes.size) {
            val distance = junctionBoxes[i].distanceTo(junctionBoxes[j])
            adjacency[Pair(i, j)] = distance
        }
    }
    val sortedAdjacency = adjacency.keys.sortedBy { adjacency[it]!! }

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(sortedAdjacency, 1000)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(sortedAdjacency, junctionBoxes)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getPart1Result(sortedAdjacency: List<Pair<Int, Int>>, connections: Int): Long {
    val circuits = mutableListOf<MutableSet<Int>>()
    repeat(connections) { i ->
        val (firstIndex, secondIndex) = sortedAdjacency[i]
        val firstCircuit = circuits.indexOfFirst { it.contains(firstIndex) }
        val secondCircuit = circuits.indexOfFirst { it.contains(secondIndex) }
        if (firstCircuit == -1 && secondCircuit == -1) {
            circuits.add(mutableSetOf(firstIndex, secondIndex))
        } else if (firstCircuit == -1) {
            circuits[secondCircuit].add(firstIndex)
        } else if (secondCircuit == -1) {
            circuits[firstCircuit].add(secondIndex)
        } else if (firstCircuit != secondCircuit) {
            circuits[firstCircuit].addAll(circuits[secondCircuit])
            circuits.removeAt(secondCircuit)
        }
    }

    return circuits.map { it.size.toLong() }.sortedDescending().take(3)
        .reduce( Long::times )
}

fun getPart2Result(sortedAdjacency: List<Pair<Int, Int>>, junctionBoxes: List<JunctionBox>): Long {
    val circuits = mutableListOf<MutableSet<Int>>()
    var i = 0
    var firstIndex = -1
    var secondIndex = -1
    while(circuits.size != 1 || circuits[0].size != junctionBoxes.size) {
        val pair = sortedAdjacency[i]
        firstIndex = pair.first
        secondIndex = pair.second
        val firstCircuit = circuits.indexOfFirst { it.contains(firstIndex) }
        val secondCircuit = circuits.indexOfFirst { it.contains(secondIndex) }
        if (firstCircuit == -1 && secondCircuit == -1) {
            circuits.add(mutableSetOf(firstIndex, secondIndex))
        } else if (firstCircuit == -1) {
            circuits[secondCircuit].add(firstIndex)
        } else if (secondCircuit == -1) {
            circuits[firstCircuit].add(secondIndex)
        } else if (firstCircuit != secondCircuit) {
            circuits[firstCircuit].addAll(circuits[secondCircuit])
            circuits.removeAt(secondCircuit)
        }
        i++
    }
    return junctionBoxes[firstIndex].x * junctionBoxes[secondIndex].x
}
