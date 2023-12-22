package year15.day21

import AoCResultPrinter

fun main() {
    val printer = AoCResultPrinter(2015, 21)

    //Setup
    val playerHp = 100
    val enemy = Character(109, 8, 2, -1)
    getEquipmentCombinations(playerHp)
    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(playerHp, enemy)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(playerHp, enemy)
    printer.endPart2()

    printer.printResults(part1Result, part2Result)
}

fun elapsedMicroSeconds(start: Long, end: Long): Long = (end - start) / 1000

val equipmentCombosMemo: MutableMap<Int, List<Character>> = mutableMapOf()
fun getEquipmentCombinations(playerHp: Int): List<Character> {
    if (!equipmentCombosMemo.containsKey(playerHp)) {
        equipmentCombosMemo[playerHp] = Weapon.entries.map { weapon ->
            Armor.entries.map { armor ->
                getRingPermutations().map { rings ->
                    Character(
                        playerHp,
                        weapon.damage + rings.sumOf { it.damage },
                        armor.armor + rings.sumOf { it.armor },
                        weapon.cost + armor.cost + rings.sumOf { it.cost }
                    )
                }
            }.flatten()
        }.flatten()
    }
    return equipmentCombosMemo[playerHp]!!
}

fun getRingPermutations(): List<List<Ring>> {
    val ringPermutations: MutableList<List<Ring>> = mutableListOf(emptyList()) //no ring
    Ring.entries.forEach { ring -> ringPermutations.add(listOf(ring)) } //single ring
    Ring.entries.map {ring1 ->
        Ring.entries.filter { ring2 -> ring2.ordinal > ring1.ordinal }
            .map{ring2 -> listOf(ring1, ring2)}
    }.flatten().forEach { combo -> ringPermutations.add(combo) }
    return ringPermutations
}
fun getPart1Result(playerHp: Int, enemy: Character): Int {
    return getEquipmentCombinations(playerHp)
        .filter { player: Character  -> player.canBeat(enemy) }
        .minOf { player: Character  -> player.cost }
}



fun getPart2Result(playerHp: Int, enemy: Character): Int {
    return getEquipmentCombinations(playerHp)
        .filter { player: Character -> !player.canBeat(enemy) }
        .maxOf { player: Character  -> player.cost }
}
