package com.example.calculatorapp2.activity

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.calculatorapp2.databinding.ActivityMainBinding
import com.example.calculatorapp2.viewmodel.CalculatorViewModel

class MainActivity : AppCompatActivity() {

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

    private lateinit var viewModel: CalculatorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBindings()
        setupListeners()

        viewModel = ViewModelProvider(this).get(CalculatorViewModel::class.java)

        // Observe display value change
        viewModel.display.observe(this, Observer {
            display.text = it
        })

        savedInstanceState?.let {
            viewModel.model.operands = it.getDoubleArray("operands")?.toMutableList() ?: mutableListOf()
            viewModel.model.operators = it.getStringArray("operators")?.toMutableList() ?: mutableListOf()
            viewModel.model.isNewOperand = it.getBoolean("isNewOperand", true)
            viewModel.model.memoryValue = it.getDouble("memoryValue", 0.0)
            viewModel.updateDisplay(it.getString("displayText", "0"))
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putDoubleArray("operands", viewModel.model.operands.toDoubleArray())
        outState.putStringArray("operators", viewModel.model.operators.toTypedArray())
        outState.putBoolean("isNewOperand", viewModel.model.isNewOperand)
        outState.putDouble("memoryValue", viewModel.model.memoryValue)
        outState.putString("displayText", display.text.toString())
    }

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

    private fun setupListeners(){
        btn0.setOnClickListener { viewModel.onDigit("0") }
        btn1.setOnClickListener { viewModel.onDigit("1") }
        btn2.setOnClickListener { viewModel.onDigit("2") }
        btn3.setOnClickListener { viewModel.onDigit("3") }
        btn4.setOnClickListener { viewModel.onDigit("4") }
        btn5.setOnClickListener { viewModel.onDigit("5") }
        btn6.setOnClickListener { viewModel.onDigit("6") }
        btn7.setOnClickListener { viewModel.onDigit("7") }
        btn8.setOnClickListener { viewModel.onDigit("8") }
        btn9.setOnClickListener { viewModel.onDigit("9") }
        btnAdd.setOnClickListener { viewModel.onOperator("+") }
        btnSubtract.setOnClickListener { viewModel.onOperator("-") }
        btnMultiply.setOnClickListener { viewModel.onOperator("*") }
        btnDivide.setOnClickListener { viewModel.onOperator("/") }
        btnEqual.setOnClickListener { viewModel.onEqual() }
        btnSquareRoot.setOnClickListener { viewModel.onSquareRoot() }
        btnExponentiation.setOnClickListener { viewModel.onExponentiation() }
        btnClear.setOnClickListener { viewModel.onClear() }
        btnBack.setOnClickListener { viewModel.onBack() }
        btnDot.setOnClickListener { viewModel.onDot() }
        btnStore.setOnClickListener { viewModel.onStore() }
        btnRecall.setOnClickListener { viewModel.onRecall() }
    }
}
