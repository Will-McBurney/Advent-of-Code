package year24.day23

import AoCResultPrinter
import Reader
import java.util.Stack

const val year: Int = 24
const val day: Int = 23



fun main() {
    val printer = AoCResultPrinter(year, day)
    
    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
    
    val networkMap: MutableMap<String, Set<String>> = mutableMapOf()
    lines.map { it.trim().split("-") }
        .forEach {
            networkMap[it[0]] = (networkMap[it[0]] ?: mutableSetOf()) + it[1]
            networkMap[it[1]] = (networkMap[it[1]] ?: mutableSetOf()) + it[0]
        }
    
    printer.endSetup()
    
    //Do Part 1
    val tripleCliques = getTripleCliques(networkMap)
    
    
    val part1Result = tripleCliques.size
    printer.endPart1()
    
    //Do Part 2
    val part2Result = getPart2Result(networkMap)
    printer.endPart2()
    
    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getTripleCliques(networkMap: MutableMap<String, Set<String>>): Set<Set<String>> {
    val cliques = networkMap.entries.filter { it.key.startsWith("t") }
        .map { a ->
            a.value.map { b ->
                networkMap[b]!!.filter { a.value.contains(it) }
                    .map { setOf(a.key, b, it) }
            }.flatten()
        }
        .flatten()
        .toSet()
    return cliques
}

fun getPart2Result(networkMap: MutableMap<String, Set<String>>): String {
    return getLargestClique(networkMap).sorted().joinToString(",")
}

fun getLargestClique(
    networkMap: MutableMap<String, Set<String>>,
): Set<String> {
    var initialCliques = networkMap.keys.map { setOf(it) }
    var largestClique = emptySet<String>()
    val stack = Stack<Set<String>>()
    val stackSet = mutableSetOf<Set<String>>()
    initialCliques.forEach { stack.push(it) }
    while (stack.isNotEmpty()) {
        val clique = stack.pop()
        if (clique.size > largestClique.size) {
            largestClique = clique
        }
        val newCliques = networkMap.entries.filterNot{ clique.contains(it.key) }
            .filter { (_, value) -> value.containsAll(clique) }
            .map { clique + it.key }
        
        newCliques.filterNot { stackSet.contains(it) }
            .forEach {
                stack.add(it)
                stackSet.add(it)
            }
    }
    return largestClique
}
