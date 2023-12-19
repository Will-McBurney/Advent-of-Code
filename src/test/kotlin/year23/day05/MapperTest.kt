package year23.day05

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class MapperTest {

    @Test
    fun getSourceRangeTest() {
        val mapper = Mapper(emptyList())
        val actualRange = mapper.getSourceRange(Triple(50L, 98L, 2L))
        assertEquals(98L..<100L, actualRange)
    }

    @Test
    fun getDestinationRangeTest() {
        val mapper = Mapper(emptyList())
        val actualRange = mapper.getDestinationRange(Triple(50L, 98L, 2L))
        assertEquals(50L..<52L, actualRange)
    }

    @Test
    fun isInRangeTest() {
        val mapper = Mapper(emptyList())
        assertFalse(mapper.isInSourceRange(97L, Triple(50L, 98L, 2L)))
        assertTrue(mapper.isInSourceRange(98L, Triple(50L, 98L, 2L)))
        assertTrue(mapper.isInSourceRange(99L, Triple(50L, 98L, 2L)))
        assertFalse(mapper.isInSourceRange(100L, Triple(50L, 98L, 2L)))
    }

    @Test
    fun getMappingInRangeTest() {
        val mapper = Mapper(emptyList())
        assertEquals(50L, mapper.getMappingInRange(98L, Triple(50L, 98L, 2L)))
        assertEquals(51L, mapper.getMappingInRange(99L, Triple(50L, 98L, 2L)))
        assertThrows(IllegalArgumentException::class.java) {
            mapper.getMappingInRange(100L, Triple(50L, 98L, 2L))
        }
    }

    @Test
    fun getReverseMappingTest() {
        val mapper = Mapper(listOf(
            Triple(50L, 98L, 2L),
            Triple(52L, 50L, 48L)
        ))
        assertEquals(0L, mapper.getMapping(0L))
        assertEquals(49L, mapper.getMapping(49L))
        assertEquals(52L, mapper.getMapping(50L))
        assertEquals(99L, mapper.getMapping(97L))
        assertEquals(50L, mapper.getMapping(98L))
        assertEquals(100L, mapper.getMapping(100L))
    }

    @Test
    fun getMappingTest() {
        val mapper = Mapper(listOf(
            Triple(50L, 98L, 2L),
            Triple(52L, 50L, 48L)
        ))
        assertEquals(0L, mapper.getReverseMapping(0L))
        assertEquals(49L, mapper.getReverseMapping(49L))
        assertEquals(50L, mapper.getReverseMapping(52L))
        assertEquals(97L, mapper.getReverseMapping(99L))
        assertEquals(98L, mapper.getReverseMapping(50L))
        assertEquals(100L, mapper.getReverseMapping(100L))
    }

}