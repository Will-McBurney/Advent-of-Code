package dayOne

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class DigitizerKtTest {

    @Test
    fun `getFirstDigit - two1nine`() {
        assertEquals(2, dayOne.getFirstDigit("two1nine"))
    }

    @Test
    fun `getFirstDigit - eightwothree`() {
        assertEquals(8, dayOne.getFirstDigit("eightwothree"))
    }

    @Test
    fun `getFirstDigit - abcone2threexyz`() {
        assertEquals(1, dayOne.getFirstDigit("abcone2threexyz"))
    }

    @Test
    fun `getFirstDigit - 4nineeightseven2`() {
        assertEquals(4, dayOne.getFirstDigit("4nineeightseven2"))
    }

    @Test
    fun `getLastDigit - twoneight` () {
        assertEquals(8, dayOne.getLastDigit("twoneight"))
    }

    @Test
    fun `getLastDigit - 7pqrstsixteen` () {
        assertEquals(6, dayOne.getLastDigit("7pqrstsixteen"))
    }

    @Test
    fun `getLastDigit - 4nineeightseven2` () {
        assertEquals(2, dayOne.getLastDigit("4nineeightseven2"))
    }
}