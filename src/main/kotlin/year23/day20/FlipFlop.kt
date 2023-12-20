package year23.day20

class FlipFlop(
    private val name: String,
    private val outputList: List<String>
): Module {
    private var isOn: Boolean = false
    override fun getName() = name
    override fun getOutputs(): List<String> = outputList

    override fun handlePulse(pulse: Pulse, source: String): Pulse {
        if (pulse == Pulse.LOW) {
            isOn = !isOn
            return if(isOn) Pulse.HIGH else Pulse.LOW
        }
        return Pulse.NONE
    }
}