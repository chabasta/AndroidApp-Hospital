package com.example.hospital.doctor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.hospital.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.FirebaseDatabase




class MedicineInfo : AppCompatActivity() {

    lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicine_info)

        val info = getIntent().getExtras()?.getString("id")

        val name = findViewById<TextView>(R.id.medicine_info_name_title)
        val about = findViewById<TextView>(R.id.medicine_info_name_input)
        val httk = findViewById<TextView>(R.id.medicine_info_httk)
        val sideEf = findViewById<TextView>(R.id.medicine_info_sideef_info)

        var instance = FirebaseDatabase.getInstance()
        var medRef = instance.getReference("Medicines")
        medRef.child("$info").get().addOnSuccessListener {
            name.text = it.child("name").getValue(String::class.java)
            about.text = it.child("about").getValue(String::class.java)
            httk.text = it.child("instruction").getValue(String::class.java)
            name.text = it.child("name").getValue(String::class.java)
        }

        initBottomNavBar()
    }


    fun initBottomNavBar(){
        bottomNavigationView = findViewById(R.id.bottom_nav_doctor)

        bottomNavigationView.apply { this.selectedItemId = R.id.medicines_doctor }

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
                    true
                }

            }
            false
        }
    }
}

