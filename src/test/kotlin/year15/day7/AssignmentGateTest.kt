package year15.day7

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class AssignmentGateTest {

    private val wireMap = WireMap()
    private val assignmentGate = AssignmentGate("123 -> x")

    @Test
    fun isResolvable() {
        assertTrue(assignmentGate.isResolvable(wireMap))
    }

    @Test
    fun getResultWireName() {
        assertEquals("x", assignmentGate.getResultWireName())
    }

    @Test
    fun getResultValue() {
        assertEquals(123.toUShort(), assignmentGate.getResultValue(wireMap))
    }
}