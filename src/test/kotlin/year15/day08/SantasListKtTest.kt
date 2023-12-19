package year15.day08

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class SantasListKtTest {

    @Test
    fun getStringCodeLength_0() {
        assertEquals(2, getStringCodeLength(""))
    }

    @Test
    fun getStringCodeLength_1() {
        assertEquals(10, getStringCodeLength("aaa\\\"aaa"))
    }

    @Test
    fun getStringCodeLength_2() {
        assertEquals(6, getStringCodeLength("\\x27"))
    }

    @Test
    fun getStringMemoryLength_0() {
        assertEquals(0, getStringMemoryLength(""))
    }

    @Test
    fun getStringMemoryLength_1() {
        assertEquals(7, getStringMemoryLength("aaa\"aaa"))
    }

    @Test
    fun getStringMemoryLength_2() {
        assertEquals(1, getStringMemoryLength("\\x27"))
    }

    @Test
    fun getStringMemoryLength_3() {
        assertEquals(18, getStringMemoryLength( "d\\\\gkbqo\\\\fwukyxab\\\"u"))
    }

    @Test
    fun getStringMemoryLength_4() {
        assertEquals(14, getStringMemoryLength("x\\\"\\xcaj\\\\xwwvpdldz"))
    }

    @Test
    fun getStringMemoryLength_5() {
        assertEquals(4, getStringMemoryLength("\\\\x27"))
    }

    @Test
    fun getEncodedStringLength_0() {
        assertEquals(6, getEncodedStringLength(""))
    }

    @Test
    fun getEncodedStringLength_1() {
        assertEquals(9, getEncodedStringLength("abc"))
    }

    @Test
    fun getEncodedStringLength_2() {
        assertEquals(16, getEncodedStringLength("aaa\\\"aaa"))
    }
}