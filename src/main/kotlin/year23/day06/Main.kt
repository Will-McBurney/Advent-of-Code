package year23.day06

import kotlin.math.sqrt


fun main() {
    val startTime = System.currentTimeMillis()
    val times = listOf<Long>(54, 94, 65, 92)
    val distances = listOf<Long>(302, 1476, 1029, 1404)
    val part1Result = getPart1Result(times, distances)
    val part2Time = 54946592L
    val part2Distance = 302147610291404L
    val part2Result = getPart2Result(part2Time, part2Distance)
    val endTime = System.currentTimeMillis()
    println(
        """
        |Part One: $part1Result 
        |Part Two: $part2Result 
        |Calculation time - ${endTime - startTime}ms
        |""".trimMargin()
    )
}

fun getPart1Result(times: List<Long>, distances: List<Long>): Long {
    return (times.indices)
        .map{ getWinningPossibilities(times[it], distances[it]) }
        .reduce(Long::times)
}

fun getPart2Result(totalTime: Long, recordDistance: Long): Long {
    val zeroPoint = getZeroPoint(totalTime, recordDistance)
    return totalTime - 2 * zeroPoint + (totalTime + 1) % 2
}

fun getWinningPossibilities(time: Long, distance: Long): Long {
    return (0..time)
        .map{ distanceTraveled(time - it, time)}
        .count { it > distance }.toLong()
}

fun getZeroPoint(time: Long, distance: Long): Long {
    return ((time - sqrt((time * time - 4 * distance).toDouble()))/2).toLong() + 1
}

fun distanceTraveled(waitTime: Long, totalTime: Long): Long {
     return waitTime * (totalTime - waitTime)
}
