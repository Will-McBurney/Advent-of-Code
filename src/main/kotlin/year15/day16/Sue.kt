package year15.day16

class Sue(
    val id: Int
) {
    private val stuffCounts: MutableMap<String, Int> = mutableMapOf()

    fun addStuffCount(stuffCategory: String, count: Int) {
        stuffCounts[stuffCategory] = count
    }

    fun getStuffCount(stuffCategory: String): Int? {
        return stuffCounts[stuffCategory]
    }

    override fun toString(): String {
        return "Sue(id=$id, stuffCounts=$stuffCounts)"
    }


}