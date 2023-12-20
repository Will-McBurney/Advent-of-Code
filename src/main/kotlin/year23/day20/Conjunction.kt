package year23.day20

class Conjunction(
    private val name: String,
    private val outputList: List<String>,
    private var tic: Int = 0
): Module {
    private val inputs: MutableMap<String, Pulse> = mutableMapOf()
    private var currentPulse: Pulse = Pulse.NONE
    private var nextPulse: Pulse = Pulse.NONE

    fun addInput(name: String) {
        inputs[name] = Pulse.NONE
    }
    override fun getName(): String = name

    override fun getOutputs(): List<String> = outputList

    override fun getTic(): Int = tic
    override fun setTic(tic: Int) {
        this.tic = tic
    }

    override fun incrementTic() {
        tic++
        currentPulse = nextPulse
        nextPulse = Pulse.NONE
    }

    override fun handlePulse(pulse: Pulse, source: String) {
        inputs[source] = pulse
        nextPulse = if (inputs.values.all{ p: Pulse -> p == Pulse.HIGH }) Pulse.LOW else Pulse.HIGH
    }

    override fun getNextPulse(): Pulse {
        val output = currentPulse
        currentPulse = Pulse.NONE
        return output
    }
    override fun isDone(): Boolean  = currentPulse == Pulse.NONE
}