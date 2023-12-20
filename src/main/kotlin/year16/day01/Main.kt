package year16.day01

import kotlin.math.abs

fun main() {
    val startTime = System.currentTimeMillis()

    //Read input
    val reader = object {}.javaClass.getResourceAsStream("input.txt")!!.bufferedReader()
    val steps: List<Pair<Char, Int>> = getSteps(reader.readLine())
    val readEndTime = System.currentTimeMillis()

    //Do Part 1
    val part1Result = getPart1Result(steps)
    val part1EndTime = System.currentTimeMillis()

    //Do Part 2
    val part2Result = getPart2Result(steps)
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

fun getSteps(line: String): List<Pair<Char, Int>> {
    return line.trim().split(",")
        .map{ it.trim() }
        .map{it[0] to it.substring(1).toInt()}
}

fun getPart1Result(steps: List<Pair<Char, Int>>): Int {
    var x = 0
    var y = 0
    var direction = Direction.NORTH
    steps.forEach { step ->
        direction = if (step.first == 'L') {
            direction.left()
        } else {
            direction.right()
        }
        val distance: Int = step.second
        x += direction.dx * distance
        y += direction.dy * distance
    }
    return abs(x) + abs(y)
}

fun getPart2Result(steps: List<Pair<Char, Int>>): Int {
    var x = 0
    var y = 0
    var direction = Direction.NORTH
    val visited: MutableList<Pair<Int, Int>> = mutableListOf()
    steps.forEach { step ->
        direction = if (step.first == 'L') {
            direction.left()
        } else {
            direction.right()
        }
        val distance: Int = step.second
        repeat(distance) {
            x += direction.dx
            y += direction.dy
            if (visited.contains(x to y)) {
                return abs(x) + abs(y)
            }
            visited.add(x to y)
        }
    }
    return abs(x) + abs(y)
}