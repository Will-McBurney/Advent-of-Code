package year23.day14

fun main() {
    val startTime = System.currentTimeMillis()

    //Read input
    val reader = object {}.javaClass.getResourceAsStream("input.txt")!!.bufferedReader()
    val lines = reader.readLines()
    val part1Platform = getPlatform(lines)
    val part2Platform = getPlatform(lines)
    //println(part1Platform)
    val readEndTime = System.currentTimeMillis()

    //Do Part 1
    val part1Result = getPart1Result(part1Platform)
    val part1EndTime = System.currentTimeMillis()

    //Do Part 2
    val part2Result = getPart2Result(part2Platform)
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

fun getPlatform(lines: List<String>): SlidingPlatform {
    val gridState: MutableList<MutableList<Char>> = mutableListOf()
    lines.forEach { line ->
        val row: MutableList<Char> = line.toMutableList()
        gridState.add(row)
    }
    return SlidingPlatform(gridState)
}

fun getPart1Result(startingPlatform: SlidingPlatform): Long {
    startingPlatform.slideUp()
    return startingPlatform.getLoad()
}

fun getPart2Result(startingPlatform: SlidingPlatform): Long {
    var targetCycleNumber = 100000000
    val stateMap: MutableMap<String, Int> = mutableMapOf()
    var cycleCount = 0
    var oscillationSize = -1
    while (true) {
        cycleCount++
        startingPlatform.cycle()
        val state = startingPlatform.toString()
        if (stateMap.containsKey(state) && oscillationSize == -1) {
            oscillationSize = cycleCount - stateMap[state]!!
            targetCycleNumber %= oscillationSize
            println(targetCycleNumber)
        }
        stateMap[state] = cycleCount
        if (cycleCount % oscillationSize == targetCycleNumber) {
            return startingPlatform.getLoad()
        }
    }
}