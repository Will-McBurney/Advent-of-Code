package year23.day7

var jacksWild = false

private const val WILD_JACK = 'J'

fun main() {

    val startTime = System.currentTimeMillis()
    val hands = getHandsFromFilename("input.txt")
    val readEndTime = System.currentTimeMillis()
    val part1Result = getBidScore(hands)
    val part1EndTime = System.currentTimeMillis()

    jacksWild = true
    val part2Result = getBidScore(hands)
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

fun getHands(lines: List<String>): List<Hand> {
    return lines.filterNot(String::isEmpty)
        .map(String::trim)
        .map { it.split(" ")}
        .map { Hand(it[0].toCharArray(), it[1].toInt()) }
}

fun getBidScore(hands: List<Hand>): Int {
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
        return getCharacterCounts(charArray).maxOf { it.value }
    }
    val charList = charArray.toMutableList()
    val jays = charList.count { it == WILD_JACK }
    while(charList.contains(WILD_JACK)) {charList.remove(WILD_JACK)}

    if (charList.isEmpty()) return 5 // charList was all Jacks

    return (charList.associateWith { keyChar -> charList.count{ it == keyChar} }
        .maxOf { it.value }) + jays
}

fun isFullHouse(charArray: CharArray): Boolean {
    if (charArray.size != 5) {
        throw IllegalArgumentException("Incorrect hand size")
    }
    if (jacksWild && charArray.count { it == WILD_JACK } == 1) {
        return (isTwoPair(charArray))
    }
    val charCounts = getAscendingCardCounts(charArray)
    return (charCounts[0] == 2 && charCounts[1] == 3)

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

val charToPointsNormalJacks = mapOf(
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
        val charToPoints = if (jacksWild) charToPointsJacksWild else charToPointsNormalJacks
        getMaxCharacterCount(charArray)
        var totalScore = getHandType().rank
        for (char in charArray) {
            totalScore = (totalScore shl 4) + charToPoints[char]!!
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