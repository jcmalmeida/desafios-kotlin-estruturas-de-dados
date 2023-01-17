package Questao1

import java.util.*
import kotlin.random.Random

private const val numberOfAttempts = 3

data class Message(
    val text: String,
    val recipient: String
)

class MessageBroker {
    private val messageSender = Random(1000)
    private val messageQueue: Queue<Message> = LinkedList()

    fun addMessageToQueue(message: Message) {
        messageQueue.offer(message)
    }

    fun processFirstMessage() {
        /*
        * Implementar maneira de processar a fila e de retentar o envio de uma mensagem que deu erro
        */
        var message: Message?

        if (messageQueue.isNotEmpty()) {
            message = messageQueue.poll()
            println("Tentando enviar a seguinte mensagem: $message.")
        } else {
            println("Não há mensagens a serem enviadas.")
            return
        }

        if (message != null) {
            for (i in 1..numberOfAttempts) {
                val messageSentSuccessfully = send(message)
                if (messageSentSuccessfully) {
                    println("Mensagem enviada com sucesso!")
                    return
                } else {
                    println("Falha no envio da mensagem (tentativa $i).")
                }
            }
            println("A mensagem foi descartada.")
        }
    }

    /*
    * Retorna true ao conseguir enviar uma mensagem com sucesso e false se não conseguir
    */
    private fun send(message: Message): Boolean {
        return messageSender.nextBoolean()
    }
}

fun main() {
    val messageBroker = MessageBroker()

    messageBroker.addMessageToQueue(Message(text = "Text 1", recipient = "Recipient 1"))
    messageBroker.addMessageToQueue(Message(text = "Text 2", recipient = "Recipient 2"))
    messageBroker.addMessageToQueue(Message(text = "Text 3", recipient = "Recipient 3"))
    messageBroker.addMessageToQueue(Message(text = "Text 4", recipient = "Recipient 4"))
    messageBroker.addMessageToQueue(Message(text = "Text 5", recipient = "Recipient 5"))

    messageBroker.processFirstMessage()
    messageBroker.processFirstMessage()
    messageBroker.processFirstMessage()
    messageBroker.processFirstMessage()
    messageBroker.processFirstMessage()
    messageBroker.processFirstMessage()
}