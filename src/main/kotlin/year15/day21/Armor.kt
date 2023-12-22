package year15.day21

enum class Armor(val cost: Int, val damage: Int, val armor: Int) {
    NONE(0, 0, 0),
    LEATHER(13, 0, 1),
    CHAIN_MAIL(31, 0, 2),
    SPLINT_MAIL(53, 0, 3),
    BANDED_MAIL(75, 0, 4),
    PLATE_MAIL(102, 0, 5)
}