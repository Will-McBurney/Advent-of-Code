package year23.day7

var jacksWild = false

fun main() {
    val startTime = System.currentTimeMillis()
    val reader = object {}.javaClass.getResourceAsStream("input.txt")!!.bufferedReader()
    val lines = reader.readLines()
//    val lines = """
//        32T3K 765
//        T55J5 684
//        KK677 28
//        KTJJT 220
//        QQQJA 483
//    """.trimIndent().split("\n")
    val hands = getHands(lines)
    val part1Result = getPart1Result(hands)
    jacksWild = true
    val part2Result = getPart2Result(hands)
    val endTime = System.currentTimeMillis()
    println(
        """
        |Part One: $part1Result
        |Part Two: $part2Result 
        |Calculation time - ${endTime - startTime}ms
        |""".trimMargin()
    )
}

fun getHands(lines: List<String>): List<Hand> {
    return lines.filterNot(String::isEmpty)
        .map(String::trim)
        .map { it.split(" ")}
        .map { Hand(it[0].toCharArray(), it[1].toInt()) }
}

fun getPart1Result(hands: List<Hand>): Int {
    hands.sortedBy(Hand::getHandScore)
        .forEach{ println("$it - ${it.getHandType()} - ${it.getHandScore()}") }
    return hands.sortedBy (Hand::getHandScore)
        .mapIndexed { index, hand ->  ((index + 1) * hand.bid)}
        .sum()
}

fun getPart2Result(hands: List<Hand>): Int {
    hands.sortedBy(Hand::getHandScore)
        .forEach{ println("$it - ${it.getHandType()} - ${it.getHandScore()}") }
    return hands.sortedBy (Hand::getHandScore)
        .mapIndexed { index, hand ->  ((index + 1) * hand.bid)}
        .sum()
}


fun getCharacterCounts(charArray: CharArray): Map<Char, Int> {
    return charArray.associateWith {
        keyChar ->  charArray.count{ it == keyChar }
    }
}

fun getMaxCharacterCount(charArray: CharArray): Int {
    if (!jacksWild) {
        return charArray.associateWith { keyChar -> charArray.count{ it == keyChar} }
            .maxOf { it.value }
    }
    val charList = charArray.toMutableList()
    val jays = charList.count { it == 'J' }
    while(charList.contains('J')) {charList.remove('J')}
    if (charList.isEmpty()) return 5
    return (charList.associateWith { keyChar -> charList.count{ it == keyChar} }
        .maxOf { it.value })+ jays
}

fun isFullHouse(charArray: CharArray): Boolean {
    if (charArray.size != 5) {
        throw IllegalArgumentException("Incorrect hand size")
    }
    if (!jacksWild || !charArray.contains('J')) {
        val charCounts = getAscendingCardCounts(charArray)
        return (charCounts[0] == 2 && charCounts[1] == 3)
    }
    return (isTwoPair(charArray))

}

fun isTwoPair(charArray: CharArray): Boolean {
    if (charArray.size != 5) {
        throw IllegalArgumentException("Incorrect hand size")
    }
    val cardCounts = getAscendingCardCounts(charArray)
    return (cardCounts[0] == 1 && cardCounts[1] == 2 && cardCounts[2] == 2)
}

fun getAscendingCardCounts(charArray: CharArray): List<Int> {
    return getCharacterCounts(charArray)
        .map { it.value }
        .sorted()
        .toList()
}

enum class HandType(val rank: Int) {
    FIVE_OF_A_KIND(6),
    FOUR_OF_A_KIND(5),
    FULL_HOUSE(4),
    THREE_OF_A_KIND(3),
    TWO_PAIR(2),
    PAIR(1),
    HIGH_CARD(0),
}

val charToPoints = mapOf(
    '2' to 1,
    '3' to 2,
    '4' to 3,
    '5' to 4,
    '6' to 5,
    '7' to 6,
    '8' to 7,
    '9' to 8,
    'T' to 9,
    'J' to 10,
    'Q' to 11,
    'K' to 12,
    'A' to 13
)

val charToPointsJacksWild = mapOf(
    'J' to 0,
    '2' to 1,
    '3' to 2,
    '4' to 3,
    '5' to 4,
    '6' to 5,
    '7' to 6,
    '8' to 7,
    '9' to 8,
    'T' to 9,
    'Q' to 11,
    'K' to 12,
    'A' to 13
)

data class Hand (val charArray: CharArray, val bid: Int) {

    fun getHandScore(): Int {
        getMaxCharacterCount(charArray)
        var totalScore = getHandType().rank
        for (char in charArray) {
            totalScore = (totalScore * 16) + charToPoints[char]!!
        }
        return totalScore
    }

    fun getHandType(): HandType {
        val maxCount = getMaxCharacterCount(charArray)
        return when(maxCount) {
            5 -> HandType.FIVE_OF_A_KIND
            4 -> HandType.FOUR_OF_A_KIND
            3 -> if (isFullHouse(charArray)) HandType.FULL_HOUSE else HandType.THREE_OF_A_KIND
            2 -> if (isTwoPair(charArray)) HandType.TWO_PAIR else HandType.PAIR
            else -> HandType.HIGH_CARD
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Hand

        if (!charArray.contentEquals(other.charArray)) return false
        if (bid != other.bid) return false

        return true
    }

    override fun hashCode(): Int {
        var result = charArray.contentHashCode()
        result = 31 * result + bid
        return result
    }
}