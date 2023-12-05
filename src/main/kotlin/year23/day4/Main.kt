package year23.day4

lateinit var inputLines: List<String>

fun main() {
    val startTime = System.currentTimeMillis()
    val reader = object {}.javaClass.getResourceAsStream("input.txt")!!.bufferedReader()
    inputLines = reader.readLines()
    val getPart1Result = getPart1Result()
    val getPart2Result = getPart2Result()
    val endTime = System.currentTimeMillis()
    println(
        """
        |Part One: $getPart1Result 
        |Part Two: $getPart2Result
        |Calculation time - ${endTime - startTime}ms
        |""".trimMargin()
    )
}

fun scoreCard(card: Pair<Set<Int>, Set<Int>>):Int {
    var count = getMatchesCount(card)
    if (count == 0) {
        return 0
    }
    var score = 1
    while (count > 1) {
        score *= 2
        count--
    }
    return score
}

fun getPart1Result(): Int {
    return inputLines
        .map { it.substringAfter(": ") }
        .map { it.split("|") }
        .map { Pair(stringToInts(it[0]), stringToInts(it[1]))}
        .sumOf{ scoreCard(it) }
}

fun stringToInts(input: String): Set<Int> {
    return input.trim()
        .split(" ")
        .filter(String::isNotEmpty)
        .map ( String::toInt )
        .toSet()
}
private fun getMatchesCount(card: Pair<Set<Int>, Set<Int>>): Int {
    val winningNumbers = card.first
    val guessedNumbers = card.second
    return winningNumbers.count(guessedNumbers::contains)
}

fun getPart2Result(): Int {
    val startingCards = inputLines.map{
        it.substringAfter(": ")
    }
        .map { it.split("|") }
        .map { Pair(stringToInts(it[0]), stringToInts(it[1]))}
    val cardCounts = startingCards.map { 1 }.toMutableList()
    for (cardIndex in startingCards.indices) {
        val score = getMatchesCount(startingCards[cardIndex])
        for (i in cardIndex+ 1 ..< cardIndex+ 1 + score) {
            cardCounts[i] += cardCounts[cardIndex]
        }
    }
    return cardCounts.sum()
}