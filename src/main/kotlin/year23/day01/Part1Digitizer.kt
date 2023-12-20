package year23.day01

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