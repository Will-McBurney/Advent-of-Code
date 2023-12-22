package year23.day22

import AoCResultPrinter
import Grid
import GridCoordinate
import Reader

const val year: Int = 23
const val day: Int = 22

fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
    val bricks = getBricks(lines)

    dropBricks(bricks) //initial drop

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(bricks)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(bricks)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getBricks(lines: List<String>): List<Brick> {
    return lines.map(String::trim)
        .map { line -> line.split("~") }
        .map { s ->
            s.map { c -> c.split(",").map(String::toInt) }
                .map { Position(it[0], it[1], it[2]) }
        }
        .mapIndexed{ index, pair -> Brick(index, pair[0], pair[1]) }
}





fun getPart1Result(bricks: List<Brick>): Int {
    val removableBricks = getRemovableBricks(bricks)

    return bricks.size - removableBricks.size
}

private fun getRemovableBricks(bricks: List<Brick>): List<Brick> {
    val removalBricks = bricks.map(Brick::supportedBy)
        .filter { set -> set.size == 1 }
        .flatten()
        .distinct()
    return removalBricks
}
/** returns the number of bricks moved as a result of the drop **/

fun dropBricks(bricks: List<Brick>): Int {
    assert(0 >= bricks.minOf { it.minX } )
    assert(0 >= bricks.minOf { it.minY } )

    val xSize = bricks.maxOf { it.maxX } + 1
    val ySize = bricks.maxOf { it.maxY } + 1
    val heightsMap: Grid<Int> = Grid(MutableList(xSize) { MutableList(ySize) { 0 } } )
    val topBricks: Grid<Brick?> = Grid(MutableList(xSize) { MutableList(ySize) { null } })

    var bricksMoved = 0

    bricks.sortedBy { it.minHeight }
        .forEach { brick ->

            val footprintCoordinates: List<GridCoordinate> = brick.footprint.map { GridCoordinate(it.first, it.second) }
            val restingZ = footprintCoordinates.maxOf {c -> heightsMap.get(c)} + 1
            if (brick.minHeight > restingZ) {
                bricksMoved++
                brick.dropTo(restingZ)
            }

            //add bricks to supporting
            footprintCoordinates.mapNotNull { c -> topBricks.get(c) }
                .filter { b -> b.maxHeight == restingZ - 1 }
                .distinct()
                .forEach { b ->
                    b.supporting.add(brick)
                    brick.supportedBy.add(b)
                }

            //update maps
            footprintCoordinates.forEach {c -> heightsMap.set(c, brick.maxHeight) }
            footprintCoordinates.forEach {c -> topBricks.set(c, brick)  }
        }
    return bricksMoved

}

/** Brute force simulation, which I'm sure is slow, but it's fast enough for the input **/

fun getPart2Result(bricks: List<Brick>): Int {
    val removableBricks = getRemovableBricks(bricks)

    return removableBricks.parallelStream().mapToInt { brickToRemove: Brick ->
        val bricksCopy = bricks.map(Brick::clone)
            .filter { brick -> brick.id != brickToRemove.id }
        return@mapToInt dropBricks(bricksCopy)
    }.sum()
}