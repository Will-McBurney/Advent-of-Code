package year15.day07

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

class NotGateTest {
    private lateinit var notGate: Gate
    private lateinit var wireMap: WireMap

    @BeforeEach
    fun setup() {
        notGate = NotGate("NOT go -> gp")
        wireMap = WireMap()
    }

    @Test
    fun isResolvable_false() {
        assertFalse(notGate.isResolvable(wireMap))
    }

    @Test
    fun isResolvable_true() {
        wireMap.addWireValue("go", 123.toUShort())
        assertTrue(notGate.isResolvable(wireMap))
    }

    @Test
    fun getResultWireName() {
        assertEquals("gp", notGate.getResultWireName())
    }

    @Test
    fun getResultValue() {
        wireMap.addWireValue("go", 123.toUShort())
        assertEquals(65412.toUShort(), notGate.getResultValue(wireMap))
    }
}