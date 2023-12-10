package year23.day10

fun main() {
    val startTime = System.currentTimeMillis()

    //Read input
    val reader = object {}.javaClass.getResourceAsStream("input.txt")!!.bufferedReader()
    val pipeGrid = getProcessInput(reader.readLines())
    val loop = pipeGrid.getLoop()
    val readEndTime = System.currentTimeMillis()

    //Do Part 1
    val part1Result = getPart1Result(loop)
    val part1EndTime = System.currentTimeMillis()

    //Do Part 2
    val part2Result = getPart2Result(pipeGrid, loop)
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

enum class Direction(val dx: Int, val dy: Int) {
    UP(-1, 0), DOWN(1, 0), LEFT(0, -1), RIGHT(0, 1)
}



fun getProcessInput(lines: List<String>): PipeGrid {
    return PipeGrid(lines.map { it.trim() }
        .filter { it.isNotEmpty() }
        .map{ it.toMutableList() }
        .toMutableList());
}

fun getPart1Result(loop: Loop): Int {
    return loop.getSize() / 2
}

fun getPart2Result(pipeGrid: PipeGrid, loop: Loop): Int {
    return pipeGrid.getNumEnclosedSpaces(loop)
}
