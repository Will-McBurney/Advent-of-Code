package year15.day16

import org.junit.jupiter.api.Test
import kotlin.test.*

class SueTest {

    @Test
    fun isPossibleTest_True_Present() {
        val sue = Sue(1)
        sue.addStuffCount("cats", 4)
        assertTrue(Part1SueChecker().isPossible(sue, mapOf("cats" to 4, "children" to 3, "dogs" to 7)))
    }

    @Test
    fun isPossibleMapTest_True() {
        val sue = Sue(1)
        sue.addStuffCount("cat", 4)
        sue.addStuffCount("children", 3)
        sue.addStuffCount("car", 8)
        assertFalse(Part2SueChecker().isPossible(sue, mapOf("cats" to 4, "children" to 3, "dogs" to 7)))
    }
}