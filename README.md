### Desafios em Kotlin - Estrutura de Dados - Curso do Santander Coders

**Lista de exercícios resolvidos em Kotlin para o curso do Santander Coders - Ada (Let's Code), envolvendo estrutura de dados:**

**Filhas e Pilas**

1. Temos um serviço de mensageria que ao receber uma nova mensagem a coloca numa fila e tenta enviar a mensagem ao destinatário, acontece que as vezes temos problemas ao tentar entregar a mensagem, por isso é necessário que tentemos entregar a mesma mensagem até 3 vezes. <br> Implementar uma fila dentro do MessageBroker onde seja possível retentar o envio das mensagens por pelo menos 3 vezes, caso a terceira tentativa seja uma falha, descartar a mensagem.

```
data class Message(
    val text: String,
    val recipient: String
)

class MessageBroker {
    private val messageSender = Random(1000)

    fun processFirstMessage() {
        /*
        * Implementar maneira de processar a fila e de retentar o envio de uma mensagem que deu erro
        */
        //send(message)
    }

    /*
    * Retorna true ao conseguir enviar uma mensagem com sucesso e false se não conseguir
    */
    private fun send(message: Message): Boolean {
        return messageSender.nextBoolean()
    }
}
```

2. Interpretar expressões matemáticas não é uma tarefa trivial, por isso é comum transformamos expressões da forma como conhecemos (3 + 4) em uma notação polonesa reversa, que nesse caso teria o formato: 3 4 +. Ao fazermos isso facilita criamos um algoritmo para calcular o resultado da expressão. <br> Para fazer tal conversão existem alguns algoritmos diferentes, um deles é chamado Shunting Yard, você deve implementar um conversor de notação utilizando o algoritmo de Shunting Yard simplificado, ou seja, ignorando a parte onde ele aceita funções no meio da expressão. Esse conversor deve utilizar a estrutura de dados de pilha.
<br>[Shunting Yard Algorithm](https://en.wikipedia.org/wiki/Shunting_yard_algorithm)
<br>[Reverse Polish notation](https://en.wikipedia.org/wiki/Reverse_Polish_notation)
<br>

**Listas**

1. Dado a estrutura de lista ligada que construímos em sala, adicionar:
<br> - Método de busca por um valor específico
<br> - Otimização para termos a adição de um nó ao final de O(1), ou seja, será necessário manter uma referência para o último elemento da lista

```
data class Node<T>(
    var value: T,
    var next: Node<T>?
)

data class LinkedList2<T>(
    var head: Node<T>? = null
) {
    fun add(value: T) {
        val newNode = Node(value = value, null)

        if (head == null) {
            head = newNode
            return
        } else {
            var current = head
            while (current?.next != null) {
                current = current.next
            }
            current?.next = newNode
        }
    }

    fun remove(value: T): Boolean {
        if (head?.value == value) {
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
            previous?.next = current.next
            return true
        }

        return false
    }

    fun show() {
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
```
