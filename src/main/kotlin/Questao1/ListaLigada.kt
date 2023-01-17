package Questao1

data class Node<T>(
    var value: T,
    var next: Node<T>?
)

data class LinkedList2<T>(
    var head: Node<T>? = null,
    var tail: Node<T>? = null
) {
    fun add(value: T) {
        val newNode = Node(value = value, null)

        if (head == null) {
            head = newNode
        } else {
            val previous = tail
            previous?.next = newNode
        }
        tail = newNode
    }

    fun remove(value: T): Boolean {
        if (head?.value == value) {
            if (head == tail)
                tail = head?.next
            head = head?.next
            return true
        }
        var current = head?.next
        var previous = head

        while(current != null && current.value != value) {
            previous = current
            current = current.next
        }

        if (current != null) {
            if (current == tail)
                tail = previous
            previous?.next = current.next
            return true
        }

        return false
    }

    fun search(value: T): Node<T>? {
        if (head?.value == value) {
            return head
        }
        var current = head?.next

        while(current != null && current.value != value) {
            current = current.next
        }

        if (current != null) {
            return current
        }

        return null
    }

    fun show() {
        println("Head: $head")
        println("Tail: $tail")

        var current = head
        if (current == null) {
            println("Lista vazia")
            return
        }

        while(current != null) {
            println(current.value)
            current = current.next
        }
    }

    fun isEmpty(): Boolean {
        return head == null
    }
}

fun main() {
    val linkedList = LinkedList2<String>()

    linkedList.add("String 1")
    linkedList.add("String 2")
    linkedList.add("String 3")
    linkedList.add("String 4")
    linkedList.add("String 5")
    linkedList.show()
    println(linkedList.isEmpty())

    println()

    println(linkedList.search("String 1"))
    println(linkedList.remove("String 1"))
    println(linkedList.search("String 1"))
    println(linkedList.search("String 5"))
    linkedList.show()
    println(linkedList.isEmpty())
}