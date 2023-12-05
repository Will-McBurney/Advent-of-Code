package year23.day5

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

import year23.day5.MapperChain.Datum

class MapperChainTest {

    lateinit var mappers: List<Mapper>
    lateinit var mapperChain: MapperChain
    @BeforeEach
    fun setup() {
        mappers = listOf(
            Mapper(
                listOf(
                    Triple(50L, 98L, 2L),
                    Triple(52L, 50L, 48L)
                )
            ),
            Mapper(
                listOf(
                    Triple(0, 15, 37),
                    Triple(37, 52, 2),
                    Triple(39, 0, 15)
                )
            ),
            Mapper(
                listOf(
                    Triple(49, 53, 8),
                    Triple(0, 11, 42),
                    Triple(42, 0, 7),
                    Triple(57, 7, 4)
                )
            )
        )
        mapperChain = MapperChain(mappers)
    }

    @Test
    fun getAllData_1() {
        val expected = listOf(79L, 81L, 81L, 81L)
        assertEquals(expected, mapperChain.getAllData(79))
    }

    @Test
    fun getAllData_2() {
        val expected = listOf(14L, 14L, 53L, 49L)
        assertEquals(expected, mapperChain.getAllData(14))
    }

    @Test
    fun getAllData_3() {
        val expected = listOf(55L, 57L, 57L, 53L)
        assertEquals(expected, mapperChain.getAllData(55))
    }

    @Test
    fun getData_SEED() {
        assertEquals(79L, mapperChain.getData(79L, Datum.SEED))
    }

    @Test
    fun getData_SOIL() {
        assertEquals(81L, mapperChain.getData(79L, Datum.SOIL))
    }

    @Test
    fun getData_FERTILIZER() {
        assertEquals(53L, mapperChain.getData(14L, Datum.FERTILIZER))
    }
}