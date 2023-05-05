package com.example.personal_finance_management_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView


class LoanCalculator : AppCompatActivity() {

    private lateinit var loanAmountEditText: EditText
    private lateinit var interestRateEditText: EditText
    private lateinit var loanTermEditText: EditText
    private lateinit var calculateButton: Button
    private lateinit var resultTextView: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loan_calculator)

        loanAmountEditText = findViewById(R.id.loanAmountEditText)
        interestRateEditText = findViewById(R.id.interestRateEditText)
        loanTermEditText = findViewById(R.id.loanTermEditText)
        calculateButton = findViewById(R.id.calculateButton)
        resultTextView = findViewById(R.id.resultTextView)

        calculateButton.setOnClickListener {
            val loanAmount = loanAmountEditText.text.toString().toDoubleOrNull() ?: 0.0
            val interestRate = interestRateEditText.text.toString().toDoubleOrNull() ?: 0.0
            val loanTerm = loanTermEditText.text.toString().toIntOrNull() ?: 0

            val monthlyPayment = calculateMonthlyPayment(loanAmount, interestRate, loanTerm)
            resultTextView.text = "Monthly Payment: RS ${"%.2f".format(monthlyPayment)}"
        }
    }

    private fun calculateMonthlyPayment(loanAmount: Double, interestRate: Double, loanTerm: Int): Double {
        val monthlyInterestRate = interestRate / 1200.0
        val totalPayments = loanTerm * 12
        val power = Math.pow(1 + monthlyInterestRate, totalPayments.toDouble())
        return loanAmount * monthlyInterestRate * power / (power - 1)
    }


    }
