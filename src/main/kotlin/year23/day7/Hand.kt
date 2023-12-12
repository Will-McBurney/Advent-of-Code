package year23.day7

const val JACK = 'J'

data class Hand (
    val charArray:
    CharArray, val bid: Int) {

    fun getHandScore(gameRules: GameRules): Int {
        val charToPoints = gameRules.cardPoints
        var totalScore = getHandType(gameRules).rank
        for (char in charArray) {
            totalScore = (totalScore shl 4) + charToPoints[char]!!
        }
        return totalScore
    }

    private fun getHandType(gameRules: GameRules): HandType {
        val maxCount = getMaxCharacterCount(charArray, gameRules)
        return when(maxCount) {
            5 -> HandType.FIVE_OF_A_KIND
            4 -> HandType.FOUR_OF_A_KIND
            3 -> if (isFullHouse(charArray, gameRules)) HandType.FULL_HOUSE else HandType.THREE_OF_A_KIND
            2 -> if (isTwoPair(charArray)) HandType.TWO_PAIR else HandType.PAIR
            else -> HandType.HIGH_CARD
        }
    }

    private fun getMaxCharacterCount(charArray: CharArray, gameRules: GameRules): Int {
        if (gameRules == GameRules.NORMAL || !charArray.contains(JACK)) {
            return getCharacterCounts(charArray).maxOf { it.value }
        }

        val charList = charArray.toMutableList()
        val jays = charList.count { it == JACK }
        while(charList.contains(JACK)) {charList.remove(JACK)}

        if (charList.isEmpty()) return 5

        return (charList.associateWith { keyChar -> charList.count{ it == keyChar} }
            .maxOf { it.value }) + jays
    }

    private fun isFullHouse(charArray: CharArray, gameRules: GameRules): Boolean {
        if (charArray.size != 5) {
            throw IllegalArgumentException("Incorrect hand size")
        }
        if (gameRules == GameRules.JACKS_WILD && charArray.count { it == JACK } == 1) {
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

    override fun toString(): String {
        return "Hand(charArray=${charArray.contentToString()}, bid=$bid)"
    }
}