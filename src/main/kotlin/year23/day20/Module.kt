package year23.day20

interface Module {
    fun getName(): String
    fun getOutputs(): List<String>
    fun getTic(): Int
    fun setTic(tic: Int)
    fun incrementTic()
    fun handlePulse(pulse: Pulse, source: String)

    fun getNextPulse(): Pulse

    fun isDone(): Boolean
}

