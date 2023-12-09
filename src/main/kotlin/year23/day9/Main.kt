package year23.day9

fun main() {
    val startTime = System.currentTimeMillis()

    //Read input
    val reader = object {}.javaClass.getResourceAsStream("input.txt")!!.bufferedReader()
    val numbers: List<List<Int>> = getNumberLists(reader.readLines())
    val readEndTime = System.currentTimeMillis()

    //Do Part 1
    val part1Result = getPart1Result(numbers)
    val part1EndTime = System.currentTimeMillis()

    //Do Part 2
    val part2Result = getPart2Result(numbers)
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
        |""".trimMargin().format(
            readEndTime - startTime,
            part1Result,
            part1EndTime - readEndTime,
            part2Result,
            part2EndTime - part1EndTime
        )
    )
}

fun getNumberLists(lines: List<String>): List<List<Int>> {
    return lines.map { it.trim() }
        .filterNot { it.isEmpty() }
        .map { it.split(" ") }
        .map { list -> list.map { it.toInt() } }
        .toList()
}

fun getPart1Result(numbers: List<List<Int>>): Int {
    return numbers.sumOf { extrapolate(it, Direction.FORWARDS) }
}

fun getPart2Result(numbers: List<List<Int>>): Int {
    return numbers.sumOf { extrapolate(it, Direction.BACKWARDS) }
}

fun getDifferences(input: List<Int>): List<Int> {
    return (0..<input.size - 1).map { index -> input[index + 1] - input[index] }
}

enum class Direction {
    FORWARDS, BACKWARDS
}

fun extrapolate(input: List<Int>, direction: Direction): Int {
    if (input.all { it == 0 }) return 0
    val extrapolation = extrapolate(getDifferences(input), direction)
    return when (direction) {
        Direction.FORWARDS -> extrapolation + input.last()
        Direction.BACKWARDS -> input.first() - extrapolation
    }
}