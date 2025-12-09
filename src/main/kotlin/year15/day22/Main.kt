package year15.day22

import AoCResultPrinter
import Reader
import java.util.PriorityQueue

const val year: Int = 15
const val day: Int = 22

enum class Effect(
    val duration: Int,
    val armorIncrease: Int = 0,
    val damage: Int = 0,
    val manaIncrease: Int = 0
) {
    SHIELDED(6, armorIncrease = 7),
    POISONED(6, damage = 3 ),
    RECHARGING(5, manaIncrease = 101);
}

enum class Moves(
    val manaCost: Int,
    val damage: Int = 0,
    val heal: Int = 0,
    val effect: Effect? = null
){
    MAGIC_MISSILE(manaCost = 53, damage = 4),
    DRAIN(manaCost = 73, damage = 2, heal = 2),
    SHIELD(manaCost = 113, effect = Effect.SHIELDED),
    POISON(manaCost = 173, effect = Effect.POISONED),
    RECHARGE(manaCost = 229, effect = Effect.RECHARGING)
}

data class Node(
    val playerHp: Int,
    val playerMana: Int,
    val bossHp: Int,
    val shieldedRemaining: Int = 0,
    val poisonedRemaining: Int = 0,
    val rechargingRemaining: Int = 0,
    val playerTurn: Boolean = true,
)

fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val startingPlayerHp = 50
    val startingPlayerMana = 500
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
    val startingBossHp = lines[0].substringAfter("Hit Points: ").toInt()
    val bossDamage = lines[1].substringAfter("Damage: ").toInt()

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(startingPlayerHp, startingPlayerMana, startingBossHp, bossDamage, hardMode = false)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart1Result(startingPlayerHp, startingPlayerMana, startingBossHp, bossDamage, hardMode = true)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getPart1Result(
    startingPlayerHp: Int,
    startingPlayerMana: Int,
    startingBossHp: Int,
    bossDamage: Int,
    hardMode: Boolean
): Int {
    val startingNode = Node(
        playerHp = startingPlayerHp,
        playerMana = startingPlayerMana,
        bossHp = startingBossHp,
    )
    val bestCost = mutableMapOf(startingNode to 0)
    val bestPath = mutableMapOf(startingNode to listOf<Moves>())
    val queue = PriorityQueue<Node>(compareBy { bestCost[it] })
    queue.add(startingNode)
    while (!queue.isEmpty()) {
        val node = queue.poll()
        //println("${bestCost[node]} $node")

        var playerHp = node.playerHp

        //apply hard mode penalty on player turn only
        if (node.playerTurn && hardMode) {
            playerHp -= 1
            if (playerHp <= 0) continue
        }

        var bossHp = node.bossHp
        // apply poison damage
        var poisonedRemaining = node.poisonedRemaining
        if (poisonedRemaining > 0) {
            poisonedRemaining -= 1
            bossHp -= Effect.POISONED.damage
        }

        // check if the boss is dead
        if (bossHp <= 0) {
            val path = bestPath[node]!!
            //path.forEach { println(it) }
            return bestCost[node]!!
        }

        // apply mana recharge
        var playerMana = node.playerMana
        var rechargingRemaining = node.rechargingRemaining
        if (rechargingRemaining > 0) {
            playerMana += Effect.RECHARGING.manaIncrease
            rechargingRemaining -= 1

        }
        var shieldedRemaining = node.shieldedRemaining
        var playerArmor = 0
        if (shieldedRemaining > 0) {
            playerArmor += Effect.SHIELDED.armorIncrease
            shieldedRemaining -= 1
        }

        if (!node.playerTurn) { // boss turn
            playerHp -= (bossDamage - playerArmor).coerceAtLeast(1)
            if (playerHp <= 0) continue // player dead - no edge nodes

            val nextNode = node.copy( //otherwise proceed to player turn
                playerHp = playerHp,
                playerMana = playerMana,
                bossHp = bossHp,
                playerTurn = true,
                shieldedRemaining = shieldedRemaining,
                poisonedRemaining = poisonedRemaining,
                rechargingRemaining = rechargingRemaining,
            )
            //Add if new node, or is a better cost than our old node
            if (bestCost[nextNode] == null || bestCost[node]!! < bestCost[nextNode]!! ) {
                bestCost[nextNode] = bestCost[node]!!
                bestPath[nextNode] = bestPath[node]!!
                queue.add(nextNode)
            }
        } else { //player turn
            val playerOptions = Moves.entries.filter { playerMana >= it.manaCost }.toMutableList()
            if (shieldedRemaining > 0) { playerOptions.remove(Moves.SHIELD) }
            if (poisonedRemaining > 0) { playerOptions.remove(Moves.POISON) }
            if (rechargingRemaining > 0) { playerOptions.remove(Moves.RECHARGE) }
            if (playerOptions.isEmpty()) { continue } // DEAD
            playerOptions.forEach { choice ->
                val nextNode = node.copy(
                    playerHp = playerHp + choice.heal,
                    playerMana = playerMana - choice.manaCost,
                    bossHp = bossHp - choice.damage,
                    playerTurn = false,
                    shieldedRemaining = shieldedRemaining +
                            if (choice.effect == Effect.SHIELDED) choice.effect.duration
                            else 0,
                    poisonedRemaining = poisonedRemaining +
                            if (choice.effect == Effect.POISONED) choice.effect.duration
                            else 0,
                    rechargingRemaining = rechargingRemaining +
                            if (choice.effect == Effect.RECHARGING) choice.effect.duration
                            else 0
                )
                val totalManaSpent = bestCost[node]!! + choice.manaCost
                if (bestCost[nextNode] == null || totalManaSpent < bestCost[nextNode]!! ) {
                    bestCost[nextNode] = totalManaSpent
                    bestPath[nextNode] = bestPath[node]!! + choice
                    queue.add(nextNode)
                }
            }
        }
    }
    throw IllegalStateException("Unwinnable")
}

fun getPart2Result(): Int {
    return 0
}
