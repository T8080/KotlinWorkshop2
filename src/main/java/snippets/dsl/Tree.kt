package snippets.dsl

data class Node(
    val name: String,
    val children: MutableList<Node> = mutableListOf()
)

fun tree(name: String, config: Node.() -> Unit): Node {
    val person = Node(name)
    person.config()
    return person
}

fun Node.node(name: String, config: Node.() -> Unit = {}) {
    val person = Node(name)
    person.config()
    children.add(person)
}

fun main() {
    val root = tree("root") {
        node("a") {
            node("aa")
        }

        node("b") {
            node("ba")
            node("bb") {
                for (x in "abcdef") {
                    node("bb$x")
                }
            }
        }
    }
}