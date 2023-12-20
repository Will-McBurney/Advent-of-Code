package year15.day14

fun main() {
    val startTime = System.currentTimeMillis()
    val inputTime = 1000

    //Read input
    val reader = object {}.javaClass.getResourceAsStream("test_input1.txt")!!.bufferedReader()
    val lines = reader.readLines()
    val reindeer: List<Reindeer> = getReindeer(lines)
    println(reindeer)
    val readEndTime = System.currentTimeMillis()

    //Do Part 1
    val part1Result = getPart1Result(reindeer, inputTime)
    val part1EndTime = System.currentTimeMillis()

    //Do Part 2
    val part2Result = getPart2Result(reindeer, inputTime)
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

val LINE_PATTERN = "([A-Za-z]+) can fly ([0-9]+) km/s for ([0-9]+) seconds, but then must rest for ([0-9]+) seconds\\.".toRegex()

fun getReindeer(lines: List<String>): List<Reindeer> {
    return lines.filterNot { it.isEmpty() }
        .map{ LINE_PATTERN.find(it)!!.groups}
        .map{ Reindeer(it[1]!!.value, it[2]!!.value.toInt(), it[3]!!.value.toInt(), it[4]!!.value.toInt())}
        .toList()
}

fun getPart1Result(reindeer: List<Reindeer>, raceTimeSeconds: Int): Int {
    return reindeer.maxOf { it.getDistanceTraveled(raceTimeSeconds) }
}

fun getPart2Result(reindeer: List<Reindeer>, raceTimeSeconds: Int): Int {
    (1..raceTimeSeconds)
        .forEach { incrementLeadingReindeer(reindeer, it) }
    return reindeer.maxOf { it.points }
}

fun incrementLeadingReindeer(reindeer: List<Reindeer>, timeElapsedSeconds: Int) {
    val reindeerDistances = reindeer.associateWith { it.getDistanceTraveled(timeElapsedSeconds) }
    val maxDistance = reindeerDistances.values.max()
    reindeerDistances.filter { it.value == maxDistance }.keys.forEach { it.incrementPoints() }
}
