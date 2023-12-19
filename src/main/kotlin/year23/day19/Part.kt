package year23.day19

data class Part(
    val x: Int,
    val m: Int,
    val a: Int,
    val s: Int
) {
    fun getValue(letter:Char): Int {
        return when(letter) {
            'x' -> x
            'm' -> m
            'a' -> a
            's' -> s
            else -> throw IllegalArgumentException("Bad letter: char")
        }
    }
}
