package year15.day17

fun main() {
    val startTime = System.currentTimeMillis()

    //Read input
    val reader = object {}.javaClass.getResourceAsStream("input.txt")!!.bufferedReader()
    val containers: List<Int> = getContainersDescendingOrder(reader.readLines())
    println(containers)
    val target = 150
    val readEndTime = System.currentTimeMillis()

    //Do Part 1
    val part1Result = getPart1Result(containers, target)
    val part1EndTime = System.currentTimeMillis()

    //Do Part 2
    val part2Result = getPart2Result(containers, target)
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

fun getContainersDescendingOrder(lines: List<String>): List<Int> {
    return lines.map{ it.trim() }
        .filterNot { it.isEmpty() }
        .map{ it.toInt() }
        .sortedDescending()
}

fun getPart1Result(containers: List<Int>, target: Int): Int {
    return getPossibilitiesCount(containers, target)
}

fun getPart2Result(containers: List<Int>, target: Int): Int {
    val possibitilies = getPossibilities(containers, target)
    val minSize = possibitilies.minOf { it.size }
    return possibitilies.count { it.size == minSize }
}

fun getPossibilitiesCount(containers: List<Int>, target: Int): Int {
    return getPossibilitiesCountHelper(0, containers, target)
}

fun getPossibilitiesCountHelper(startingIndex: Int, containers: List<Int>, target: Int): Int {
    if (target == 0) {
        return 1
    }
    if (target < 0) {
        return 0
    }
    if (startingIndex == containers.size) {
        return 0
    }
    return getPossibilitiesCountHelper(startingIndex + 1, containers, target - containers[startingIndex]) +
            getPossibilitiesCountHelper(startingIndex + 1, containers, target)
}

fun getPossibilities(containers: List<Int>, target: Int): MutableList<MutableList<Int>> {
    return getPossibilitiesHelper(0, containers, target)!!
}

fun getPossibilitiesHelper(startingIndex: Int, containers: List<Int>, target: Int): MutableList<MutableList<Int>>? {
    if (target == 0) {
        return mutableListOf(mutableListOf())
    }
    if (target < 0) {
        return null
    }
    if (startingIndex == containers.size) {
        return null
    }
    val outerPermutations = mutableListOf<MutableList<Int>>()
    val possibilitiesWith = getPossibilitiesHelper(startingIndex + 1, containers, target - containers[startingIndex])
    if (possibilitiesWith != null) {
        for (possibility in possibilitiesWith) {
            outerPermutations.add(possibility)
            possibility.add(0, containers[startingIndex])
        }
    }
    val possibilitiesWithout = getPossibilitiesHelper(startingIndex + 1, containers, target)
    if (possibilitiesWithout != null) {
        for (possibility in possibilitiesWithout) {
            outerPermutations.add(possibility)
        }
    }
    return outerPermutations
}
