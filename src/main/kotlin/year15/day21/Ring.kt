package year15.day21

enum class Ring(val cost: Int, val damage: Int, val armor: Int) {
    DAMAGE_1(25, 1, 0),
    DAMAGE_2(50, 2, 0),
    DAMAGE_3(100, 3, 0),
    DEFENSE_1(20, 0, 1),
    DEFENSE_2(40, 0, 2),
    DEFENSE_3(80, 0, 3);
}