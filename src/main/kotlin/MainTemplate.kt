fun main() {
    val startTime = System.nanoTime()

    //Read input
    val reader = object {}.javaClass.getResourceAsStream("input.txt")!!.bufferedReader()
    val lines = reader.readLines()

    val readEndTime = System.nanoTime()

    //Do Part 1
    val part1Result = getPart1Result()
    val part1EndTime = System.nanoTime()

    //Do Part 2
    val part2Result = getPart2Result()
    val part2EndTime = System.nanoTime()

    //Display output
    println("Input read time: ${elapsedMicroSeconds(startTime, readEndTime)} μs\n")
    println("Part 1: %15d - Time %10d  μs"
        .format(part1Result, elapsedMicroSeconds(readEndTime, part1EndTime)))
    println("Part 2: %15d - Time %10d  μs\n"
        .format(part2Result, elapsedMicroSeconds(part1EndTime, part2EndTime)))
    println("Total time - ${elapsedMicroSeconds(startTime, part2EndTime)} μs")
}

fun elapsedMicroSeconds(start: Long, end: Long): Long = (end - start) / 1000

fun getPart1Result(): Int {
    return 0
}

fun getPart2Result(): Int {
    return 0
}
