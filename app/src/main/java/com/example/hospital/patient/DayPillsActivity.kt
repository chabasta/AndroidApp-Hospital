package com.example.hospital.patient

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hospital.R
import com.example.hospital.databinding.ActivityDayPillsBinding
import com.example.hospital.databinding.ActivityPillsEditBinding
import com.example.hospital.doctor.DashboardDoctorActivity
import com.example.hospital.doctor.MedicinesDoctorActivity
import com.example.hospital.doctor.PacientsDoctorActivity
import com.example.hospital.doctor.RoomsDoctorActivity
import com.example.hospital.doctor.adapters.DayPillsAdapter
import com.example.hospital.doctor.adapters.PillFilterAdapter
import com.example.hospital.model.MedicineModel
import com.example.hospital.model.PillModel
import com.example.hospital.patient.adapters.PillsAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DayPillsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDayPillsBinding
    private lateinit var firebaseAuth: FirebaseAuth
    lateinit var bottomNavigationView: BottomNavigationView

    private lateinit var pills : ArrayList<PillModel>

    private lateinit var pillsAdapter: PillsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDayPillsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var bundle :Bundle ?=intent.extras
        var dayUid = bundle!!.getString("dayUid")

        firebaseAuth = FirebaseAuth.getInstance()

        loadPills(dayUid!!)

        initBottomNavBar()
    }

    private fun loadPills(dayUid: String) {
        pills = ArrayList()
        val ref = FirebaseDatabase.getInstance().getReference("Pills").orderByChild("dayId").equalTo(dayUid)
        ref.addValueEventListener(  object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                pills.clear()
                for (ds in snapshot.children){
                    val model = ds.getValue(PillModel::class.java)!!
                    pills.add(model)
                }
                pillsAdapter = PillsAdapter(this@DayPillsActivity, pills)
                binding.medicinesOnDayRecyclerView.adapter = pillsAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun initBottomNavBar(){
        bottomNavigationView = findViewById(R.id.bottom_nav_patient)

        bottomNavigationView.apply { this.selectedItemId = R.id.dashboard }

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.dashboard -> {
                    true
                }
                R.id.reservations -> {
                    val intent = Intent(applicationContext, Reservations::class.java)
                    startActivity(intent)
                    overridePendingTransition(0,0)
                    true
                }
                R.id.camera -> {
                    val intent = Intent(applicationContext, Camera::class.java)
                    startActivity(intent)
                    overridePendingTransition(0,0)
                    true
                }
                else -> {
                    false
                }

            }
        }
    }
}