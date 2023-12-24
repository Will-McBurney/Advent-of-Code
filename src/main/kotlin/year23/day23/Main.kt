package year23.day23

import AoCResultPrinter
import CardinalDirection
import Grid
import GridCoordinate
import Reader
import java.util.*

const val year: Int = 23
const val day: Int = 23

fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
    val maze = getMaze(lines)
    val startingPoint: GridCoordinate = getStartingPosition(maze)
    val endingPoint: GridCoordinate = getEndingPosition(maze)
    branchPoints.add(startingPoint)
    buildEdges(maze, listOf(startingPoint), listOf(CardinalDirection.DOWN), endingPoint)

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(edges, startingPoint, endingPoint)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(edges, startingPoint, endingPoint)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getMaze(lines: List<String>): Grid<Char> {
    return Grid<Char>(lines.map { line -> line.toMutableList() }.toMutableList())
}

val memo: MutableMap<GridCoordinate, Int> = mutableMapOf()
val branchPoints: MutableSet<GridCoordinate> = mutableSetOf()
val edges: MutableMap<Pair<GridCoordinate, GridCoordinate>, Int> = mutableMapOf()

fun getPart1Result(edges: MutableMap<Pair<GridCoordinate, GridCoordinate>, Int>,
                   startingPoint: GridCoordinate,
                   endingPoint: GridCoordinate
): Int {
    return dfsLongestDistance(edges, startingPoint, endingPoint)
}

fun getPart2Result(
    edges: MutableMap<Pair<GridCoordinate, GridCoordinate>, Int>,
    startingPoint: GridCoordinate,
    endingPoint: GridCoordinate
): Int {
    val backEdges = edges.keys.filter { p: Pair<GridCoordinate, GridCoordinate> ->
        p.first != startingPoint && p.second != endingPoint }
        .associate { p -> Pair(p.second, p.first) to edges[p]!!}

    return dfsLongestDistance(edges + backEdges, startingPoint, endingPoint)
}

fun dfsLongestDistance(
    edges: Map<Pair<GridCoordinate, GridCoordinate>, Int>,
    startingPoint: GridCoordinate,
    endingPoint: GridCoordinate
): Int {
    val stack: Stack<Triple<GridCoordinate, Int, Set<GridCoordinate>>> = Stack()
    stack.push(Triple(startingPoint, 0, setOf(startingPoint)))

    var longestDistance = -1
    while (stack.isNotEmpty()) {
        val next = stack.pop()
        val location = next.first
        val currentDistance = next.second
        val visited = next.third

        //get successors
        val toSearch = edges.keys.filter { edge: Pair<GridCoordinate, GridCoordinate> -> location == edge.first}
            .filterNot {edge ->  visited.contains(edge.second)}

        //check for ending
        val endingEdge = toSearch.filter { it: Pair<GridCoordinate, GridCoordinate> -> it.second == endingPoint }
        if (endingEdge.isNotEmpty()) {
            val endingDistance = currentDistance + edges[endingEdge.single()]!!
            if (endingDistance > longestDistance) {
                longestDistance = endingDistance
            }
            continue
        }

        //add successors to search
        toSearch.forEach { edge ->
            stack.push(Triple(edge.second, currentDistance + edges[edge]!!, visited + edge.second))
        }
    }

    return longestDistance
}


fun getStartingPosition(maze: Grid<Char>): GridCoordinate =
    (0..<maze.cols).map { colIndex: Int -> GridCoordinate(0, colIndex) }
        .single { gc: GridCoordinate -> maze.get(gc) == MazeTile.EMPTY.char }

fun getEndingPosition(maze: Grid<Char>): GridCoordinate =
    (0..<maze.cols).map { colIndex: Int -> GridCoordinate(maze.rows - 1, colIndex) }
        .single { gc: GridCoordinate -> maze.get(gc) == MazeTile.EMPTY.char }



fun buildEdges(
    maze: Grid<Char>,
    locationHistory: List<GridCoordinate>,
    directionHistory: List<CardinalDirection>,
    endingPoint: GridCoordinate,
    isSlippery: Boolean = true
) {
    val locHistory = locationHistory.toMutableList()
    val dirHistory = directionHistory.toMutableList()
    var lastBranchLocation = locHistory.last { branchPoints.contains(it) }
    var lastBranchIndex = locHistory.indexOf( lastBranchLocation )
    while (true) {
        val currentLocation = locHistory.last()
        if (currentLocation == endingPoint) {
            branchPoints.add(currentLocation)
            edges[Pair(lastBranchLocation, currentLocation)] = locHistory.size - 1 - lastBranchIndex
            return
        }
        val lastDirection = dirHistory.last()
        val legalMoves: List<Pair<CardinalDirection, GridCoordinate>> =
            getLegalMoves(maze, currentLocation, lastDirection, isSlippery).toList()
                .filterNot { move: Pair<CardinalDirection, GridCoordinate> -> locHistory.contains(move.second) }
        if (legalMoves.isEmpty()) {
            return
        }

        if (locHistory.size > 1 && branchPoints.contains(currentLocation)) {
            edges[Pair(lastBranchLocation, currentLocation)] = locHistory.size - 1 - lastBranchIndex
            lastBranchLocation = currentLocation
            lastBranchIndex = locHistory.size - 1
        }

        if (legalMoves.size > 1) {
            legalMoves.forEach() { move: Pair<CardinalDirection, GridCoordinate> ->
                buildEdges(maze, locHistory + move.second,
                    dirHistory + move.first, endingPoint, isSlippery)
            }
            memo[currentLocation]
            return
        }
        val nextMove = legalMoves.single()
        locHistory.add(nextMove.second)
        dirHistory.add(nextMove.first)
    }
}

fun getLegalMoves(
    maze: Grid<Char>,
    location: GridCoordinate,
    lastDirection: CardinalDirection,
    isSlippery: Boolean = true
): Map<CardinalDirection, GridCoordinate> {
    if (isSlippery) {
        if (maze.get(location) == MazeTile.DOWN_SLOPE.char) {
            return mapOf(CardinalDirection.DOWN to location.getMovement(CardinalDirection.DOWN))
        }

        if (maze.get(location) == MazeTile.RIGHT_SLOPE.char) {
            return mapOf(CardinalDirection.RIGHT to location.getMovement(CardinalDirection.RIGHT))
        }
    }

    val neighbors = CardinalDirection.entries
        .filter { direction -> direction != lastDirection.opposite }
        .associateWith { location.getMovement(it) }
        .filter { entry -> maze.get(entry.value) != MazeTile.WALL.char }.toMutableMap()

    if (neighbors.size > 1) {
        branchPoints.add(location)
    }

    if (isSlippery) {
        if (neighbors.containsKey(CardinalDirection.UP) && maze.get(neighbors[CardinalDirection.UP]!!) == MazeTile.DOWN_SLOPE.char) {
            neighbors.remove(CardinalDirection.UP)
        }

        if (neighbors.containsKey(CardinalDirection.LEFT) && maze.get(neighbors[CardinalDirection.LEFT]!!) == MazeTile.RIGHT_SLOPE.char) {
            neighbors.remove(CardinalDirection.LEFT)
        }
    }

    return neighbors
}
