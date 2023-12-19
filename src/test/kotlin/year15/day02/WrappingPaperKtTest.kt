package year15.day02

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class WrappingPaperKtTest {

    @Test
    fun getDimensions() {
        assertEquals(listOf(29L, 13L, 26L), getDimensions("29x13x26"))
    }

    @Test
    fun getArea() {
        assertEquals(58, getArea(listOf(2L, 3L, 4L)))
    }

    @Test
    fun getRibbon() {
        assertEquals(34L, year15.day02.getRibbon(listOf(2L, 3L, 4L)))
    }
}