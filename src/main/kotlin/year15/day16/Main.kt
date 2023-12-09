package year15.day16

fun main() {
    val startTime = System.currentTimeMillis()

    //Read input
    val suesReader = object {}.javaClass.getResourceAsStream("input.txt")!!.bufferedReader()
    val sues= getSues(suesReader.readLines())

    val stuffReader = object {}.javaClass.getResourceAsStream("known_stuff.txt")!!.bufferedReader()
    val knownStuff: Map<String, Int> = getKnownStuff(stuffReader.readLines())

    val readEndTime = System.currentTimeMillis()

    //Do Part 1
    val part1Result = findSue(sues, knownStuff, Part1SueChecker())
    val part1EndTime = System.currentTimeMillis()

    //Do Part 2
    val part2Result = findSue(sues, knownStuff, Part2SueChecker())
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

fun getKnownStuff(lines: List<String>): Map<String, Int> {
    return lines.map { it.trim() }
        .filterNot { it.isEmpty() }
        .map { it.split(":")}
        .associate { Pair(it[0].trim(), it[1].trim().toInt()) }
}

val LINE_PATTERN = "Sue ([0-9]{1,3}): ([a-z]+): ([0-9]+), ([a-z]+): ([0-9]+), ([a-z]+): ([0-9]+)".toRegex()
fun getSues(lines: List<String>): List<Sue> {
    return lines.asSequence()
        .map{ it.trim() }
        .filterNot { it.isEmpty() }
        .map{ LINE_PATTERN.find(it)!!.groupValues }
        .map{ groups ->
            val newSue = Sue(groups[1].toInt());
            (2..6 step 2).forEach { newSue.addStuffCount(groups[it], groups[it + 1].toInt()) }
            return@map newSue
        }.toList()
}
fun findSue(sues: List<Sue>, knownStuff: Map<String, Int>, sueChecker: SueChecker): Int {
    sues.filter { sue -> sueChecker.isPossible(sue, knownStuff) }.forEach { println(it) }
    return sues.first { sue -> sueChecker.isPossible(sue, knownStuff) }.id
    //return 0
}
