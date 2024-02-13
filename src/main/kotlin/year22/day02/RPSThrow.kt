package year22.day02


const val LOSS_SCORE = 0
const val DRAW_SCORE = 3
const val WIN_SCORE = 6

enum class RPSThrow {
    ROCK,
    PAPER,
    SCISSORS;

    companion object {
        fun getRPSThrow(char: Char): RPSThrow {
            return when (char) {
                'A' -> ROCK
                'B' -> PAPER
                'C' -> SCISSORS
                else -> throw IllegalArgumentException()
            }
        }

        fun getPart1Throw(char: Char): RPSThrow {
            return when (char) {
                'X' -> ROCK
                'Y' -> PAPER
                'Z' -> SCISSORS
                else -> throw IllegalArgumentException()
            }
        }
    }

    private fun throwScore(): Int {
        return when(this) {
            ROCK -> 1
            PAPER -> 2
            SCISSORS -> 3
        }
    }
    private fun getOutcomeScore(opponentThrow: RPSThrow): Int {
        return when(this){
            ROCK -> when(opponentThrow) {
                ROCK -> DRAW_SCORE
                PAPER -> LOSS_SCORE
                SCISSORS -> WIN_SCORE
            }

            PAPER -> when(opponentThrow) {
                ROCK -> WIN_SCORE
                PAPER -> DRAW_SCORE
                SCISSORS -> LOSS_SCORE
            }
            SCISSORS -> when(opponentThrow) {
                ROCK -> LOSS_SCORE
                PAPER -> WIN_SCORE
                SCISSORS -> DRAW_SCORE
            }
        }
    }

    fun getTotalScore(opponentThrow: RPSThrow): Int = throwScore() + getOutcomeScore(opponentThrow)

    fun beatenBy(): RPSThrow = when(this) {
        ROCK -> PAPER
        PAPER -> SCISSORS
        SCISSORS -> ROCK
    }

    fun beats(): RPSThrow = when(this) {
        ROCK -> SCISSORS
        PAPER -> ROCK
        SCISSORS -> PAPER
    }
}