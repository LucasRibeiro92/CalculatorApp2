package com.example.calculatorapp2

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.calculatorapp2.databinding.ActivityMainBinding
import kotlin.math.pow
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    //Declaring Bindings
    private lateinit var binding: ActivityMainBinding
    private lateinit var display: TextView
    private lateinit var btnClear: Button
    private lateinit var btnBack: Button
    private lateinit var btnDivide: Button
    private lateinit var btn7: Button
    private lateinit var btn8: Button
    private lateinit var btn9: Button
    private lateinit var btnMultiply: Button
    private lateinit var btn4: Button
    private lateinit var btn5: Button
    private lateinit var btn6: Button
    private lateinit var btnSubtract: Button
    private lateinit var btn1: Button
    private lateinit var btn2: Button
    private lateinit var btn3: Button
    private lateinit var btnAdd: Button
    private lateinit var btn0: Button
    private lateinit var btnDot: Button
    private lateinit var btnEqual: Button
    private lateinit var btnSquareRoot: Button
    private lateinit var btnExponentiation: Button
    private lateinit var btnStore: Button
    private lateinit var btnRecall: Button

    //Declaring General Variables
    private var operands: MutableList<Double> = mutableListOf()
    private var operators: MutableList<String> = mutableListOf()
    private var isNewOperand: Boolean = true
    private var memoryValue: Double = 0.0
    private var TAG = "CHECK_RESULT"

    //Overriding the the OnCreate from Super
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBindings()
        setupListeners()

        // Restaurar o estado da aplicação se houver um Bundle salvo
        savedInstanceState?.let {
            operands = it.getDoubleArray("operands")?.toMutableList() ?: mutableListOf()
            operators = it.getStringArray("operators")?.toMutableList() ?: mutableListOf()
            isNewOperand = it.getBoolean("isNewOperand", true)
            memoryValue = it.getDouble("memoryValue", 0.0)
            display.text = it.getString("displayText", "0")
        }
    }

    //Function to setup all the needed bindings.
    private fun setupBindings(){
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        display = binding.tvDisplay
        btnClear = binding.btnClear
        btnBack = binding.btnBack
        btnDivide = binding.btnDivide
        btn7 = binding.btn7
        btn8 = binding.btn8
        btn9 = binding.btn9
        btnMultiply = binding.btnMultiply
        btn4 = binding.btn4
        btn5 = binding.btn5
        btn6 = binding.btn6
        btnSubtract = binding.btnSubtract
        btn1 = binding.btn1
        btn2 = binding.btn2
        btn3 = binding.btn3
        btnAdd = binding.btnAdd
        btn0 = binding.btn0
        btnDot = binding.btnDot
        btnEqual = binding.btnEqual
        btnSquareRoot = binding.btnSquareRoot
        btnExponentiation = binding.btnExponentiation
        btnStore = binding.btnStore
        btnRecall = binding.btnRecall
    }

    //Function to setup all the needed buttons' listeners.
    private fun setupListeners(){
        btnClear.setOnClickListener { onClear() }
        btnBack.setOnClickListener { onBack() }
        btnDivide.setOnClickListener { onOperator("/") }
        btn7.setOnClickListener { onDigit("7") }
        btn8.setOnClickListener { onDigit("8") }
        btn9.setOnClickListener { onDigit("9") }
        btnMultiply.setOnClickListener { onOperator("*") }
        btn4.setOnClickListener { onDigit("4") }
        btn5.setOnClickListener { onDigit("5") }
        btn6.setOnClickListener { onDigit("6") }
        btnSubtract.setOnClickListener { onOperator("-") }
        btn1.setOnClickListener { onDigit("1") }
        btn2.setOnClickListener { onDigit("2") }
        btn3.setOnClickListener { onDigit("3") }
        btnAdd.setOnClickListener { onOperator("+") }
        btn0.setOnClickListener { onDigit("0") }
        btnDot.setOnClickListener { onDot() }
        btnEqual.setOnClickListener { onEqual() }
        btnSquareRoot.setOnClickListener { onSquareRoot() }
        btnExponentiation.setOnClickListener { onExponentiation() }
        btnStore.setOnClickListener { onStore() }
        btnRecall.setOnClickListener { onRecall() }
    }

    // Salva o estado da aplicação quando necessário
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putDoubleArray("operands", operands.toDoubleArray())
        outState.putStringArray("operators", operators.toTypedArray())
        outState.putBoolean("isNewOperand", isNewOperand)
        outState.putDouble("memoryValue", memoryValue)
        outState.putString("displayText", display.text.toString())
    }

    private fun onDigit(digit: String) {
        if (isNewOperand) {
            display.text = digit
            isNewOperand = false
        } else {
            display.append(digit)
        }
    }

    private fun onOperator(operator: String) {
        if (!isNewOperand) {
            val value = display.text.toString().toDouble()
            operands.add(value)
            if (operator == "=") {
                performOperation()
            }
        }

        operators.add(operator)
        isNewOperand = true
    }

    private fun onClear() {
        display.text = "0"
        operands.clear()
        operators.clear()
        isNewOperand = true
    }

    private fun onEqual() {
        if (!isNewOperand && operators.isNotEmpty()) {
            val value = display.text.toString().toDouble()
            operands.add(value)
            performOperation()
            isNewOperand = true
            operands.clear()
            operators.clear()
        }
    }

    private fun performOperation() {
        if (operators.isNotEmpty() && operands.size >= 2) {
            var result = operands[0]
            for (i in operators.indices) {
                when (operators[i]) {
                    "+" -> result += operands[i + 1]
                    "-" -> result -= operands[i + 1]
                    "*" -> result *= operands[i + 1]
                    "/" -> result /= operands[i + 1]
                }
            }
            display.text = result.toString()
            operands.clear()
            operands.add(result)
            operators.clear()
        } else {
            display.text = "53105"
        }
    }

    private fun onBack() {
        if (!isNewOperand && display.text.isNotEmpty() && display.text.length > 1) {
            display.text = display.text.dropLast(1)
        } else {
            display.text = "0"
            isNewOperand = true
        }
    }

    private fun onDot() {
        if (isNewOperand) {
            display.text = "0."
            isNewOperand = false
        } else if (!display.text.contains('.')) {
            display.append(".")
        }
    }

    private fun onSquareRoot() {
        val value = display.text.toString().toDouble()
        display.text = sqrt(value).toString()
    }

    private fun onExponentiation() {
        val value = display.text.toString().toDouble()
        display.text = (value.pow(2)).toString()
    }

    private fun onStore() {
        memoryValue = display.text.toString().toDouble()
        isNewOperand = true
    }

    private fun onRecall() {
        display.text = memoryValue.toString()
        isNewOperand = true
    }
}
