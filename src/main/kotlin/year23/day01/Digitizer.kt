package year23.day01

interface Digitizer {
    fun getFirstDigit(inputString: String): Int
    fun getLastDigit(inputString: String): Int
}

class Part1Digitizer: Digitizer {
    override fun getFirstDigit(inputString: String): Int {
        return inputString.first { char -> isDigit(char) }.digitToInt()
    }

    override fun getLastDigit(inputString: String): Int {
        return inputString.last { char -> isDigit(char) }.digitToInt()
    }

    private fun isDigit(char: Char): Boolean {
        return char in ('0'..'9')
    }

}

class Part2Digitizer: Digitizer {
    companion object {
        val digitMap: Map<String, Int> = mapOf(
            "1" to 1,
            "2" to 2,
            "3" to 3,
            "4" to 4,
            "5" to 5,
            "6" to 6,
            "7" to 7,
            "8" to 8,
            "9" to 9,
            "one" to 1,
            "two" to 2,
            "three" to 3,
            "four" to 4,
            "five" to 5,
            "six" to 6,
            "seven" to 7,
            "eight" to 8,
            "nine" to 9
        )
    }

    override fun getFirstDigit(inputString: String): Int {
        val firstDigit = digitMap.keys.filter {key -> inputString.contains(key)}
            .minBy {key -> inputString.indexOf(key)}
        return digitMap[firstDigit]!!
    }

    override fun getLastDigit(inputString: String): Int {
        val firstDigit = digitMap.keys.filter {key -> inputString.contains(key)}
            .maxBy {key -> inputString.lastIndexOf(key)}
        return digitMap[firstDigit]!!
    }
}