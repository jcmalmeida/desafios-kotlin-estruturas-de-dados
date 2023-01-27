package Desafio1

import java.util.*
import Desafio1.Associativity.*
import Desafio1.Operator.*

enum class Associativity {
    RIGHT,
    LEFT
}

enum class Operator(val operator: Char, val precedence: Int, val associativity: Associativity) {
    POTENTIATION(operator = '^', precedence = 4, associativity = RIGHT),
    MULTIPLICATION(operator = 'x', precedence = 3, associativity = LEFT),
    DIVISION(operator = '/', precedence = 3, associativity = LEFT),
    SUM(operator = '+', precedence = 2, associativity = LEFT),
    SUBTRACTION(operator = '-', precedence = 2, associativity = LEFT),
    LEFT_PARENTHESIS(operator = '(', precedence = 0, associativity = LEFT),
    INVALID(operator = ' ', precedence = 0, associativity = LEFT)
}

class ShuntingYardAlgorithm() {
    private val outputQueue: Queue<Char> = LinkedList()
    private val operatorStack: Stack<Operator> = Stack()

    fun parseExpression(expression: String): String {
        expression.forEach {
            if (it.isDigit()) {
                outputQueue.offer(it)

            } else if (isOperator(it)) {
                val operatorO1 = whichOperator(it)
                var operatorO2: Operator?

                while (shouldPopOperatorO2(operatorO1)) {
                    operatorO2 = operatorStack.pop()
                    outputQueue.offer(operatorO2!!.operator)
                }
                operatorStack.push(operatorO1)

            } else if (it == '(') {
                operatorStack.push(LEFT_PARENTHESIS)

            } else if (it == ')') {
                while (shouldPopOperatorStack()) {
                    val operator = operatorStack.pop()
                    outputQueue.offer(operator.operator)
                }
                if (operatorStack.isNotEmpty()) {
                    if (operatorStack.peek().operator == '(')
                        operatorStack.pop()
                }
            }
        }
        while (operatorStack.isNotEmpty()) {
            val operator = operatorStack.pop()
            outputQueue.offer(operator.operator)
        }
        var outputString = ""
        outputQueue.forEach {
            outputString = outputString + it + " "
        }
        outputQueue.clear()

        return outputString
    }

    private fun isOperator(operator: Char): Boolean {
        return when (operator) {
            '^', 'x', '÷', '+', '-' -> true
            else -> false
        }
    }

    private fun whichOperator(operator: Char): Operator {
        return when (operator) {
            '^' -> POTENTIATION
            'x' -> MULTIPLICATION
            '÷' -> DIVISION
            '+' -> SUM
            '-' -> SUBTRACTION
            else -> INVALID
        }
    }

    private fun shouldPopOperatorO2(operatorO1: Operator): Boolean {
        var operatorO2: Operator?

        if (operatorStack.isNotEmpty()) {
            operatorO2 = operatorStack.peek()
            val operatorO2IsLeftParenthesis = (operatorO2 == LEFT_PARENTHESIS)
            val operatorO2HasGreaterPrecedence = (operatorO2.precedence > operatorO1.precedence)
            val operatorsHaveSamePrecedence = (operatorO1.precedence == operatorO2.precedence)

            return (!operatorO2IsLeftParenthesis && (operatorO2HasGreaterPrecedence ||
                    (operatorsHaveSamePrecedence && operatorO1.associativity == LEFT)))
        }

        return false
    }

    private fun shouldPopOperatorStack(): Boolean {
        var operator: Operator?

        if (operatorStack.isNotEmpty()) {
            operator = operatorStack.peek()
            return (operator != LEFT_PARENTHESIS)
        }

        return false
    }

}

fun main() {
    val shuntingYardAlgorithm = ShuntingYardAlgorithm()
    println(shuntingYardAlgorithm.parseExpression("3+4"))
    println("---")
    println(shuntingYardAlgorithm.parseExpression("3+4x(2-1)"))
    println("---")
    println(shuntingYardAlgorithm.parseExpression("3+4x2÷(1-5)^2^3"))
    println("---")
    println(shuntingYardAlgorithm.parseExpression("3-4x(2+4x(5-1))"))
}