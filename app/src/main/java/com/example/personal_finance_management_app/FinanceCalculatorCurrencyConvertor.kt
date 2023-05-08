package com.example.personal_finance_management_app


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.*
import okhttp3.*
import okio.IOException
import org.json.JSONObject
import java.text.DecimalFormat




class FinanceCalculatorCurrencyConvertor : AppCompatActivity() {
    private var fromCurrency: String = "USD"
    private var toCurrency: String = "USD"
    private var exchangeRate: Double = 1.0
    private lateinit var button_convert : Button
    private lateinit var editText_amount : EditText
    private lateinit var textViewFromResult : TextView
    private lateinit var textViewToResult : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finance_calculator_currency_convertor)

        // Set up the spinner for "From Currency"
        val spinnerFrom = findViewById<Spinner>(R.id.spinner_from)
        val spinnerTo = findViewById<Spinner>(R.id.spinner_to)
        button_convert = findViewById(R.id.button_convert)
        editText_amount = findViewById(R.id.editText_amount)
        textViewFromResult = findViewById(R.id.textViewFromResult)
        textViewToResult = findViewById(R.id.textViewToResult)



// Set up the spinner for "From Currency"
        spinnerFrom.adapter = ArrayAdapter.createFromResource(
            this,
            R.array.currencies,
            android.R.layout.simple_spinner_item
        )


        spinnerFrom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                fromCurrency = parent?.getItemAtPosition(position).toString()
                updateExchangeRate()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Set up the spinner for "To Currency"
        spinnerTo.adapter = ArrayAdapter.createFromResource(
            this,
            R.array.currencies,
            android.R.layout.simple_spinner_item
        )

        spinnerTo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                toCurrency = parent?.getItemAtPosition(position).toString()
                updateExchangeRate()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }



        // Set up the button to convert the amount
        button_convert.setOnClickListener {
            val amount = editText_amount.text.toString().toDoubleOrNull() ?: 0.0
            val result = amount * exchangeRate
            val formatter = DecimalFormat("#,###.##")
            textViewFromResult.text = "${formatter.format(amount)} $fromCurrency"
            textViewToResult.text = "${formatter.format(result)} $toCurrency"
        }
    }

    private fun updateExchangeRate() {
        val from = fromCurrency.take(3)
        val to = toCurrency.take(3)
        val url = "https://api.exchangeratesapi.io/latest?base=$from&symbols=$to"

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val json = response.body?.string()
                val jsonObject = JSONObject(json)
                val rates = jsonObject.optJSONObject("rates")
                if (rates != null) {
                    exchangeRate = rates.optDouble(to, 1.0)
                } else {
                    exchangeRate = 1.0
                }
            }

        })
    }
}


