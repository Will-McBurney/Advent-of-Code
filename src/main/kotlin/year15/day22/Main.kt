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
    val playerHp: Int,
    val playerMana: Int,
    val bossHp: Int,
    val shieldedRemaining: Int = 0,
    val poisonedRemaining: Int = 0,
    val rechargingRemaining: Int = 0,
    val turn: Turn = Turn.PLAYER,
)

enum class Moves(
    val manaCost: Int,
    val damage: Int = 0,
    val heal: Int = 0,
    val effect: Effect? = null,

    /** Condition that must be true to be allowed to cast, not counting mana cost*/
    val castCondition: (Node) -> Boolean = { _ -> true }
){
    MAGIC_MISSILE(manaCost = 53, damage = 4),
    DRAIN(manaCost = 73, damage = 2, heal = 2),
    SHIELD(manaCost = 113, effect = Effect.SHIELDED, castCondition = { node -> node.shieldedRemaining <= 1 }),
    POISON(manaCost = 173, effect = Effect.POISONED, castCondition = { node -> node.poisonedRemaining <= 1 }),
    RECHARGE(manaCost = 229, effect = Effect.RECHARGING, castCondition = { node -> node.rechargingRemaining <= 1 }),
}

enum class Effect(
    val initialDuration: Int,
    val armorIncrease: Int = 0,
    val damage: Int = 0,
    val manaIncrease: Int = 0
) {
    SHIELDED(6, armorIncrease = 7),
    POISONED(6, damage = 3 ),
    RECHARGING(5, manaIncrease = 101);
}

enum class GameMode {
    EASY,
    HARD
}

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
    val part1Result = getLeastManaSpent(startingPlayerHp, startingPlayerMana, startingBossHp, bossDamage, gameMode = GameMode.EASY)
    printer.endPart1()

    //Do Part 2
    val part2Result = getLeastManaSpent(startingPlayerHp, startingPlayerMana, startingBossHp, bossDamage, gameMode = GameMode.HARD)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getLeastManaSpent(
    startingPlayerHp: Int,
    startingPlayerMana: Int,
    startingBossHp: Int,
    bossDamage: Int,
    gameMode: GameMode
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
        //println("${bestCost[node]} $node")

        var playerHp = node.playerHp
        var playerMana = node.playerMana
        var bossHp = node.bossHp
        var poisonedRemaining = node.poisonedRemaining
        var rechargingRemaining = node.rechargingRemaining
        var shieldedRemaining = node.shieldedRemaining
        val turn = node.turn

        //apply hard mode penalty on player turn only
        if (turn == Turn.PLAYER && gameMode == GameMode.HARD) {
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

                // create new node
                val nextNode = node.copy(
                    //otherwise proceed to player turn
                    playerHp = playerHp,
                    playerMana = playerMana,
                    bossHp = bossHp,
                    turn = Turn.PLAYER,
                    shieldedRemaining = shieldedRemaining,
                    poisonedRemaining = poisonedRemaining,
                    rechargingRemaining = rechargingRemaining,
                )
                //Add if new node, or is a better cost than our old node
                if (bestCost[nextNode] == null || bestCost[node]!! < bestCost[nextNode]!!) {
                    bestCost[nextNode] = bestCost[node]!!
                    queue.add(nextNode)
                }
            }

            Turn.PLAYER -> { // handle player turn
                // get the valid spell options
                val playerOptions = Moves.entries.filter {
                    playerMana >= it.manaCost && it.castCondition(node)
                }

                if (playerOptions.isEmpty()) {
                    continue
                } // DEAD - cannot cast any moves

                //for each spell, simulate the next phase, and create the new node
                playerOptions.forEach { choice ->
                    val nextNode = node.copy(
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
                    val totalManaSpent = bestCost[node]!! + choice.manaCost

                    // if this is a "never before seen" node, OR it is the best cost for this node
                    if (bestCost[nextNode] == null || totalManaSpent < bestCost[nextNode]!!) {
                        bestCost[nextNode] = totalManaSpent
                        queue.add(nextNode)
                    }
                }
            }
        }
    }
    throw IllegalStateException("Unwinnable")
}
