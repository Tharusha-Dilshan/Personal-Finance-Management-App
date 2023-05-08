package com.example.personal_finance_management_app

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.personal_finance_management_app.databinding.ActivityFinanceBinding


private lateinit var binding:ActivityFinanceBinding
class FinanceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinanceBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.sugimg.setOnClickListener{
            intent = Intent(applicationContext, FinanceSuggestionsSubmit::class.java)
            startActivity(intent)
        }
        binding.rateimg.setOnClickListener{
            val url = "https://www.cbsl.gov.lk/rates-and-indicators/exchange-rates"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
        binding.loancalimg.setOnClickListener{
            intent = Intent(applicationContext, FinanceCalculatorLoan::class.java)
            startActivity(intent)
        }

        binding.excalimg.setOnClickListener{
            intent = Intent(applicationContext, FinanceCalculatorCurrencyConvertor::class.java)
            startActivity(intent)
        }
        binding.fdcalimg.setOnClickListener{
            intent = Intent(applicationContext, FinanceCalculatorLoan::class.java)
            startActivity(intent)
        }
        binding.prefimg.setOnClickListener{
            intent = Intent(applicationContext, FinancePreferencesFetchingActivity::class.java)
            startActivity(intent)
        }


    }
}