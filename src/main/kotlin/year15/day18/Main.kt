package year15.day18

fun main() {
    val startTime = System.currentTimeMillis()

    //Read input
    val reader = object {}.javaClass.getResourceAsStream("input.txt")!!.bufferedReader()
    val lines = reader.readLines()
    val grid = getStartingGrid(lines)

    val readEndTime = System.currentTimeMillis()

    //Do Part 1
    val part1Result = getPart1Result(grid, 100)
    val part1EndTime = System.currentTimeMillis()

    //Do Part 2
    val part2Result = getPart2Result()
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

fun getStartingGrid(lines: List<String>): Grid {
    val newGridList: MutableList<List<Char>> = mutableListOf()
    lines.forEach { line ->
        newGridList.add(line.trim().toList())
    }
    return Grid(newGridList)
}

fun getPart1Result(grid: Grid, repetitions: Int): Int {
    repeat(repetitions) { grid.advance() }

    println(grid)
    return grid.getOnCount()
}

fun getPart2Result(): Int {
    return 0
}
