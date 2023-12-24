package year23.day23

enum class MazeTile(val char: Char) {
    EMPTY('.'),
    WALL('#'),
    DOWN_SLOPE('v'),
    RIGHT_SLOPE('>');
}