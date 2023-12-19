package year15.day07

class WireMap() {
    private val wireMap: MutableMap<String, UShort> = mutableMapOf()

    fun contains(wireName: String): Boolean = wireMap.containsKey(wireName)

    fun addWireValue(wireName: String, value: UShort) {
        if (!contains(wireName)) wireMap[wireName] = value
    }

    fun getWireValue(wireName: String): UShort? = wireMap[wireName]
    override fun toString(): String {
        return "WireMap(wireMap=$wireMap)"
    }


}