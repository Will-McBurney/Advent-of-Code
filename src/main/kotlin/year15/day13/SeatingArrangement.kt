package year15.day13

class SeatingArrangement(
    private val seatingOrder: List<String>,
    private val happinessMap: HappinessMap
) {
    fun getNetHappiness(): Int {
        return seatingOrder
            .mapIndexed{index, it -> listOf(
                happinessMap.get(it, seatingOrder[(index + 1) % seatingOrder.size]),
                happinessMap.get(it, seatingOrder[(index + seatingOrder.size - 1) % seatingOrder.size]))
            }
            .flatten()
            .sum()
    }
}