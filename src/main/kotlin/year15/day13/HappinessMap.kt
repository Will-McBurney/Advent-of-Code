package year15.day13

class HappinessMap {
    private val happinessMap: MutableMap<Pair<String, String>, Int> = mutableMapOf()

    fun add(source: String, nextTo: String, score: Int) {
        happinessMap[Pair(source, nextTo)] = score
    }

    fun get(source: String, nextTo: String): Int {
        return happinessMap[Pair(source, nextTo)]!!
    }

    fun getPeople(): List<String> = happinessMap.keys
        .map { it.first }
        .distinct()
        .toList()

    override fun toString(): String {
        return "HappinessMap(happinessMap=$happinessMap)"
    }

}