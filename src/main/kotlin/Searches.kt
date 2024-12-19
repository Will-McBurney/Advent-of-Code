import java.util.Stack

interface SearchNode<E> {
    fun getNode(): E
    fun getNext(): List<SearchNode<E>>
}

interface SearchResult<E> {
    fun updateResult(node: SearchNode<*>)
}

fun<E> dfs(
    start: Set<SearchNode<*>>,
    initialResult: SearchResult<E>,
    visitOnlyOnce: Boolean = true,
    endSearch: (SearchNode<*>) -> Boolean = { false },
): SearchResult<E> {
    val stack = Stack<SearchNode<*>>()
    val visited = HashSet<SearchNode<*>>(start)
    var result = initialResult
    start.forEach { stack.push(it) }
    while (stack.isNotEmpty()) {
        val node = stack.pop()
        result.updateResult(node)
        if (endSearch(node)) { break }
        node.getNext().filterNot { visitOnlyOnce && visited.contains(it) }
            .forEach {
                visited.add(it)
                stack.push(it)
            }
    }
    return result
}