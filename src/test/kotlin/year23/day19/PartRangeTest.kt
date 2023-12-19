package year23.day19

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class PartRangeTest {
    private val startingPartRange = PartRange(1, 4000)
    private val smallRange = PartRange (
        (100..200),
        (200 .. 250),
        (300 .. 301),
        (400 .. 400)
    )

    @Test
    fun getXRange() {
        assertEquals((1..4000), startingPartRange.xRange)
    }

    @Test
    fun getMRange() {
        assertEquals((1..4000), startingPartRange.mRange)
    }

    @Test
    fun getARange() {
        assertEquals((1..4000), startingPartRange.aRange)
    }

    @Test
    fun getSRange() {
        assertEquals((1..4000), startingPartRange.sRange)
    }

    @Test
    fun getRangeSize_x() {
        assertEquals(101, smallRange.getRangeSize('x'))
    }

    @Test
    fun getRangeSize_m() {
        assertEquals(51, smallRange.getRangeSize('m'))
    }

    @Test
    fun getRangeSize_a() {
        assertEquals(2, smallRange.getRangeSize('a'))
    }

    @Test
    fun getRangeSize_s() {
        assertEquals(1, smallRange.getRangeSize('s'))
    }

    @Test
    fun isSplitNeeded_Equivalence_true() {
        val rule = Rule('x', Condition.GREATER_THAN, 150)
        assertTrue(smallRange.isSplitNeeded(rule))
    }

    @Test
    fun isSplitNeeded_Equivalence_false() {
        val rule = Rule('m', Condition.LESS_THAN, 150)
        assertFalse(smallRange.isSplitNeeded(rule))
    }

    @Test
    fun isSplitNeeded_Boundary_Low_GreaterThan_true() {
        val rule = Rule('a', Condition.GREATER_THAN, 300)
        assertTrue(smallRange.isSplitNeeded(rule))
    }

    @Test
    fun isSplitNeeded_Boundary_Low_LessThan_false() {
        val rule = Rule('a', Condition.LESS_THAN, 300)
        assertFalse(smallRange.isSplitNeeded(rule))
    }

    @Test
    fun isSplitNeeded_Boundary_High_GreaterThan_false() {
        assertFalse(smallRange.isSplitNeeded(Rule('a', Condition.GREATER_THAN, 301)))
    }

    @Test
    fun isSplitNeeded_Boundary_High_LessThan_true() {
        assertTrue(smallRange.isSplitNeeded(Rule('a', Condition.LESS_THAN, 301)))
    }

    @Test
    fun isSplitNeeded_SizeOne_alwaysFalse_greaterThan() {
        assertFalse(smallRange.isSplitNeeded(Rule('s', Condition.GREATER_THAN, 399)))
        assertFalse(smallRange.isSplitNeeded(Rule('s', Condition.GREATER_THAN, 400)))
        assertFalse(smallRange.isSplitNeeded(Rule('s', Condition.GREATER_THAN, 401)))
    }

    @Test
    fun isSplitNeeded_SizeOne_alwaysFalse_lessThan() {
        assertFalse(smallRange.isSplitNeeded(Rule('s', Condition.LESS_THAN, 399)))
        assertFalse(smallRange.isSplitNeeded(Rule('s', Condition.LESS_THAN, 400)))
        assertFalse(smallRange.isSplitNeeded(Rule('s', Condition.LESS_THAN, 401)))
    }

    @Test
    fun isFullRangeTrue_True() {
        assertTrue(smallRange.isFullRangeTrue(Rule('x', Condition.GREATER_THAN, 99)))
        assertTrue(smallRange.isFullRangeTrue(Rule('m', Condition.LESS_THAN, 251)))
    }
    @Test
    fun isFullRangeTrue_False() {
        assertFalse(smallRange.isFullRangeTrue(Rule('a', Condition.GREATER_THAN, 300)))
        assertFalse(smallRange.isFullRangeTrue(Rule('s', Condition.LESS_THAN, 400)))
    }

    @Test
    fun isFullRangeFalse_True() {
        assertTrue(smallRange.isFullRangeFalse(Rule('x', Condition.LESS_THAN, 100)))
        assertTrue(smallRange.isFullRangeFalse(Rule('s', Condition.GREATER_THAN, 400)))
    }
    @Test
    fun isFullRangeFalse_False() {
        assertFalse(smallRange.isFullRangeFalse(Rule('m', Condition.GREATER_THAN, 249)))
        assertFalse(smallRange.isFullRangeFalse(Rule('a', Condition.LESS_THAN, 301)))
    }

    @Test
    fun splitRange_Eq_greaterThan_X() {
        val splitRanges: Pair<PartRange, PartRange> = smallRange.splitRange(Rule('x', Condition.GREATER_THAN, 150))

        assertEquals((100..150), splitRanges.first.xRange)
        assertEquals((151..200), splitRanges.second.xRange)

        assertEquals((200..250), splitRanges.first.mRange)
        assertEquals((200..250), splitRanges.second.mRange)
        assertEquals((300..301), splitRanges.first.aRange)
        assertEquals((300..301), splitRanges.second.aRange)
        assertEquals((400..400), splitRanges.first.sRange)
        assertEquals((400..400), splitRanges.second.sRange)
    }

    @Test
    fun splitRange_Boundary_lessThan_A() {
        val splitRanges: Pair<PartRange, PartRange> = smallRange.splitRange(Rule('a', Condition.LESS_THAN, 301))

        assertEquals((300..300), splitRanges.first.aRange)
        assertEquals((301..301), splitRanges.second.aRange)

        assertEquals((100..200), splitRanges.first.xRange)
        assertEquals((100..200), splitRanges.second.xRange)
        assertEquals((200..250), splitRanges.first.mRange)
        assertEquals((200..250), splitRanges.second.mRange)
        assertEquals((400..400), splitRanges.first.sRange)
        assertEquals((400..400), splitRanges.second.sRange)
    }

    @Test
    fun getPartPossibilities() {
        val expected = 10302L // x = 101 * m = 51 * a = 2 * s = 1
        assertEquals(expected, smallRange.getPartPossibilities())
    }
}