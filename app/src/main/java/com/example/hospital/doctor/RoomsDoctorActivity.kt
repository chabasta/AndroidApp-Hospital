package com.example.hospital.doctor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hospital.R
import com.example.hospital.databinding.ActivityPacientsDoctorBinding
import com.example.hospital.databinding.ActivityRoomsDoctorBinding
import com.example.hospital.doctor.adapters.NewReservationCardRecyclerAdapter
import com.example.hospital.doctor.adapters.NotificationCardRecyclerAdapter
import com.example.hospital.doctor.adapters.PatientCardRecyclerAdapter
import com.example.hospital.doctor.adapters.RoomCardRecyclerAdapter
import com.example.hospital.doctor.data.DataSourceRooms
import com.example.hospital.doctor.data.DataSrouceNewReservations
import com.example.hospital.model.PatientModel
import com.example.hospital.model.RoomModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RoomsDoctorActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: ActivityRoomsDoctorBinding
    lateinit var bottomNavigationView: BottomNavigationView

    private lateinit var roomsArrayList: ArrayList<RoomModel>

    private lateinit var roomAdapter: RoomCardRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rooms_doctor)

        binding = ActivityRoomsDoctorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        loadRooms()

        var btn_create_new_room = findViewById<Button>(R.id.btn_create_new_room)

        btn_create_new_room.setOnClickListener {
            val intent = Intent(applicationContext, CreateRoom::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
            true
        }

        initBottomNavBar()
    }

    private fun loadRooms() {

        var instance = FirebaseDatabase.getInstance()
        var roomsRef = instance.getReference("Rooms")

        roomsArrayList = ArrayList()
        roomsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                roomsArrayList.clear()
                for (roomSnapshot in snapshot.children) {
                    val roomModel = roomSnapshot.getValue(RoomModel::class.java)!!
                    // load data from users
                    var patientId = roomSnapshot.child("pacientId").getValue(String::class.java)
                    val userRefs = instance.getReference("Users")
                    userRefs.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            for (ds in snapshot.children) {
                                val userModel = ds.getValue(PatientModel::class.java)!!
                                println(patientId)
                                println(userModel.uid)
                                if (userModel.uid.equals(patientId)) {
                                    roomModel.pacientName = userModel.name + ' ' + userModel.surname
                                }
                            }

                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }

                    })

                    roomsArrayList.add(roomModel)
                }
                roomAdapter = RoomCardRecyclerAdapter(this@RoomsDoctorActivity, roomsArrayList)
                binding.recyclerRoomsView.adapter = roomAdapter
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