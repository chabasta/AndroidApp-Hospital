package com.example.hospital.patient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.hospital.R
import com.example.hospital.databinding.ActivityPatientChatBinding
import com.example.hospital.databinding.ActivityPillsEditBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class PatientChat : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth

    private lateinit var binding: ActivityPatientChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityPatientChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var bundle :Bundle ?=intent.extras
        var roomNumber = bundle!!.getString("roomNumber")

        firebaseAuth = FirebaseAuth.getInstance()

        var input = findViewById<EditText>(R.id.chat_message_input)

        var btn_send = findViewById<Button>(R.id.chat_message_btn)

        btn_send.setOnClickListener {
            val uid = firebaseAuth.uid

            val timestamp = System.currentTimeMillis()

            val hashMap: HashMap<String, Any?> = HashMap()
            hashMap["pacientId"] = uid
            hashMap["date"] = timestamp
            hashMap["id"] = "$timestamp"
            hashMap["roomNumber"] = roomNumber
            hashMap["text"] = input.text.toString()

            val ref = FirebaseDatabase.getInstance().getReference("Messages")
            ref.child("$timestamp")
                .setValue(hashMap)
                .addOnSuccessListener {
                    println("Message was send")
                    finish()
                }
                .addOnFailureListener() { e ->
                    println("Message send failed due to ${e.message}")
                }
        }



    }
}
