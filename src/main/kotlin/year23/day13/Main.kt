package year23.day13

fun main() {
    val startTime = System.currentTimeMillis()

    //Read input
    val reader = object {}.javaClass.getResourceAsStream("input.txt")!!.bufferedReader()
    val patterns = getPatterns(reader.readLines())
    val readEndTime = System.currentTimeMillis()

    //Do Part 1
    val part1Result = getPart1Result(patterns)
    val part1EndTime = System.currentTimeMillis()

    //Do Part 2
    val part2Result = getPart2Result()
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

fun getPatterns(lines: List<String>): List<Pattern> {
    val patterns = mutableListOf<Pattern>()
    var currentLines = mutableListOf<String>()
    for (line in lines) {
        if (line.isBlank()) {
            patterns.add(Pattern(currentLines))
            currentLines = mutableListOf<String>()
        }
        else {
            currentLines.add(line)
        }
    }
    patterns.add(Pattern(currentLines))
    return patterns
}

fun getPart1Result(patterns: List<Pattern>): Int {
    var sum = 0;
    for (pattern in patterns) {
        var rows = pattern.findHorizontalReflection()
        var columns = pattern.findVerticalReflection()
        if (rows == -1) {
            sum += 100 * columns
        } else {
            sum += rows;
        }
        println("$pattern - $rows - $columns")
    }

    return sum;
}

fun getPart2Result(): Int {
    return 0
}
