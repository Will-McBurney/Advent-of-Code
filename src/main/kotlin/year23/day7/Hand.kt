package year23.day7

enum class HandType(val rank: Int) {
    FIVE_OF_A_KIND(6),
    FOUR_OF_A_KIND(5),
    FULL_HOUSE(4),
    THREE_OF_A_KIND(3),
    TWO_PAIR(2),
    PAIR(1),
    HIGH_CARD(0),
}

val charToPointsNoWilds = mapOf(
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

const val WILD_JACK = 'J'

data class Hand (val charArray: CharArray, val bid: Int) {

    fun getHandScore(jacksWild: Boolean): Int {
        val charToPoints = if (jacksWild) charToPointsJacksWild else charToPointsNoWilds
        var totalScore = getHandType(jacksWild).rank
        for (char in charArray) {
            totalScore = (totalScore shl 4) + charToPoints[char]!!
        }
        return totalScore
    }

    private fun getHandType(jacksWild: Boolean): HandType {
        val maxCount = getMaxCharacterCount(charArray, jacksWild)
        return when(maxCount) {
            5 -> HandType.FIVE_OF_A_KIND
            4 -> HandType.FOUR_OF_A_KIND
            3 -> if (isFullHouse(charArray, jacksWild)) HandType.FULL_HOUSE else HandType.THREE_OF_A_KIND
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

    private fun getMaxCharacterCount(charArray: CharArray, jacksWild: Boolean): Int {
        if (!jacksWild) {
            return getCharacterCounts(charArray).maxOf { it.value }
        }
        val charList = charArray.toMutableList()
        val jays = charList.count { it == 'J' }
        while(charList.contains(WILD_JACK)) {charList.remove(WILD_JACK)}

        if (charList.isEmpty()) return 5

        return (charList.associateWith { keyChar -> charList.count{ it == keyChar} }
            .maxOf { it.value }) + jays
    }

    private fun isFullHouse(charArray: CharArray, jacksWild: Boolean): Boolean {
        if (charArray.size != 5) {
            throw IllegalArgumentException("Incorrect hand size")
        }
        if (jacksWild && charArray.count { it == WILD_JACK } == 1) {
            return (isTwoPair(charArray))
        }
        val charCounts = getAscendingCardCounts(charArray)
        return (charCounts[0] == 2 && charCounts[1] == 3)

    }

    private fun isTwoPair(charArray: CharArray): Boolean {
        if (charArray.size != 5) {
            throw IllegalArgumentException("Incorrect hand size")
        }
        val cardCounts = getAscendingCardCounts(charArray)
        return (cardCounts[0] == 1 && cardCounts[1] == 2 && cardCounts[2] == 2)
    }

    private fun getAscendingCardCounts(charArray: CharArray): List<Int> {
        return getCharacterCounts(charArray)
            .map { it.value }
            .sorted()
            .toList()
    }
}