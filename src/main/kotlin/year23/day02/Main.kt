package year23.day02

fun main() {
    val startTime = System.nanoTime()

    //Read input
    val reader = object {}.javaClass.getResourceAsStream("input.txt")!!.bufferedReader()
    val lines = reader.readLines()
    val games = getGames(lines)

    val readEndTime = System.nanoTime()

    //Do Part 1
    val bag: Map<String, Int> = mapOf("red" to 12, "green" to 13, "blue" to 14)
    val part1Result = getPart1Result(games, bag)
    val part1EndTime = System.nanoTime()

    //Do Part 2
    val part2Result = getPart2Result(games)
    val part2EndTime = System.nanoTime()

    //Display output
    println("Input read time: ${elapsedMicroSeconds(startTime, readEndTime)} μs\n")
    println("Part 1: %15d - Time %10d  μs"
        .format(part1Result, elapsedMicroSeconds(readEndTime, part1EndTime)))
    println("Part 2: %15d - Time %10d  μs\n"
        .format(part2Result, elapsedMicroSeconds(part1EndTime, part2EndTime)))
    println("Total time - ${elapsedMicroSeconds(startTime, part2EndTime)} μs")
}

fun getGames(lines:List<String>): List<Game> {
    return lines.map { line -> Game(line) }
}

fun getPart1Result(games: List<Game>, bag: Map<String, Int>): Int {
    return games.filter { game -> game.isPossible(bag)}
        .sumOf {game -> game.id}
}

fun getPart2Result(games: List<Game>): Int {
    return games.sumOf { game -> game.power}
}

fun elapsedMicroSeconds(start: Long, end: Long): Long = (end - start) / 1000