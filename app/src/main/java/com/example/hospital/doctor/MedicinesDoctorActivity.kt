package com.example.hospital.doctor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.hospital.R
import com.example.hospital.databinding.ActivityCameraBinding.inflate
import com.example.hospital.databinding.ActivityMedicinesDoctorBinding
import com.example.hospital.databinding.ActivityRoomsDoctorBinding
import com.example.hospital.doctor.adapters.MedicinesCardRecyclerViewAdapter
import com.example.hospital.doctor.adapters.RoomCardRecyclerAdapter
import com.example.hospital.model.MedicineModel
import com.example.hospital.model.PatientModel
import com.example.hospital.model.RoomModel
import com.example.hospital.patient.adapters.MedicineCardRecyclerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MedicinesDoctorActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: ActivityMedicinesDoctorBinding
    lateinit var bottomNavigationView: BottomNavigationView

    private lateinit var medicinesArrayList: ArrayList<MedicineModel>

    private lateinit var medicineAdapter: MedicinesCardRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicines_doctor)

        binding = ActivityMedicinesDoctorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        loadMedicines()


        var btn = findViewById<Button>(R.id.btn_doctor_create_new_medicine)

        btn.setOnClickListener {
            val intent = Intent(applicationContext, CreateMedicine::class.java)
            startActivity(intent)
            overridePendingTransition(0,0)
        }






        initBottomNavBar()
    }

    fun loadMedicines(){
        var instance = FirebaseDatabase.getInstance()
        var medRef = instance.getReference("Medicines")

        medicinesArrayList = ArrayList()
        medRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                medicinesArrayList.clear()
                for (medicineSnapshot in snapshot.children) {
                    val medicineModel = medicineSnapshot.getValue(MedicineModel::class.java)!!
                    medicinesArrayList.add(medicineModel)
                }
                medicineAdapter = MedicinesCardRecyclerViewAdapter(this@MedicinesDoctorActivity, medicinesArrayList)
                binding.recyclerAllMedicinesView.adapter = medicineAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
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
