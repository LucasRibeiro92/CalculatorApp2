package com.example.calculatorapp2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calculatorapp2.model.CalculatorModel

class CalculatorViewModel : ViewModel() {

    val model = CalculatorModel()

    private val _display = MutableLiveData<String>()
    val display: LiveData<String>
        get() = _display

    init {
        updateDisplay(model.displayValue)
    }

    fun updateDisplay(value: String) {
        _display.postValue(value)
    }

    fun onDigit(digit: String) {
        if (model.isNewOperand) {
            model.isNewOperand = false
            model.displayValue = digit
        } else {
            model.displayValue += digit
        }
        updateDisplay(model.displayValue)
    }

    fun onOperator(operator: String) {
        if (!model.isNewOperand) {
            val value = model.displayValue.toDouble()
            model.operands.add(value)
        }
        model.operators.add(operator)
        model.isNewOperand = true
    }

    fun onClear() {
        model.clear()
        updateDisplay(model.displayValue)
    }

    fun onEqual() {
        if (!model.isNewOperand && model.operators.isNotEmpty()) {
            val value = model.displayValue.toDouble()
            model.operands.add(value)
            model.performOperation()
            updateDisplay(model.displayValue)
        }
    }

    fun onBack() {
        if (!model.isNewOperand && model.displayValue.isNotEmpty() && model.displayValue.length > 1) {
            model.isNewOperand = true
        } else {
            model.clear()
        }
        updateDisplay(model.displayValue)
    }

    fun onDot() {
        if (model.isNewOperand) {
            model.isNewOperand = false
            model.displayValue = "0."
        } else if (!model.displayValue.contains('.')) {
            model.displayValue += "."
        }
        updateDisplay(model.displayValue)
    }

    fun onSquareRoot() {
        val value = model.displayValue.toDouble()
        val result = model.calculateSquareRoot(value)
        model.displayValue = result.toString()
        updateDisplay(model.displayValue)
    }

    fun onExponentiation() {
        val value = model.displayValue.toDouble()
        val result = model.calculateExponentiation(value)
        model.displayValue = result.toString()
        updateDisplay(model.displayValue)
    }

    fun onStore() {
        model.storeValue(model.displayValue.toDouble())
    }

    fun onRecall() {
        model.displayValue = model.recallValue().toString()
        updateDisplay(model.displayValue)
    }
}
