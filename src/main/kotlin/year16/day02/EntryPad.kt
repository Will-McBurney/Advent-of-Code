package year16.day02

interface EntryPad {
    fun getNextEntry(directions: List<Direction>): Char
}