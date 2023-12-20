package year16.day02

fun main() {
    val startTime = System.nanoTime()

    //Read input
    val reader = object {}.javaClass.getResourceAsStream("input.txt")!!.bufferedReader()
    val lines = reader.readLines()
    val directionsList: List<List<Direction>> = getDirectionList(lines)

    val readEndTime = System.nanoTime()

    //Do Part 1
    val part1Result = getResult(directionsList, NumberPad())
    val part1EndTime = System.nanoTime()

    //Do Part 2
    val part2Result = getResult(directionsList, DiamondPad())
    val part2EndTime = System.nanoTime()

    //Display output
    println("Input read time: ${elapsedMicroSeconds(startTime, readEndTime)} μs\n")
    println("Part 1: %15s - Time %10d  μs"
        .format(part1Result, elapsedMicroSeconds(readEndTime, part1EndTime)))
    println("Part 2: %15s - Time %10d  μs\n"
        .format(part2Result, elapsedMicroSeconds(part1EndTime, part2EndTime)))
    println("Total time - ${elapsedMicroSeconds(startTime, part2EndTime)} μs")
}

fun getDirectionList(lines: List<String>): List<List<Direction>> {
    return lines.map{line -> line.toCharArray().toList()}
        .map{chars ->
            chars.map {char -> Direction.valueOf(char.toString())}
        }
}

fun elapsedMicroSeconds(start: Long, end: Long): Long = (end - start) / 1000

fun getResult(directionsList: List<List<Direction>>, entryPad: EntryPad): String {
    return directionsList.map { directions -> entryPad.getNextEntry(directions) }
        .joinToString("")
}
