

fun main() {
    val startTime = System.currentTimeMillis()
    val reader = object {}.javaClass.getResourceAsStream("input.txt")!!.bufferedReader()
    val lines = reader.readLines()
    val part1Result = getPart1Result()
    val part2Result = getPart2Result()
    val endTime = System.currentTimeMillis()
    println(
        """
        |Part One: $part1Result 
        |Part Two: $part2Result 
        |Calculation time - ${endTime - startTime}ms
        |""".trimMargin()
    )
}

fun getPart1Result(): Int {
    return 0
}

fun getPart2Result(): Int {
    return 0
}
