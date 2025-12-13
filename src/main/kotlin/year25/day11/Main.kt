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
    val start = servers["you"]!!
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


data class P2Memo(
    val id: String,
    val neither: Long,
    val hasDac: Long,
    val hasFft: Long,
    val hasBoth: Long
)

fun getPart2Result(servers: Map<String, Server>): Long {
    val p2Cache = mutableMapOf<String, P2Memo>()
    return recursiveP2("svr", servers, p2Cache).hasBoth
}

fun recursiveP2(serverString: String, servers: Map<String, Server>, p2Cache: MutableMap<String, P2Memo>): P2Memo {
    if (p2Cache[serverString] != null) return p2Cache[serverString]!!
    if (servers[serverString]!!.connections.contains("out")) {
        p2Cache[serverString] = P2Memo(serverString, neither = 1L, hasDac = 0L, hasFft = 0L, hasBoth = 0L)
        return p2Cache[serverString]!!
    }
    val children = servers[serverString]!!.connections.map { recursiveP2(it, servers, p2Cache) }
    p2Cache[serverString] = when (serverString) {
        "dac" -> {
            P2Memo(
                id = serverString,
                neither = 0L,
                hasDac = children.sumOf { it.neither },
                hasFft = 0L,
                hasBoth = children.sumOf { it.hasFft }
            )
        }
        "fft" -> {
            P2Memo(
                id = serverString,
                neither = 0L,
                hasDac = 0L,
                hasFft = children.sumOf { it.neither },
                hasBoth = children.sumOf { it.hasDac }
            )
        }
        else -> {
            P2Memo(
                id = serverString,
                neither = children.sumOf { it.neither },
                hasDac = children.sumOf { it.hasDac },
                hasFft = children.sumOf { it.hasFft },
                hasBoth = children.sumOf { it.hasBoth }
            )
        }
    }
    return p2Cache[serverString]!!
}
