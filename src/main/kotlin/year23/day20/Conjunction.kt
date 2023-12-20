package year23.day20

class Conjunction(
    private val name: String,
    private val outputList: List<String>
): Module {
    private val inputs: MutableMap<String, Pulse> = mutableMapOf()

    fun addInput(name: String) {
        inputs[name] = Pulse.NONE
    }

    override fun getName(): String = name

    override fun getOutputs(): List<String> = outputList

    override fun handlePulse(pulse: Pulse, source: String): Pulse {
        inputs[source] = pulse
        return if (inputs.values.all{ p: Pulse -> p == Pulse.HIGH }) Pulse.LOW else Pulse.HIGH
    }
}