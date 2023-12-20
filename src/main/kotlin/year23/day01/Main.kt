package year23.day01

const val MU_UNICODE = "\u03BC".toString()

fun main() {
    val startTime = System.nanoTime()

    //Read input
    val reader = object {}.javaClass.getResourceAsStream("input.txt")!!.bufferedReader()
    val lines = reader.readLines().map{ line -> line.trim()}
        .filter { line -> line.isNotEmpty() }

    val readEndTime = System.nanoTime()

    //Do Part 1
    val part1Result = getResult(lines, Part1Digitizer())
    val part1EndTime = System.nanoTime()

    //Do Part 2
    val part2Result = getResult(lines, Part2Digitizer())
    val part2EndTime = System.nanoTime()

    //Display output
    displayOutput(startTime, readEndTime, part1Result, part1EndTime, part2Result, part2EndTime)
}

private fun displayOutput(startTime: Long, readEndTime: Long, part1Result: Int, part1EndTime: Long, part2Result: Int, part2EndTime: Long) {
    println("Input read time: ${elapsedMicroSeconds(startTime, readEndTime)} ${MU_UNICODE}s\n")
    println(
        "Part 1: %15d - Time %10d  ${MU_UNICODE}s"
            .format(part1Result, elapsedMicroSeconds(readEndTime, part1EndTime))
    )
    println(
        "Part 2: %15d - Time %10d  ${MU_UNICODE}s\n"
            .format(part2Result, elapsedMicroSeconds(part1EndTime, part2EndTime))
    )
    println("Total time - ${elapsedMicroSeconds(startTime, part2EndTime)} ${MU_UNICODE}s")
}

fun elapsedMicroSeconds(start: Long, end: Long): Long = (end - start) / 1000

fun getResult(lines: List<String>, digitizer: Digitizer): Int {
    return lines.sumOf{line ->
        digitizer.getFirstDigit(line) * 10 + digitizer.getLastDigit(line)
    }
}




