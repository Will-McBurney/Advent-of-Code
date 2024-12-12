package year24.day12

import AoCResultPrinter
import Grid
import GridCoordinate
import Reader
import java.util.Stack
import CardinalDirection.RIGHT as RIGHT
import CardinalDirection.UP as UP

const val year: Int = 24
const val day: Int = 12

val SORT_TOP_LEFT: Comparator<GridCoordinate> = compareBy<GridCoordinate>{ it.row }.thenBy { it.col }

fun main() {
    val printer = AoCResultPrinter(year, day)
    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)

    val grid = Grid<Char>(lines.map { it.toCharArray().toMutableList() }.toMutableList())

    val coordinates = grid.coordinates.toMutableSet()
    val regions = mutableListOf<Region>()
    while (coordinates.isNotEmpty()) {
        val regionCoordinates = mutableSetOf<GridCoordinate>()
        val boundaryCoordinates = mutableSetOf<GridCoordinate>()
        val c = coordinates.first()
        val stack = Stack<GridCoordinate>()
        coordinates.remove(c)
        stack.push(c)
        do {
            val node = stack.pop()
            regionCoordinates.add(node)

            val nextNodes = grid.getCardinalNeighbors(node)
                .filter { coordinates.contains(it) }
                .filter { grid.get(it) == grid.get(node) } // ensure same letter

            boundaryCoordinates.addAll(
                grid.getCardinalNeighbors(node, filterOutOfBounds = false)
                .filter { !grid.isInBounds(it) || grid.get(it) != grid.get(node) }
            )

            nextNodes.forEach {
                stack.push(it)
                coordinates.remove(it)
            }
        } while (stack.isNotEmpty())
        regions.add(Region(
            grid.get(c),
            regionCoordinates,
            boundaryCoordinates,
            grid
        ))
    }

    regions.forEach {
        println("${it.letter} - ${it.sides}")
    }

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(regions)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(regions)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

data class Region(
    val letter: Char,
    val coordinates: Set<GridCoordinate> = setOf(),
    val boundary: Set<GridCoordinate> = setOf(),
    val grid: Grid<Char>
) {
    val area: Int
        get() = coordinates.size

    val perimeter: Int
        get() = coordinates.sumOf { c ->
            grid.getCardinalNeighbors(c, filterOutOfBounds = false)
                .count { !coordinates.contains(it) }
        }

    val sides: Int
        get() {
            val topLeftCoordinate = coordinates.minWith(compareBy<GridCoordinate> { it.row }.thenBy { it.col })
            val start = topLeftCoordinate.getMovement(UP)
            var sides = 0
            var traveler = start
            var moving = RIGHT
            do {
                if (!coordinates.contains(traveler.getMovement(moving))) {
                    traveler = traveler.getMovement(moving)
                    if (!coordinates.contains(traveler.getMovement(moving.clockwise))) {
                        moving = moving.clockwise
                        traveler = traveler.getMovement(moving)
                        sides++
                    }
                } else {
                    moving = moving.counterClockwise
                    sides++
                }
            } while (traveler != start)

            return sides
        }

    fun circumscribes(region: Region): Boolean {
        return region.boundary.all { coordinates.contains(it) }
    }
}


fun getPart1Result(regions: MutableList<Region>): Long {
    return regions.sumOf { it.area.toLong() * it.perimeter }
}

fun getPart2Result(regions: MutableList<Region>): Long {
    val outerSides = regions.sumOf { it.area.toLong() * it.sides }
    val circumscribedSides = regions.sumOf { r1 ->
        regions.sumOf { r2 ->
            if (r1.circumscribes(r2)) (r1.area * r2.sides) else 0
        }
    }
    return outerSides + circumscribedSides
}
