package com.example.calculatorapp2.model

import android.util.Log
import kotlin.math.pow
import kotlin.math.sqrt

class CalculatorModel {
    var displayValue: String = "0"
    var operands: MutableList<Double> = mutableListOf()
    var operators: MutableList<String> = mutableListOf()
    var isNewOperand: Boolean = true
    var memoryValue: Double = 0.0

    //Function to execute most part of operations.
    fun performOperation() {
        while (operators.size > 0 && operands.size >= 2) {
            val op = operators.removeAt(0) // Remove next operator from array
            val operand1 = operands.removeAt(0) // Remove the first operand from array
            val operand2 = operands.removeAt(0) // Remove the second operand from array
            when (op) {
                "+" -> operands.add(operand1 + operand2) // Add the sum of operands to array
                "-" -> operands.add(operand1 - operand2) // Add the subtraction of operands to array
                "*" -> operands.add(operand1 * operand2) // Add the multiplication of operands to array
                "/" -> operands.add(operand1 / operand2) // Add the division of operands to array
            }
        }
        operators.clear() // Clean operators array
        displayValue = operands.getOrNull(0)?.toString() ?: "ERROR" // Defines the display value or set to ERROR.
        operands.clear() // Clean the operands array
    }

    //Function to clear the display and variables
    fun clear() {
        displayValue = "0"
        operands.clear()
        operators.clear()
        isNewOperand = true
    }

    //Other functions to deal with square root, exponentiation, storage and recall.
    fun calculateSquareRoot(value: Double): Double { return sqrt(value) }
    fun calculateExponentiation(value: Double): Double { return value.pow(2.0) }
    fun storeValue(value: Double) { memoryValue = value }
    fun recallValue(): Double { return memoryValue }
}
