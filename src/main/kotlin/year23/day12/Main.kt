package year23.day12

fun main() {
    val startTime = System.currentTimeMillis()

    val reader = object {}.javaClass.getResourceAsStream("input.txt")!!.bufferedReader()
    val rows = getRows(reader.readLines())

    //rows.forEach { println("${it.getArrangementsCount()} - ${it.getArrangementsByBruteForce()} - ${it}" ) }

    val readEndTime = System.currentTimeMillis()

    //Do Part 1
    val part1Result = getPart1Result(rows)
    val part1EndTime = System.currentTimeMillis()

    val part2Rows = rows.map{ row -> Row((row.startingState+"?").repeat(4) + row.startingState,
        (List(5) {row.configuration}).flatten())}
    //println(part2Rows[0])

    //part2Rows.forEach { println("${it.getArrangementsCount()} - ${it}" ) }

    //Do Part 2
    val part2Result = getPart1Result(part2Rows)
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

fun getRows(lines: List<String>): List<Row> {
    return lines.map { it.trim() }
        .filterNot {it.isEmpty()}
        .map { it.split(" ") }
        .map { Row(it[0],
            it[1].split(",")
                .map{ value -> value.toInt() }
                .toList()
            )
        }
}

fun getPart1Result(rows: List<Row>): Long {
    return rows.parallelStream().mapToLong { it.getArrangementsCount() }.sum()
}

fun getPart2Result(): Long {
    return 0
}
