package year15.day10

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class MainKtTest {

    @Test
    fun seeSayNumberString_0() {
        assertEquals("11", seeSayNumberString("1"))
    }

    @Test
    fun seeSayNumberString_1() {
        assertEquals("21", seeSayNumberString("11"))
    }

    @Test
    fun seeSayNumberString_2() {
        assertEquals("312211", seeSayNumberString("111221"))
    }
}