package year23.day7



fun main() {

    val startTime = System.currentTimeMillis()

    val hands = getHandsFromFilename("input.txt")
    val readEndTime = System.currentTimeMillis()

    val part1Result = getBidScore(hands, GameRules.NORMAL)
    val part1EndTime = System.currentTimeMillis()

    val part2Result = getBidScore(hands, GameRules.JACKS_WILD)
    val part2EndTime = System.currentTimeMillis()

    println(
        """
        |Read Time: %d ms
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

private fun getHandsFromFilename(filename: String): List<Hand> {
    val reader = object {}.javaClass.getResourceAsStream(filename)!!.bufferedReader()
    return reader.readLines().asSequence()
        .filterNot(String::isEmpty)
        .map(String::trim)
        .map { it.split(" ")}
        .map { Hand(it[0].toCharArray(), it[1].toInt()) }
        .toList()
}

fun getBidScore(hands: List<Hand>, gameRules: GameRules): Int {
    return hands.sortedBy{ it.getHandScore(gameRules) }
        .mapIndexed { index, hand ->  ((index + 1) * hand.bid)}
        .sum()
}


fun getCharacterCounts(charArray: CharArray): Map<Char, Int> {
    return charArray.associateWith {
        keyChar ->  charArray.count{ it == keyChar }
    }
}



