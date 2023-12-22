package year15.day21

data class Character(
    val maxHealth: Int,
    val damage: Int,
    val armor: Int,
    val cost: Int
) {
    fun canBeat(enemy: Character): Boolean {
        val playerDamagePerAttack: Int = (this.damage - enemy.armor).coerceAtLeast(1)
        val enemyDamagePerAttack: Int = (enemy.damage - this.armor).coerceAtLeast(1)
        return this.maxHealth / enemyDamagePerAttack > enemy.maxHealth / playerDamagePerAttack
    }
}
