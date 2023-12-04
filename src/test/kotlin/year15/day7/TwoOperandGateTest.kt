package year15.day7

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

class TwoOperandGateTest {

    private val andString = "x AND y -> d"
    private val orString = "x OR y -> e"
    private val leftShiftString = "x LSHIFT 2 -> f"
    private val rightShiftString = "y RSHIFT 2 -> g"

    private val andExpected = 72
    private val orExpected = 507
    private val lShiftExpected = 492
    private val rShiftExpected = 114

    private val andGate = TwoOperandGate(andString)
    private val orGate = TwoOperandGate(orString)
    private val lShiftGate = TwoOperandGate(leftShiftString)
    private val rShiftGate = TwoOperandGate(rightShiftString)

    private lateinit var wireMap: WireMap

    @BeforeEach
    fun setup() {
        wireMap = WireMap()
    }

    @Test
    fun andConstructor() {
        assertEquals("x", andGate.getOperand1())
        assertEquals("y", andGate.getOperand2())
        assertEquals("AND", andGate.getOperation())
        assertEquals("d", andGate.getResultWireName())
    }

    @Test
    fun isResolvable_false_bothMissing() {
        assertFalse(andGate.isResolvable(wireMap))
    }

    @Test
    fun isResolvable_false_oneMissing() {
        wireMap.addWireValue("x", 123.toUShort())
        assertFalse(andGate.isResolvable(wireMap))
    }

    @Test
    fun isResolvable_false_otherOneMissing() {
        wireMap.addWireValue("y", 456.toUShort())
        assertFalse(andGate.isResolvable(wireMap))
    }

    @Test
    fun isResolvable_true() {
        wireMap.addWireValue("x", 123.toUShort())
        wireMap.addWireValue("y", 456.toUShort())
        assertTrue(andGate.isResolvable(wireMap))
    }

    @Test
    fun getResultWireName() {
        assertEquals("d", andGate.getResultWireName())
    }

    @Test
    fun getResultValue_and() {
        wireMap.addWireValue("x", 123.toUShort())
        wireMap.addWireValue("y", 456.toUShort())
        assertEquals(72.toUShort(), andGate.getResultValue(wireMap))
    }

    @Test
    fun getResultValue_or() {
        wireMap.addWireValue("x", 123.toUShort())
        wireMap.addWireValue("y", 456.toUShort())
        assertEquals(507.toUShort(), orGate.getResultValue(wireMap))
    }

    @Test
    fun getResultValue_lshift() {
        wireMap.addWireValue("x", 123.toUShort())
        assertEquals(492.toUShort(), lShiftGate.getResultValue(wireMap))
    }

    @Test
    fun getResultValue_rshift() {
        wireMap.addWireValue("y", 456.toUShort())
        assertEquals(114.toUShort(), rShiftGate.getResultValue(wireMap))
    }
}