package year15.day13

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MainKtTest {
    @Test
    fun testSeatingLinePattern() {
        val input = "Alice would gain 2 happiness units by sitting next to Bob."
        assertTrue(SEATING_LINE_PATTERN.toRegex().matches(input))
    }

    @Test
    fun testSeatingLinePattern_2() {
        val input = "Alice would lose 26 happiness units by sitting next to Carol."
        assertTrue(SEATING_LINE_PATTERN.toRegex().matches(input))
    }
}