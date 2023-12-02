package year15.day5

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class NaughtyOrNiceKtTest {

    @Test
    fun hasThreeVowels_exactly3() {
        assertTrue(hasThreeVowels("irate"))
    }

    @Test
    fun hasThreeVowels_moreThan3() {
        assertTrue(hasThreeVowels("the quick brown fox jumped over the lazy dog"))
    }

    @Test
    fun hasThreeVowels_True_withOnly2Unique() {
        assertTrue(hasThreeVowels("agate"))
    }

    @Test
    fun hasThreeVowels_False_withOnly2Unique() {
        assertFalse(hasThreeVowels("broke"))
    }

    @Test
    fun hasDoubleLetter_true() {
        assertTrue(hasDoubleLetter("bookkeeper"))
    }

    @Test
    fun hasDoubleLetter_false() {
        assertFalse(hasDoubleLetter("bokeper"))
    }

    @Test
    fun containsNaughtyStrings_true() {
        assertTrue(containsNaughtyString("ab"))
        assertTrue(containsNaughtyString("bacdatassup"))
        assertTrue(containsNaughtyString("sexy"))
        assertTrue(containsNaughtyString("o=pq=o"))
    }

    @Test
    fun containsNaughtyStrings_false() {
        assertFalse(containsNaughtyString("bookkeeper"))
    }

    @Test
    fun getNiceListCount() {
        val input = """
            ugknbfddgicrmopn
            aaa
            jchzalrnumimnmhp
            haegwjzuvuyypxyu
            dvszwmarrgswjxmb
        """.trimIndent()
        assertEquals(2L, getNiceStringCount_part1(input))
    }

    @Test
    fun hasDuplicateTwoLetterSequence_earlyTrue() {
        assertTrue(hasDuplicateTwoLetterSequence("abab"))
    }

    @Test
    fun hasDuplicateTwoLetterSequence_harderTrue() {
        assertTrue(hasDuplicateTwoLetterSequence("2534abafsdabzvxc"))
    }

    @Test
    fun hasDuplicateTwoLetterSequence_LastPossibleTrue() {
        assertTrue(hasDuplicateTwoLetterSequence("2534afsdzvxcabab"))
    }

    @Test
    fun hasDuplicateTwoLetterSequence_NoOverlapRuleCheck() {
        assertFalse(hasDuplicateTwoLetterSequence("aaa"))
    }

    @Test
    fun hasDuplicateTwoLetterSequence_False() {
        assertFalse(hasDuplicateTwoLetterSequence("asdfadgfhja"))
    }

    @Test
    fun hasSandwich_True_Early() {
        assertTrue(hasSandwich("abacus"))
    }

    @Test
    fun hasSandwich_True_Late() {
        assertTrue(hasSandwich("cusaba"))
    }

    @Test
    fun hasSandwich_True_BreadSandwich() {
        assertTrue(hasSandwich("aaa"))
    }

    @Test
    fun hasSandwich_True_False() {
        assertFalse(hasSandwich("asdfasdfasdfasdfasdf"))
    }
}