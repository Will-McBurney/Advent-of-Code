package year15.day11

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class MainKtTest {
    @Test
    fun increaseLetterTripleTest() {
        assertTrue(hasIncreasingLetterTriple("hijklmmn".toCharArray()))
        assertFalse(hasIncreasingLetterTriple("abbceffg".toCharArray()))
        assertTrue(hasIncreasingLetterTriple("hijaabbcc".toCharArray()))
        assertTrue(hasIncreasingLetterTriple("aabbcchij".toCharArray()))
    }

    @Test
    fun `no blood for oil test`() {
        assertFalse(hasNoOil("hijklmmn".toCharArray()))
        assertTrue(hasNoOil("abbceffg".toCharArray()))
    }

    @Test
    fun hasTwoDoubleLetters() {
        assertFalse(hasTwoDoubleLetters("hijklmmn".toCharArray()))
        assertTrue(hasTwoDoubleLetters("abbceffg".toCharArray()))
        assertFalse(hasTwoDoubleLetters("abbbegjk".toCharArray()))
        assertTrue(hasTwoDoubleLetters("abbbbgjk".toCharArray()))
    }
}