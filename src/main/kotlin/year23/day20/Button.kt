package year23.day20

class Button(
    private val outputList: List<String>,
    private var tic: Int = 0
): Module {
    override fun getName(): String = "broadcaster"
    override fun getOutputs(): List<String> = outputList

    override fun handlePulse(pulse: Pulse, source: String): Pulse {
        if (pulse == Pulse.LOW && source == "button") {
            return Pulse.LOW
        }
        return Pulse.NONE
    }
}