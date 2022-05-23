package com.example.hospital.patient

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hospital.patient.Dashboard
import com.example.hospital.R
import com.example.hospital.databinding.ActivityPillsEditBinding
import com.example.hospital.databinding.ActivityReservationsBinding
import com.example.hospital.doctor.adapters.DayPillsAdapter
import com.example.hospital.doctor.adapters.PillFilterAdapter
import com.example.hospital.model.MedicineModel
import com.example.hospital.model.PillModel
import com.example.hospital.model.RoomModel
import com.example.hospital.patient.adapters.MedicineCardRecyclerAdapter
import com.example.hospital.patient.adapters.ReservationCardRecyclerAdapter
import com.example.hospital.patient.data.DataSourceMedicine
import com.example.hospital.patient.data.DataSourceReservations
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Reservations : AppCompatActivity() {

    private lateinit var binding: ActivityReservationsBinding
    private lateinit var firebaseAuth: FirebaseAuth
    lateinit var bottomNavigationView: BottomNavigationView

    private lateinit var reservations : ArrayList<RoomModel>

    private lateinit var reservationsCardRecyclerAdapter: ReservationCardRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        loadPatientReservations()

        initBottomNavBar()
    }

    private fun loadPatientReservations() {
        reservations = ArrayList()
        val patientUid = firebaseAuth.uid
        val ref = FirebaseDatabase.getInstance().getReference("Rooms").orderByChild("pacientId").equalTo(patientUid)
        ref.addValueEventListener(  object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                reservations.clear()
                for (ds in snapshot.children){
                    val model = ds.getValue(RoomModel::class.java)!!
                    reservations.add(model)
                }
                reservationsCardRecyclerAdapter = ReservationCardRecyclerAdapter(this@Reservations, reservations)
                binding.recyclerReservationsView.adapter = reservationsCardRecyclerAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun initBottomNavBar() {
        bottomNavigationView = findViewById(R.id.bottom_nav_patient)

        bottomNavigationView.apply { this.selectedItemId = R.id.reservations }

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.dashboard -> {
                    val intent = Intent(applicationContext, Dashboard::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    true
                }
                R.id.reservations -> {

                    true
                }
                R.id.camera -> {
                    val intent = Intent(applicationContext, Camera::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    true
                }
            }
            false
        }
    }
}
