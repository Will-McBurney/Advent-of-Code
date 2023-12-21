package year16.day04

fun main() {
    val startTime = System.nanoTime()

    //Read input
    val reader = object {}.javaClass.getResourceAsStream("input.txt")!!.bufferedReader()
    val lines = reader.readLines()

    val readEndTime = System.nanoTime()

    //Do Part 1
    val part1Result = getPart1Result(lines)
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




val roomRegex: Regex = "([a-z-]+)([0-9]{3})\\[([a-z]{5})]".toRegex()

fun getPart1Result(lines: List<String>): Int {
    return lines.map { line -> roomRegex.find(line)!!.groupValues }
        .filter { groups -> isRealRoom(groups[1], groups[3]) }
        .sumOf { groups -> groups[2].toInt() }
}

fun getPart2Result(): Int {
    return 0
}

fun isRealRoom(roomString: String, checkSum: String): Boolean {
    val actual = roomString.filter { char -> '-' != char }
        .associate {
            char -> char to roomString.count { c -> c == char }
        }
        .toList()
        .asSequence()
        .sortedWith(compareByDescending<Pair<Char, Int>>  { pair -> pair.second }.thenBy { pair -> pair.first })
        .take(5)
        .map{ pair -> pair.first }
        .joinToString("")

    return actual == checkSum
}