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

        // Restore the state of the app if exists a saved bundle
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
        btn0.setOnClickListener { onDigit("0") }
        btn1.setOnClickListener { onDigit("1") }
        btn2.setOnClickListener { onDigit("2") }
        btn3.setOnClickListener { onDigit("3") }
        btn4.setOnClickListener { onDigit("4") }
        btn5.setOnClickListener { onDigit("5") }
        btn6.setOnClickListener { onDigit("6") }
        btn7.setOnClickListener { onDigit("7") }
        btn8.setOnClickListener { onDigit("8") }
        btn9.setOnClickListener { onDigit("9") }
        btnAdd.setOnClickListener { onOperator("+") }
        btnSubtract.setOnClickListener { onOperator("-") }
        btnMultiply.setOnClickListener { onOperator("*") }
        btnDivide.setOnClickListener { onOperator("/") }
        btnEqual.setOnClickListener { onEqual() }
        btnSquareRoot.setOnClickListener { onSquareRoot() }
        btnExponentiation.setOnClickListener { onExponentiation() }
        btnClear.setOnClickListener { onClear() }
        btnBack.setOnClickListener { onBack() }
        btnDot.setOnClickListener { onDot() }
        btnStore.setOnClickListener { onStore() }
        btnRecall.setOnClickListener { onRecall() }
    }

    //Saving the state of app, when needed.
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putDoubleArray("operands", operands.toDoubleArray())
        outState.putStringArray("operators", operators.toTypedArray())
        outState.putBoolean("isNewOperand", isNewOperand)
        outState.putDouble("memoryValue", memoryValue)
        outState.putString("displayText", display.text.toString())
    }

    //Function to be called when user click on any Number button.
    private fun onDigit(digit: String) {
        if (isNewOperand) {
            display.text = digit
            isNewOperand = false
        } else {
            display.append(digit)
        }
    }

    //Function to be called when user click on any Operator button.
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

    //Function to be called when user click on Clear button.
    private fun onClear() {
        display.text = "0"
        operands.clear()
        operators.clear()
        isNewOperand = true
    }

    //Function to be called when user click on Equal button.
    private fun onEqual() {
        if (!isNewOperand && operators.isNotEmpty()) {
            val value = display.text.toString().toDouble()
            operands.add(value)
            //Test loop
            for(i in operands.indices){
                Log.d(TAG, "on equal get value ${operands[i]}")
                }
            performOperation()
        }
    }

    //Function to be called when app need to perform some calculus.
    private fun performOperation() {
        if (operators.isNotEmpty() && operands.size >= 2) {
            var result = operands[0]
            for (i in operators.indices) {
                Log.d(TAG, "${operands[i]} ${operators[i]} ${operands[i + 1]}")
                when (operators[i]) {
                    "+" -> result += operands[i + 1]
                    "-" -> result -= operands[i + 1]
                    "*" -> result *= operands[i + 1]
                    "/" -> result /= operands[i + 1]
                }
            }
            display.text = result.toString()
            operands.clear()
            operators.clear()
        } else {
            display.text = "ERROR"
        }
    }

    //Function to be called when user click on Back button.
    private fun onBack() {
        if (!isNewOperand && display.text.isNotEmpty() && display.text.length > 1) {
            display.text = display.text.dropLast(1)
        } else {
            display.text = "0"
            isNewOperand = true
        }
    }

    //Function to be called when user click on Dot button.
    private fun onDot() {
        if (isNewOperand) {
            display.text = "0."
            isNewOperand = false
        } else if (!display.text.contains('.')) {
            display.append(".")
        }
    }

    //Function to be called when user click on square root button.
    private fun onSquareRoot() {
        val value = display.text.toString().toDouble()
        display.text = sqrt(value).toString()
    }

    //Function to be called when user click on exponentiation button.
    private fun onExponentiation() {
        val value = display.text.toString().toDouble()
        display.text = (value.pow(2)).toString()
    }

    //Function to be called when user click on store button.
    private fun onStore() {
        memoryValue = display.text.toString().toDouble()
        isNewOperand = true
    }

    //Function to be called when user click on recall button.
    private fun onRecall() {
        val value = memoryValue
        if (isNewOperand) {
            display.text = value.toString()
            isNewOperand = false
        } else {
            display.append(value.toString())
        }
    }
}
