package year23.day19

import java.util.function.Predicate

class Workflow (
    val name: String,
    val rules: List<Predicate<Part>>,
    val destinations: List<String>,
    val lastDestination: String
) {
    override fun toString(): String {
        return "Workflow(name='$name', rules=$rules, destinations=$destinations, lastDestination=$lastDestination)"
    }
}