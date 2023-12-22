package year16.day04

data class Room (
    val label: String,
    val number: Int,
    val expectedCheckSum: String
) {
    fun isRealRoom(): Boolean {
        val actualCheckSum = label.filter { char -> '-' != char }
            .associateWith { char -> label.count { c -> c == char } }
            .toList()
            .asSequence()
            .sortedWith(compareByDescending<Pair<Char, Int>>  { pair -> pair.second }.thenBy { pair -> pair.first })
            .take(5)
            .joinToString("") { pair -> pair.first.toString() }

        return actualCheckSum == expectedCheckSum
    }
}