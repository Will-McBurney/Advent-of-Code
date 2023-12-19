package year23.day19

class Workflow (
    private val name: String,
    val rules: List<Rule>,
    val destinations: List<String>,
    val lastDestination: String
) {
    override fun toString(): String {
        return "Workflow(name='$name', \n" +
                "\trules=$rules, \n" +
                "\tdestinations=$destinations, \n" +
                "\tlastDestination='$lastDestination')"
    }
    fun getDestination(part: Part): String {
        rules.forEachIndexed { index, rule ->
            if (rule.isMetBy(part)) { return destinations[index] }
        }
        return lastDestination
    }
}