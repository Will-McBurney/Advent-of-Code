package year23.day11

fun main() {
    val startTime = System.currentTimeMillis()

    //Read input
    val startMapPart1 = getGalaxyMap(2)
    val startMapPart2 = getGalaxyMap(1000000)
    val readEndTime = System.currentTimeMillis()

    //Do Part 1
    val part1Result = getSumOfGalaxyDistance(startMapPart1)
    val part1EndTime = System.currentTimeMillis()

    //Do Part 2
    val part2Result = getSumOfGalaxyDistance(startMapPart2)
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

private fun getGalaxyMap(expansionFactor: Int): GalaxyMap {
    val readerPart1 = object {}.javaClass.getResourceAsStream("input.txt")!!.bufferedReader()
    val linesPart1 = readerPart1.readLines()
    val startMapPart1 = processInput(linesPart1, expansionFactor)
    return startMapPart1
}

fun processInput(lines: List<String>, expansionFactor: Int): GalaxyMap {
    return GalaxyMap(lines.map{ it.toCharArray().toList() }
        .toList(), expansionFactor)
}
fun getSumOfGalaxyDistance(galaxyMap: GalaxyMap): Long {
    return galaxyMap.getSumOfShortestDistance()
}
