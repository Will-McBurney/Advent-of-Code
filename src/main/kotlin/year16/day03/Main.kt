package year16.day03

fun main() {
    val startTime = System.nanoTime()

    //Read input
    val reader = object {}.javaClass.getResourceAsStream("input.txt")!!.bufferedReader()
    val lines = reader.readLines()
    val part1Triangles = getTrianglesPart1(lines)
    val part2Triangles = getTrianglesPart2(lines)

    val readEndTime = System.nanoTime()

    //Do Part 1
    val part1Result = getResults(part1Triangles)
    val part1EndTime = System.nanoTime()

    //Do Part 2
    val part2Result = getResults(part2Triangles)
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

fun getTrianglesPart1(lines: List<String>): List<List<Int>> {
    return lines.map{ it.trim() }
        .filter { it.isNotEmpty() }
        .map{ line -> line.split("\\s".toRegex())
            .filter(String::isNotEmpty)
            .map(String::toInt)
        }
}

fun getTrianglesPart2(lines: List<String>): List<List<Int>> {
    return (lines.indices step 3).map{ lineNumber ->
        val triangleGrid = lines.subList(lineNumber, lineNumber + 3)
            .map(String::trim)
            .map {it.split(" ")
                .filter (String::isNotEmpty)
                .map (String::toInt)
            }
        return@map (0 ..< 3).map{columnNumber ->
            listOf(triangleGrid[0][columnNumber], triangleGrid[1][columnNumber], triangleGrid[2][columnNumber])}
    }.flatten()
}

fun getResults(triangles: List<List<Int>>): Int =
    triangles.count { triangle -> triangle.sum() > 2 * triangle.max() }
