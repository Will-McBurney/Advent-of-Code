package year23.day10

fun main() {
    val startTime = System.currentTimeMillis()

    //Read input
    val reader = object {}.javaClass.getResourceAsStream("input.txt")!!.bufferedReader()
    val processedInput = getProcessInput(reader.readLines())
    val readEndTime = System.currentTimeMillis()

    //Do Part 1
    val part1Result = getPart1Result()
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

fun getProcessInput(readLines: List<String>): List<Int> {
    return listOf<Int>()
}

fun getPart1Result(): Int {
    return 0
}

fun getPart2Result(): Int {
    return 0
}
