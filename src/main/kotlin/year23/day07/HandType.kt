package year23.day07

enum class HandType(val rank: Int) {
    FIVE_OF_A_KIND(6),
    FOUR_OF_A_KIND(5),
    FULL_HOUSE(4),
    THREE_OF_A_KIND(3),
    TWO_PAIR(2),
    PAIR(1),
    HIGH_CARD(0),
}