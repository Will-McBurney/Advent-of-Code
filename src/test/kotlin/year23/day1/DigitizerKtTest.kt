package year23.day1

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class DigitizerKtTest {

    @Test
    fun `getFirstDigit - two1nine`() {
        assertEquals(2, getFirstDigit("two1nine"))
    }

    @Test
    fun `getFirstDigit - eightwothree`() {
        assertEquals(8, getFirstDigit("eightwothree"))
    }

    @Test
    fun `getFirstDigit - abcone2threexyz`() {
        assertEquals(1, getFirstDigit("abcone2threexyz"))
    }

    @Test
    fun `getFirstDigit - 4nineeightseven2`() {
        assertEquals(4, getFirstDigit("4nineeightseven2"))
    }

    @Test
    fun `getLastDigit - twoneight` () {
        assertEquals(8, getLastDigit("twoneight"))
    }

    @Test
    fun `getLastDigit - 7pqrstsixteen` () {
        assertEquals(6, getLastDigit("7pqrstsixteen"))
    }

    @Test
    fun `getLastDigit - 4nineeightseven2` () {
        assertEquals(2, getLastDigit("4nineeightseven2"))
    }
}