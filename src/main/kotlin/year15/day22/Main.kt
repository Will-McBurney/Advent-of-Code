package year15.day22

import AoCResultPrinter
import Reader
import java.util.PriorityQueue

const val year: Int = 15
const val day: Int = 22

enum class Turn {
    PLAYER,
    BOSS
}

data class Node(
    /** Player's remaining hp (uncapped) */
    val playerHp: Int,
    /** Player's remaining mana (uncapped) */
    val playerMana: Int,
    /** Boss's remaining hp */
    val bossHp: Int,
    /** How many remaining phases [Effect.SHIELDED] is active. If 0, then [Effect.SHIELDED] is inactive*/
    val shieldedRemaining: Int = 0,
    /** How many remaining phases [Effect.POISONED] is active. If 0, then [Effect.POISONED] is inactive*/
    val poisonedRemaining: Int = 0,
    /** How many remaining phases [Effect.RECHARGING] is active. If 0, then [Effect.RECHARGING] is inactive*/
    val rechargingRemaining: Int = 0,
    /** Whose [Turn] is it */
    val turn: Turn = Turn.PLAYER,
)

/** Spells a player can cast on their turn */
enum class Spells(
    /** The mana cost of the move */
    val manaCost: Int,
    /** The amount of **immediate** damage caused by the move */
    val damage: Int = 0,
    /** The amount of **immediate** healing caused by the move */
    val heal: Int = 0,
    /** The effect started by the move */
    val effect: Effect? = null,
    /** Condition that must be true to be allowed to cast, not counting mana cost*/
    val castCondition: (Node) -> Boolean = { _ -> true }
){
    /** standard damage spell */
    MAGIC_MISSILE(manaCost = 53, damage = 4),
    /** drain health from the boss */
    DRAIN(manaCost = 73, damage = 2, heal = 2),
    /** increase armor for several turn */
    SHIELD(manaCost = 113, effect = Effect.SHIELDED, castCondition = { node -> node.shieldedRemaining <= 1 }),
    /** deal damage over time to the boss for several turns */
    POISON(manaCost = 173, effect = Effect.POISONED, castCondition = { node -> node.poisonedRemaining <= 1 }),
    /** regenerate mana over the next several turns */
    RECHARGE(manaCost = 229, effect = Effect.RECHARGING, castCondition = { node -> node.rechargingRemaining <= 1 }),
}

/**
 * List of effects created by spells
 */
enum class Effect(
    /** how many turns the effect lasts upon casting */
    val initialDuration: Int,
    /** how much the effect increases player armor while the effect is active */
    val armorIncrease: Int = 0,
    /** how much damage the spell does to the boss each turn while the effect is active */
    val damage: Int = 0,
    /** how much mana the player gains each turn while the effect is active */
    val manaIncrease: Int = 0
) {
    /** The player has increased defenses */
    SHIELDED(6, armorIncrease = 7),
    /** The boss is taking damage each turn */
    POISONED(6, damage = 3 ),
    /** The player is regenerating mana */
    RECHARGING(5, manaIncrease = 101);
}

/** The game's difficult */
enum class Difficulty {
    /** Normal game difficulty */
    NORMAL,
    /** The player takes 1 additional point of damage on their turn */
    HARD
}

fun main() {
    val printer = AoCResultPrinter(year, day)

    // Get initial game state
    val startingPlayerHp = 50
    val startingPlayerMana = 500
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
    val startingBossHp = lines[0].substringAfter("Hit Points: ").toInt()
    val bossDamage = lines[1].substringAfter("Damage: ").toInt()

    printer.endSetup()


    //Do Part 1
    val part1Result = getLeastManaSpent(
        startingPlayerHp,
        startingPlayerMana,
        startingBossHp,
        bossDamage,
        Difficulty.NORMAL
    )
    printer.endPart1()

    //Do Part 2
    val part2Result = getLeastManaSpent(
        startingPlayerHp,
        startingPlayerMana,
        startingBossHp,
        bossDamage,
        Difficulty.HARD
    )
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getLeastManaSpent(
    startingPlayerHp: Int,
    startingPlayerMana: Int,
    startingBossHp: Int,
    bossDamage: Int,
    difficulty: Difficulty
): Int {
    val startingNode = Node(
        playerHp = startingPlayerHp,
        playerMana = startingPlayerMana,
        bossHp = startingBossHp,
    )

    // Cost table for Dijkstra's algorithm
    val bestCost = mutableMapOf(startingNode to 0)

    // Priority queue sorted by best code
    val queue = PriorityQueue<Node>(compareBy { bestCost[it] })
    queue.add(startingNode)

    // Searching
    while (!queue.isEmpty()) {
        val node = queue.poll()

        var playerHp = node.playerHp
        var playerMana = node.playerMana
        var bossHp = node.bossHp
        var poisonedRemaining = node.poisonedRemaining
        var rechargingRemaining = node.rechargingRemaining
        var shieldedRemaining = node.shieldedRemaining
        val turn = node.turn
        val manaSpent = bestCost[node]!!

        //apply hard mode penalty on player turn only
        if (turn == Turn.PLAYER && difficulty == Difficulty.HARD) {
            playerHp -= 1
            if (playerHp <= 0) continue // Player dead, kill branch
        }

        // apply poison damage
        if (poisonedRemaining > 0) {
            poisonedRemaining -= 1
            bossHp -= Effect.POISONED.damage
        }

        // check if the boss is dead - if it is, congrats, we have the least mana spent to kill boss!
        if (bossHp <= 0) {
            return bestCost[node]!!
        }

        // apply mana recharge
        if (rechargingRemaining > 0) {
            playerMana += Effect.RECHARGING.manaIncrease
            rechargingRemaining -= 1
        }

        // apply armor
        var playerArmor = 0
        if (shieldedRemaining > 0) {
            playerArmor += Effect.SHIELDED.armorIncrease
            shieldedRemaining -= 1
        }

        // handle boss turn
        when (turn) {
            Turn.BOSS -> {
                // boss damages player
                playerHp -= (bossDamage - playerArmor).coerceAtLeast(1)

                // if player is dead, this is a dead branch
                if (playerHp <= 0) continue

                // proceed to player turn
                val nextNode = Node(
                    playerHp = playerHp,
                    playerMana = playerMana,
                    bossHp = bossHp,
                    turn = Turn.PLAYER,
                    shieldedRemaining = shieldedRemaining,
                    poisonedRemaining = poisonedRemaining,
                    rechargingRemaining = rechargingRemaining,
                )
                //Add if new node, or is a better cost than our old node
                if (bestCost[nextNode] == null || manaSpent < bestCost[nextNode]!!) {
                    bestCost[nextNode] = bestCost[node]!!
                    queue.add(nextNode)
                }
            }

            Turn.PLAYER -> { // handle player turn
                // get the valid spell options
                val playerOptions = Spells.entries.filter {
                    playerMana >= it.manaCost && it.castCondition(node)
                }

                if (playerOptions.isEmpty()) { continue } // GAME OVER - cannot cast any moves

                //for each spell, simulate the next phase, and create the new node
                playerOptions.forEach { choice ->
                    val nextNode = Node(
                        playerHp = playerHp + choice.heal,
                        playerMana = playerMana - choice.manaCost,
                        bossHp = bossHp - choice.damage,
                        turn = Turn.BOSS,
                        shieldedRemaining = shieldedRemaining +
                                if (choice.effect == Effect.SHIELDED) choice.effect.initialDuration
                                else 0,
                        poisonedRemaining = poisonedRemaining +
                                if (choice.effect == Effect.POISONED) choice.effect.initialDuration
                                else 0,
                        rechargingRemaining = rechargingRemaining +
                                if (choice.effect == Effect.RECHARGING) choice.effect.initialDuration
                                else 0
                    )
                    val newManaSpent = manaSpent + choice.manaCost

                    // if this is a "never before seen" node, OR it is the best cost for this node
                    if (bestCost[nextNode] == null || newManaSpent < bestCost[nextNode]!!) {
                        bestCost[nextNode] = newManaSpent
                        queue.add(nextNode)
                    }
                }
            }
        }
    }
    throw IllegalStateException("Unwinnable")
}
