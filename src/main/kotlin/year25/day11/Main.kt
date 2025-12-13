package year25.day11

import AoCResultPrinter
import Reader

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


fun getPart1Result(servers: Map<String, Server>): Long {
    val start = servers["svr"]!!
    val p1Cache = mutableMapOf<Server, Long>()
    return recursiveP1(start, servers, p1Cache)
}

fun recursiveP1(server: Server, servers: Map<String, Server>, p1Cache: MutableMap<Server, Long>): Long {
    if (p1Cache[server] != null) return p1Cache[server]!!
    if (server.connections.contains("out")) {
        p1Cache[server] == 1L
        return 1L
    }
    p1Cache[server] = server.connections.sumOf { c ->
        recursiveP1(servers[c]!!, servers, p1Cache)
    }
    return p1Cache[server]!!
}


data class P2Node(
    val id: String,
    val hasDac: Boolean,
    val hasFft: Boolean
)

fun getPart2Result(servers: Map<String, Server>): Long {
    val p2Cache = mutableMapOf<P2Node, Long>()
    val start = P2Node("svr", false, false)
    return recursiveP2(start, servers, p2Cache)
}

fun recursiveP2(node: P2Node, servers: Map<String, Server>, p2Cache: MutableMap<P2Node, Long>): Long {
    if (p2Cache[node] != null) return p2Cache[node]!!
    if (servers[node.id]!!.connections.contains("out")) {
        p2Cache[node] == 1L
        return 1L
    }
    p2Cache[node] = servers[node.id]!!.connections.sumOf { c ->
        recursiveP2(node.copy(id = c), servers, p2Cache)
    }
    return p2Cache[node]!!
}
