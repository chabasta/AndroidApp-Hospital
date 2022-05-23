package com.example.hospital.doctor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hospital.R
import com.example.hospital.databinding.ActivityPatientDayPillsBinding
import com.example.hospital.databinding.ActivityPillsEditBinding
import com.example.hospital.doctor.adapters.DayPillsAdapter
import com.example.hospital.doctor.adapters.PatientDaysAdapter
import com.example.hospital.doctor.adapters.PillFilterAdapter
import com.example.hospital.model.DayModel
import com.example.hospital.model.MedicineModel
import com.example.hospital.model.PillModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PillsEdit : AppCompatActivity() {

    private lateinit var binding: ActivityPillsEditBinding
    private lateinit var firebaseAuth: FirebaseAuth
    lateinit var bottomNavigationView: BottomNavigationView

    private lateinit var medicines : ArrayList<MedicineModel>
    private lateinit var dayMedicines : ArrayList<PillModel>

    private lateinit var pillFilterAdapter: PillFilterAdapter
    private lateinit var dayPillsAdapter: DayPillsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPillsEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var bundle :Bundle ?=intent.extras
        var dayUid = bundle!!.getString("dayUid")

        firebaseAuth = FirebaseAuth.getInstance()

        loadMedicines(dayUid!!)

        loadDaysPills(dayUid!!)

        initBottomNavBar()
    }

    private fun loadDaysPills(dayUid: String) {
        dayMedicines = ArrayList()
        val ref = FirebaseDatabase.getInstance().getReference("Pills").orderByChild("dayId").equalTo(dayUid)
        ref.addValueEventListener(  object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dayMedicines.clear()
                for (ds in snapshot.children){
                    val model = ds.getValue(PillModel::class.java)!!
                    dayMedicines.add(model)
                }
                dayPillsAdapter = DayPillsAdapter(this@PillsEdit, dayMedicines)
                binding.recyclerMedicinesViewPillsEdit.adapter = dayPillsAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun loadMedicines(dayUid: String) {
        medicines = ArrayList()

        val ref = FirebaseDatabase.getInstance().getReference("Medicines")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                medicines.clear()
                for (ds in snapshot.children){
                    val model = ds.getValue(MedicineModel::class.java)!!
                    medicines.add(model)
                }
                pillFilterAdapter = PillFilterAdapter(this@PillsEdit, medicines, dayUid)
                binding.recyclerMedicinesViewPillsEditExamples.adapter = pillFilterAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun initBottomNavBar() {
        bottomNavigationView = findViewById(R.id.bottom_nav_doctor)

        bottomNavigationView.apply { this.selectedItemId = R.id.rooms_doctor }

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.dashboard_doctor -> {
                    val intent = Intent(applicationContext, DashboardDoctorActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    true
                }
                R.id.rooms_doctor -> {
                    val intent = Intent(applicationContext, RoomsDoctorActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    true
                }
                R.id.medicines_doctor -> {
                    val intent = Intent(applicationContext, MedicinesDoctorActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    true
                }
                R.id.pacients_doctor -> {
                    val intent = Intent(applicationContext, PacientsDoctorActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    true
                }

            }
            false
        }
    }
}