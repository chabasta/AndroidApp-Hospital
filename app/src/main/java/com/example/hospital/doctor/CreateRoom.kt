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

class CreateRoom : AppCompatActivity() {

    lateinit var bottomNavigationView: BottomNavigationView

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_room)


        val roomNumber = findViewById<EditText>(R.id.create_room_number_value)
        val floor = findViewById<EditText>(R.id.create_room_floor_value)
        val description = findViewById<EditText>(R.id.create_room_description_value)
        val submit = findViewById<Button>(R.id.btn_create_new_patient)
        progressDialog = ProgressDialog(this)
        progressDialog.setCanceledOnTouchOutside(false)

        firebaseAuth = FirebaseAuth.getInstance()

        submit.setOnClickListener{
            val timestamp = System.currentTimeMillis()

            val hashMap: HashMap<String, Any?> = HashMap()
            hashMap["roomId"] = "$timestamp"
            hashMap["roomNumber"] = roomNumber.text.toString().trim()
            hashMap["floor"] = floor.text.toString().trim()
            hashMap["description"] = description.text.toString().trim()
            hashMap["state"] = "free"
            hashMap["pacientId"] = ""
            hashMap["fromDate"] = 0
            hashMap["toDate"] = 0
            hashMap["pacientName"] = ""

            val ref = FirebaseDatabase.getInstance().getReference("Rooms")
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
                    true
                }

            }
            false
        }
    }
}