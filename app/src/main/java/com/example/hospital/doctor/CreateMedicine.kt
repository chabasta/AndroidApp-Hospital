package com.example.hospital.doctor

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.hospital.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class CreateMedicine : AppCompatActivity() {

    lateinit var bottomNavigationView: BottomNavigationView

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_medicine)

        var name = findViewById<EditText>(R.id.create_medicine_name_input)
        var about = findViewById<EditText>(R.id.create_medicine_about_input)
        var httk = findViewById<EditText>(R.id.create_medicine_httk_input)
        var submit = findViewById<Button>(R.id.btn_doctor_create_new_medicine)

        progressDialog = ProgressDialog(this)
        progressDialog.setCanceledOnTouchOutside(false)

        submit.setOnClickListener {
            val timestamp = System.currentTimeMillis()

            val hashMap: HashMap<String, Any?> = HashMap()
            hashMap["id"] = "$timestamp"
            hashMap["about"] = about.text.toString().trim()
            hashMap["instruction"] = httk.text.toString().trim()
            hashMap["name"] = name.text.toString().trim()

            val ref = FirebaseDatabase.getInstance().getReference("Medicines")
            ref.child("$timestamp")
                .setValue(hashMap)
                .addOnSuccessListener {
                    println("Success")
                    finish()
                }
                .addOnFailureListener() { e ->
                    println("Registration failed due to ${e.message}")
                }

        }

        initBottomNavBar()
    }

    fun initBottomNavBar(){
        bottomNavigationView = findViewById(R.id.bottom_nav_doctor)

        bottomNavigationView.apply { this.selectedItemId = R.id.pacients_doctor }

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.dashboard_doctor -> {
                    val intent = Intent(applicationContext, DashboardDoctorActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0,0)
                    true
                }
                R.id.rooms_doctor -> {
                    val intent = Intent(applicationContext, RoomsDoctorActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0,0)
                    true
                }
                R.id.medicines_doctor -> {
                    val intent = Intent(applicationContext, MedicinesDoctorActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0,0)
                    true
                }
                R.id.pacients_doctor -> {
                    val intent = Intent(applicationContext, PacientsDoctorActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0,0)
                }

            }
            false
        }
    }
}
