package year18.day03

import AoCResultPrinter

const val year: Int = 18
const val day: Int = 3

fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val sections = Reader.getLines(year, day, inputFilename)
        .filterNot { it.isEmpty() }
        .map { it.trim() }
        .map { getSection(it) }

    val coordinateCounts: Map<Coordinate, Int> = getCoordinateCounts(sections)

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(coordinateCounts)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(sections, coordinateCounts)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

data class Coordinate(
    val col: Int,
    val row: Int
)

data class Section(
    val id: Int,
    val startingCol: Int,
    val startingRow: Int,
    val colsCount: Int,
    val rowsCount: Int
) {
    fun getCoordinates(): List<Coordinate> {
        return (startingCol ..< startingCol + colsCount).map {col ->
            (startingRow ..< startingRow + rowsCount).map { row ->
                Coordinate(col, row)
            }
        }.flatten()
    }
}

fun getSection(string: String): Section {
    return Section(
        string.substringAfter("#").substringBefore("@").trim().toInt(),
        string.substringAfter("@").substringBefore(",").trim().toInt(),
        string.substringAfter(",").substringBefore(":").trim().toInt(),
        string.substringAfter(":").substringBefore("x").trim().toInt(),
        string.substringAfter("x").trim().toInt()
    )
}

fun getPart1Result(coordinateCounts: Map<Coordinate, Int>): Int {
    return coordinateCounts.count { entry -> entry.value >= 2 }
}

private fun getCoordinateCounts(sections: List<Section>) = sections.map { it.getCoordinates() }
    .flatten()
    .fold(mutableMapOf<Coordinate, Int>()) { map, coordinate ->
        map[coordinate] = 1 + map.getOrDefault(coordinate, 0)
        map
    }

fun getPart2Result(sections: List<Section>, coordinateCounts: Map<Coordinate, Int>): Int {
    return sections.single { section ->
        section.getCoordinates().all { c ->
            (coordinateCounts[c] ?: 0) == 1 }
    }.id
}
