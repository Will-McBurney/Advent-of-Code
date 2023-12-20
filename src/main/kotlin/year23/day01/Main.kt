package year23.day01

fun main() {
    val startTime = System.currentTimeMillis()

    //Read input
    val reader = object {}.javaClass.getResourceAsStream("input.txt")!!.bufferedReader()
    val lines = reader.readLines().map{ line -> line.trim()}
        .filter { line -> line.isNotEmpty() }
    val readEndTime = System.currentTimeMillis()

    //Do Part 1
    val part1Result = getResult(lines, Part1Digitizer())
    val part1EndTime = System.currentTimeMillis()

    //Do Part 2
    val part2Result = getResult(lines, Part2Digitizer())
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

fun getResult(lines: List<String>, digitizer: Digitizer): Int {
    return lines.sumOf{line ->
        digitizer.getFirstDigit(line) * 10 + digitizer.getLastDigit(line)
    }
}




