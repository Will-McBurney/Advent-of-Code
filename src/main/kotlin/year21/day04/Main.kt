package year21.day04

import AoCResultPrinter
import Reader
const val year: Int = 21
const val day: Int = 4

fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
    val numbers: List<Int> = lines[0].split(",").map(String::toInt)
    val bingoCards: List<BingoCard> = getBingoCards(lines.subList(2, lines.size))

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(bingoCards, numbers)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(bingoCards, numbers)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getBingoCards(subList: List<String>): List<BingoCard> {
    return subList.windowed(size = 5, step = 6)
        .map { lines -> BingoCard(lines) }
}

fun getPart1Result(bingoCards: List<BingoCard>, numbers: List<Int>): Int {
    numbers.forEach { nextNumber ->
        bingoCards.forEach { card -> card.handleNewValue(nextNumber) }

        val winningCards = bingoCards.filter { card -> card.contains(nextNumber) }
            .filter { card -> card.isWinner() }

        if (winningCards.isNotEmpty()) {
            return winningCards.single().getWinnerScore() * nextNumber
        }
    }
    throw IllegalArgumentException("No winners found")
}

fun getPart2Result(bingoCards: List<BingoCard>, numbers: List<Int>): Int {
    var remainingCards = bingoCards
    numbers.forEach { nextNumber ->
        remainingCards.forEach { card -> card.handleNewValue(nextNumber) }

        if (remainingCards.size == 1 && remainingCards.single().isWinner()) {
            return remainingCards.single().getWinnerScore() * nextNumber
        }

        remainingCards = remainingCards.filter { card ->
            !card.contains(nextNumber) || !card.isWinner()
        }
        assert(remainingCards.isNotEmpty())
    }

    throw IllegalStateException("No single worst card after numbers exhausted")
}
