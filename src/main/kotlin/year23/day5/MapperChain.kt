package year23.day5

class MapperChain(private val mappers: List<Mapper>) {
    fun getAllData(seedInput: Long): List<Long> {
        val dataList = mutableListOf(seedInput)
        mappers.forEach {
            dataList.add(it.getMapping(dataList.last()))
        }
        return dataList
    }

    enum class Datum(val value: Int) {
        SEED(0), SOIL(1), FERTILIZER(2), WATER(3),
        LIGHT(4), TEMPERATURE(5), HUMIDITY(6), LOCATION(7)
    }

    fun getData(seedInput: Long, datum: Datum): Long {
        val allData = getAllData(seedInput)
        return allData[datum.value]
    }

    override fun toString(): String {
        return "MapperChain(mappers=$mappers)"
    }

    fun getSeedFromLocation(value: Long): Long {
        var currentValue = value
        mappers.reversed()
            .forEach { currentValue = it.getReverseMapping(currentValue) }
        return currentValue
    }

}