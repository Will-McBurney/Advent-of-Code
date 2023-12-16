package year23.day16

fun main() {
    val startTime = System.currentTimeMillis()

    //Read input
    val reader = object {}.javaClass.getResourceAsStream("input.txt")!!.bufferedReader()
    val lines = reader.readLines()
    val mirrorGrid = getMirrorGrid(lines, Beam(Coordinate(0, 0), Direction.RIGHT))
    val readEndTime = System.currentTimeMillis()

    //Do Part 1
    val part1Result = getPart1Result(mirrorGrid)
    val part1EndTime = System.currentTimeMillis()

    //Do Part 2
    val part2Result = getPart2Result(lines)
    val part2EndTime = System.currentTimeMillis()

    //Display output
    println(
        """
        |Read Time: %10d ms
        |
        |Part One:  %10d - Time %6d ms
        |Part Two:  %10d - Time %6d ms
        |
        |Total time - ${part2EndTime - startTime}ms
        |""".trimMargin().format(readEndTime - startTime,
            part1Result,
            part1EndTime - readEndTime,
            part2Result,
            part2EndTime - part1EndTime)
    )
}

fun getMirrorGrid(lines: List<String>, beam: Beam): MirrorGrid {
    return MirrorGrid(lines.map { line -> line.toCharArray().toList() }, beam)
}

fun getPart1Result(mirrorGrid: MirrorGrid): Int {
    while (mirrorGrid.hasBeamsRemaining()) {
        mirrorGrid.advance()
    }
    return (mirrorGrid.getEnergizedCoordinates().size)
}

fun getPart2Result(lines: List<String>): Int {
    val size: Int = lines.size
    val charGrid:List<List<Char>> = lines.map { line -> line.toCharArray().toList()}
    return (0 ..< size).map {index ->
        val mirrorGridRight = MirrorGrid(charGrid, Beam(Coordinate(index, 0), Direction.RIGHT))
        val mirrorGridLeft = MirrorGrid(charGrid, Beam(Coordinate(index, size-1), Direction.LEFT))
        val mirrorGridUp = MirrorGrid(charGrid, Beam(Coordinate(size-1, index), Direction.UP))
        val mirrorGridDown = MirrorGrid(charGrid, Beam(Coordinate(0, index), Direction.DOWN))

        return@map listOf(mirrorGridRight, mirrorGridUp, mirrorGridDown, mirrorGridLeft)
    }.flatten().parallelStream()
        .peek {
            while (it.hasBeamsRemaining()) it.advance()
        }
        .mapToInt { it.getEnergizedCoordinates().size }
        .max().orElse(-1)
}