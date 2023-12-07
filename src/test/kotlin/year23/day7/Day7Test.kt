package year23.day7


import kotlin.test.Test
import kotlin.test.assertEquals

class Day7Test {
    @Test
    fun getCharacterCounts() {
        val expected = mapOf(
            'a' to 2,
            'b' to 5,
            'c' to 3,
            'd' to 2,
            'z' to 1
        )
        val actual = getCharacterCounts("aabbbbbcccddz".toCharArray())
        assertEquals(expected, actual)
    }
}