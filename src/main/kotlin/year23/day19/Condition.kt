package year23.day19

enum class Condition {
    GREATER_THAN, LESS_THAN;

    fun test(value: Int, conditionTarget: Int): Boolean {
        return when(this) {
            GREATER_THAN -> value > conditionTarget
            LESS_THAN -> value < conditionTarget
        }
    }
}