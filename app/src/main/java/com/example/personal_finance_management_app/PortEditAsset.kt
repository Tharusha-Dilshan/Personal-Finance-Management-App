package com.example.personal_finance_management_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.personal_finance_management_app.databinding.ActivityPortAddAssetBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class PortEditAsset : AppCompatActivity() {
    private lateinit var binding:ActivityPortAddAssetBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPortAddAssetBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}