package com.example.personal_finance_management_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.personal_finance_management_app.Adapters.SugAdapter
import com.example.personal_finance_management_app.DataClasses.SuggestionModel
import com.example.personal_finance_management_app.databinding.ActivitySuggeListItemsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SuggeListItems : AppCompatActivity() {

    private lateinit var binding: ActivitySuggeListItemsBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid:String
    private var mList = ArrayList<SuggestionModel>()
    private lateinit var adapter: SugAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuggeListItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize variables
       /* auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference
            .child("FinanceSuggestions").child(uid)

        binding.btnDelete.setOnClickListener{
            deleteRecord(
                intent.getStringExtra("sugId").toString()
            )
        }
        binding.btnUpdate.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("sugId").toString()
            )
        } */
    }
    /*
    private fun deleteRecord(
        id: String
    ){
        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference
            .child("FinanceSuggestions").child(uid)
        val mTask = databaseRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Suggestion deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, FetchingSuggestions::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }
*/

}