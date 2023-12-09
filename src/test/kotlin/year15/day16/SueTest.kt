package year15.day16

import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class SueTest {

    @Test
    fun isPossibleTest_True_Present() {
        val sue = Sue(1)
        sue.addStuffCount("cats", 4)
        assertTrue(sue.isPossiblePart1("cats", 4))
    }

    @Test
    fun isPossibleTest_True_Absent() {
        val sue = Sue(1)
        sue.addStuffCount("dogs", 4)
        assertTrue(sue.isPossiblePart1("cats", 4))
    }

    @Test
    fun isPossibleTest_False() {
        val sue = Sue(1)
        sue.addStuffCount("cat", 4)
        assertTrue(sue.isPossiblePart1("cats", 3))
    }

    @Test
    fun isPossibleMapTest_True() {
        val sue = Sue(1)
        sue.addStuffCount("cat", 4)
        sue.addStuffCount("children", 3)
        sue.addStuffCount("car", 8)
        assertTrue(sue.isPossiblePart1(mapOf("cats" to 4, "children" to 3, "dogs" to 7)))
    }
}