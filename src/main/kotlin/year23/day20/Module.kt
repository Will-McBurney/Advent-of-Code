package year23.day20

interface Module {
    fun getName(): String
    fun getOutputs(): List<String>
    fun handlePulse(pulse: Pulse, source: String): Pulse
}

