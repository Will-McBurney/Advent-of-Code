package year23.day20

class FlipFlop(
    private val name: String,
    val outputList: List<String>,
    private var tic: Int = 0
): Module {
    private var isOn: Boolean = false
    private var currentPulse: Pulse = Pulse.NONE
    private var nextPulse: Pulse = Pulse.NONE
    override fun getName() = name
    override fun getOutputs(): List<String> = outputList

    override fun getTic() = tic
    override fun setTic(tic: Int) {
        this.tic = tic
    }

    override fun incrementTic() {
        tic++
        currentPulse = nextPulse
        nextPulse = Pulse.NONE
    }

    override fun handlePulse(pulse: Pulse, source: String) {
        when(pulse) {
            Pulse.LOW -> {
                nextPulse = if(isOn) Pulse.LOW else Pulse.HIGH
                isOn = !isOn
            }
            else -> return
        }
    }

    override fun getNextPulse(): Pulse {
        val output = currentPulse
        currentPulse = Pulse.NONE
        return output
    }
    override fun isDone(): Boolean  = currentPulse == Pulse.NONE
}