package com.example.personal_finance_management_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.personal_finance_management_app.databinding.ActivityDashHomeBinding
import com.example.personal_finance_management_app.databinding.ActivityPortAddAssetBinding

class PortAddAsset : AppCompatActivity() {
    private lateinit var binding: ActivityPortAddAssetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPortAddAssetBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}