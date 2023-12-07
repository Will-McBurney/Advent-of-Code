package year23.day7


import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

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

    @Test
    fun getMaxCharacterCount() {
        assertEquals(5, getMaxCharacterCount("aabbbbbcccddz".toCharArray()))
    }

    @Test
    fun isFullHouseTrue() {
        assertTrue(isFullHouse("ababb".toCharArray()))
    }

    @Test
    fun isFullHouseFalse() {
        assertFalse(isFullHouse("ababc".toCharArray()))
    }

    @Test
    fun isTwoPairFalse() {
        assertFalse(isTwoPair("ababb".toCharArray()))
    }

    @Test
    fun isTwoPairTrue() {
        assertTrue(isTwoPair("ababc".toCharArray()))
    }

    @Test
    fun getHandScore() {
        val hand = Hand("AAAA2".toCharArray(), 123)
        assertEquals(5033152, hand.getHandScore())
    }
}