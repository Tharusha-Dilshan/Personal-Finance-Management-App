package com.example.personal_finance_management_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.personal_finance_management_app.databinding.ActivityDashHomeBinding

class DashHome : AppCompatActivity() {
    private lateinit var binding: ActivityDashHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDashHomeBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_dash_home)

    }
}