package year25.day11

import AoCResultPrinter
import Reader
import java.util.*

const val year: Int = 25
const val day: Int = 11

data class Server(
    val id: String,
    val connections: List<String>
)

fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
    val servers = lines.map { line ->
        Server(
            id = line.substringBefore(":").trim(),
            connections = line.substringAfter(": ").trim().split(" ")
        )
    }.associateBy { it.id }
    
    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(servers)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(servers)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getPart1Result(servers: Map<String, Server>): Int {
    val start = servers["you"]!!
    val stack = Stack<Server>().apply { this.add(start) }
    var outCount = 0
    while (stack.isNotEmpty()) {
        val node = stack.pop()
        node.connections.forEach { str ->
            if (str == "out") outCount++
            else stack.add(servers[str]!!)
        }
    }
    return outCount
}

fun getPart2Result(servers: Map<String, Server>): Int {
    val start = servers["svr"]!!
    val stack = Stack<List<Server>>().apply { this.add(listOf(start)) }
    var outCount = 0
    while (stack.isNotEmpty()) {
        val node = stack.pop()
        node.last().connections.forEach { str ->
            if (str == "out") {
                if (node.contains(servers["fft"]!!) && node.contains(servers["dac"]!!)) {
                    outCount++
                }
            } else if (!node.contains(servers[str]))
                stack.add(node + servers[str]!!)
        }
    }
    return outCount
}
