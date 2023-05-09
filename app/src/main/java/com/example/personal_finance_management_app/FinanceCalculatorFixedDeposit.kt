package com.example.personal_finance_management_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class FinanceCalculatorFixedDeposit : AppCompatActivity() {

    private lateinit var principalEditText: EditText
    private lateinit var interestRateEditText: EditText
    private lateinit var tenureEditText: EditText
    private lateinit var calculateButton: Button
    private lateinit var resetButton: Button
    private lateinit var resultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finance_calculator_fixed_deposite)

        principalEditText = findViewById(R.id.principalEditText)
        interestRateEditText = findViewById(R.id.interestRateEditText)
        tenureEditText = findViewById(R.id.tenureEditText)
        calculateButton = findViewById(R.id.calculateButton)
        resetButton = findViewById(R.id.resetButton)
        resultTextView = findViewById(R.id.resultTextView)

        calculateButton.setOnClickListener {
            val principal = principalEditText.text.toString().toDoubleOrNull() ?: 0.0
            val interestRate = interestRateEditText.text.toString().toDoubleOrNull() ?: 0.0
            val tenure = tenureEditText.text.toString().toIntOrNull() ?: 0

            val maturityAmount = calculateMaturityAmount(principal, interestRate, tenure)
            val interestEarned = maturityAmount - principal
            resultTextView.text = "Maturity Amount: RS ${"%.2f".format(maturityAmount)}\nInterest Earned: RS ${"%.2f".format(interestEarned)}"
        }

        resetButton.setOnClickListener{
            principalEditText.text.clear()
            interestRateEditText.text.clear()
            tenureEditText.text.clear()
            resultTextView.text = ""
        }
    }

    private fun calculateMaturityAmount(principal: Double, interestRate: Double, tenure: Int): Double {
        val interest = principal * interestRate * tenure / 100
        return principal + interest
    }
}
