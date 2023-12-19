package year23.day07

enum class GameRules(val cardPoints: Map<Char, Int>) {
    NORMAL(mapOf(
        '2' to 1,
        '3' to 2,
        '4' to 3,
        '5' to 4,
        '6' to 5,
        '7' to 6,
        '8' to 7,
        '9' to 8,
        'T' to 9,
        'J' to 10,
        'Q' to 11,
        'K' to 12,
        'A' to 13
    )),

    JACKS_WILD(mapOf(
        'J' to 0,
        '2' to 1,
        '3' to 2,
        '4' to 3,
        '5' to 4,
        '6' to 5,
        '7' to 6,
        '8' to 7,
        '9' to 8,
        'T' to 9,
        'Q' to 10,
        'K' to 11,
        'A' to 12
    ))
}