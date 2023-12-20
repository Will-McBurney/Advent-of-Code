package year23.day20

class Button(
    private val outputList: List<String>,
    private var tic: Int = 0
): Module {
    override fun getName(): String = "broadcaster"
    override fun getOutputs(): List<String> = outputList

    override fun getTic(): Int = tic
    override fun setTic(tic: Int) {
        this.tic = tic
    }

    override fun incrementTic() { tic++ }

    override fun handlePulse(pulse: Pulse, source: String) {}

    override fun getNextPulse(): Pulse = if (tic == 0) Pulse.LOW else Pulse.NONE

    override fun isDone(): Boolean  = true
}