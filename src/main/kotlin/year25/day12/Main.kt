package year25.day12

import AoCResultPrinter
import GridCoordinate
import Reader

const val year: Int = 25
const val day: Int = 12

data class Present(
    val offsets: List<GridCoordinate>
) {
    val size get() = offsets.size
}

const val PRESENT_WIDTH = 3

fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
    val presents = lines.subList(1, 25).windowed(size = PRESENT_WIDTH, step = 5).map {w ->
        val offsets = mutableListOf<GridCoordinate>()
        for (r in 0..2){
            for (c in 0..2) {
                if (w[r][c] == '#') offsets.add(GridCoordinate(r, c))
            }
        }
        Present(offsets)
    }
    
    val grids = lines.subList(30, lines.size).map { line ->
        line.substringBefore(":").split('x').map { it.toInt() }
    }.map {Pair(it[0], it[1]) }
    
    val counts = lines.subList(30, lines.size).map { line ->
        line.substringAfter(": ").trim().split(" ").map { it.toInt() }
    }
    

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(presents, grids, counts)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result()
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getPart1Result(presents: List<Present>, grids: List<Pair<Int, Int>>, counts: List<List<Int>>): Int {
    return grids.filterIndexed { i, g ->
        presentSearch(counts[i], g.first, g.second)
    }.count()
}

fun presentSearch(counts: List<Int>, width: Int, height: Int): Boolean =
    counts.sum() <= (width / 3 * height / 3)

fun getPart2Result(): Int {
    return 0
}
