package year15.day14

data class Reindeer(
    val name: String,
    val speedKms: Int,
    val movementTimeSeconds: Int,
    val restTimeSeconds: Int
) {
    val cycleTimeSeconds: Int = movementTimeSeconds + restTimeSeconds
    val cycleDistanceKms = speedKms * movementTimeSeconds
    var points = 0

    fun getDistanceTraveled(totalTimeSeconds: Int): Int {
        val cycles: Int = totalTimeSeconds / cycleTimeSeconds
        val remainingTime: Int  = totalTimeSeconds % cycleTimeSeconds
        val distanceSinceLastCycle: Int = if (remainingTime >= movementTimeSeconds)
            cycleDistanceKms else speedKms * remainingTime
        return cycles * cycleDistanceKms + distanceSinceLastCycle
    }

    fun incrementPoints() {
        points++
    }
}