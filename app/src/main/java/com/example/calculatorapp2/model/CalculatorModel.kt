package com.example.calculatorapp2.model

import kotlin.math.pow
import kotlin.math.sqrt

class CalculatorModel {
    var displayValue: String = "0"
    var operands: MutableList<Double> = mutableListOf()
    var operators: MutableList<String> = mutableListOf()
    var isNewOperand: Boolean = true
    var memoryValue: Double = 0.0

    fun performOperation() {
        while (operators.size > 0 && operands.size >= 2) {
            val op = operators.removeAt(0) // Remove o próximo operador da lista
            val operand1 = operands.removeAt(0) // Remove o primeiro operando da lista
            val operand2 = operands.removeAt(0) // Remove o segundo operando da lista
            when (op) {
                "+" -> operands.add(operand1 + operand2) // Adiciona a soma dos operandos
                "-" -> operands.add(operand1 - operand2) // Adiciona a subtração dos operandos
                "*" -> operands.add(operand1 * operand2) // Adiciona a multiplicação dos operandos
                "/" -> operands.add(operand1 / operand2) // Adiciona a divisão dos operandos
                "x^2" -> operands.add(operand1.pow(2.0)) // Adiciona o quadrado do primeiro operando
            }
        }
        operators.clear() // Limpa a lista de operadores
        isNewOperand = true // Define isNewOperand como verdadeiro
        displayValue = operands.getOrNull(0)?.toString() ?: "ERROR" // Define o valor do display para o primeiro operando ou "ERROR" se não houver operando
        operands.clear() // Limpa a lista de operandos
    }

    fun clear() {
        displayValue = "0"
        operands.clear()
        operators.clear()
        isNewOperand = true
    }

    fun calculateSquareRoot(value: Double): Double {
        return sqrt(value)
    }

    fun calculateExponentiation(value: Double): Double {
        return value.pow(2.0)
    }

    fun storeValue(value: Double) {
        memoryValue = value
    }

    fun recallValue(): Double {
        return memoryValue
    }
}
