package year24.day08

import AoCResultPrinter
import Grid
import GridCoordinate
import Reader

const val year: Int = 24
const val day: Int = 8



fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)

    val grid = Grid<Char>(lines.map { line -> line.toCharArray().toMutableList() }
        .toMutableList())

    val antennaGroups = grid.coordinates.filterNot{ grid.get(it) == '.' }
        .groupByTo(mutableMapOf()) { grid.get(it) }


    printer.endSetup()

    //Do Part 1
    val part1Result = getResult(antennaGroups, grid, ::getAntinodesPart1)
    printer.endPart1()

    //Do Part 2
    val part2Result = getResult(antennaGroups, grid, ::getAntinodesPart2)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}


fun getResult(
    antennaGroups: MutableMap<Char, MutableList<GridCoordinate>>,
    grid: Grid<Char>,
    getAntinodes: (GridCoordinate, GridCoordinate, Grid<Char>) -> List<GridCoordinate>
): Int {
    return antennaGroups.keys
        .map { getAntinodeList(antennaGroups[it]!!, grid, getAntinodes) }
        .flatten()
        .distinct()
        .count()
}



fun getAntinodeList(
    coordinates: List<GridCoordinate>,
    grid: Grid<Char>,
    getAntinodes: (GridCoordinate, GridCoordinate, Grid<Char>) -> List<GridCoordinate>
): List<GridCoordinate> {
    return coordinates.indices.map { i ->
        (i + 1..<coordinates.size).map { j ->
            getAntinodes(coordinates[i], coordinates[j], grid)
        }.flatten()
    } .flatten()
}

fun getAntinodesPart1(
    a: GridCoordinate, b: GridCoordinate, grid: Grid<Char>
): List<GridCoordinate> {
    val dRow = b.row - a.row
    val dCol = b.col - a.col
    val out = mutableListOf<GridCoordinate>(
        GridCoordinate(a.row - dRow, a.col - dCol),
        GridCoordinate(b.row + dRow, b.col + dCol)
    ).filter { grid.isInBounds(it)  }
    return out
}

fun getAntinodesPart2(
    a: GridCoordinate,
    b: GridCoordinate,
    grid: Grid<Char>
): List<GridCoordinate> {
    val dRow = b.row - a.row
    val dCol = b.col - a.col
    val antinodes = mutableListOf<GridCoordinate>()
    var aAntinode = a.copy()
    while (grid.isInBounds(aAntinode)) {
        antinodes.add(aAntinode)
        aAntinode = GridCoordinate(aAntinode.row - dRow, aAntinode.col - dCol)
    }

    var bAntinode = b.copy()
    while (grid.isInBounds(bAntinode)) {
        antinodes.add(bAntinode)
        bAntinode = GridCoordinate(bAntinode.row + dRow, bAntinode.col + dCol)
    }

    return antinodes.filter { grid.isInBounds(it)  }
}




