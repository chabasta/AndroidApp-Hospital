package com.example.hospital.doctor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.hospital.R
import com.example.hospital.databinding.ActivityPacientsDoctorBinding
import com.example.hospital.databinding.ActivityPatientDayPillsBinding
import com.example.hospital.databinding.ActivityRoomsDoctorBinding
import com.example.hospital.doctor.adapters.PatientCardRecyclerAdapter
import com.example.hospital.doctor.adapters.PatientDaysAdapter
import com.example.hospital.model.DayModel
import com.example.hospital.model.PatientModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PatientDayPills : AppCompatActivity() {

    private lateinit var binding: ActivityPatientDayPillsBinding
    private lateinit var firebaseAuth: FirebaseAuth
    lateinit var bottomNavigationView: BottomNavigationView

    private lateinit var daysArrayList : ArrayList<DayModel>

    private lateinit var patientDaysAdapter : PatientDaysAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientDayPillsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var bundle :Bundle ?=intent.extras
        var roomUid = bundle!!.getString("roomsId")


        firebaseAuth = FirebaseAuth.getInstance()

        loadPacientDays(roomUid)

        initBottomNavBar()

    }

    private fun loadPacientDays(roomUid : String?) {
        daysArrayList = ArrayList()

        val ref = FirebaseDatabase.getInstance().getReference("Days")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                daysArrayList.clear()
                for (ds in snapshot.children){
                    val model = ds.getValue(DayModel::class.java)!!
                    if(model.roomId == roomUid){
                        daysArrayList.add(model)
                    }
                }
                patientDaysAdapter = PatientDaysAdapter(this@PatientDayPills, daysArrayList)
                binding.recyclerDayPilsView.adapter = patientDaysAdapter
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