package year23.day19

data class Rule(
    val letter: Char,
    val condition: Condition,
    val number: Int
) {
    fun isGreaterThan():Boolean = condition == Condition.GREATER_THAN

    fun isMetBy(part: Part): Boolean = condition.test(part.getValue(letter), number)
}
