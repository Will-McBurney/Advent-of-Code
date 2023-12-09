package year23.day9

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MainKtTest {
    @Test
    fun getDifferencesTest() {
        assertEquals(listOf(2, 3, 4, 5, 6), getDifferences(listOf(1, 3, 6, 10, 15, 21)))
    }

    @Test
    fun extrapolate() {
        assertEquals(28, extrapolate(listOf(1, 3, 6, 10, 15, 21), Direction.FORWARDS))
    }

    @Test
    fun extrapolateBackwards() {
        assertEquals(5, extrapolate(listOf(10, 13, 16, 21, 30, 45), Direction.BACKWARDS))
    }
}