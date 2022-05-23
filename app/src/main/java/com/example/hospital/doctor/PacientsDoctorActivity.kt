package com.example.hospital.doctor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.hospital.R
import com.example.hospital.databinding.ActivityPacientsDoctorBinding
import com.example.hospital.databinding.PatientCardBinding
import com.example.hospital.doctor.adapters.PatientCardRecyclerAdapter
import com.example.hospital.model.PatientModel
import com.example.hospital.patient.Camera
import com.example.hospital.patient.Dashboard
import com.example.hospital.patient.Reservations
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.auth.User
import com.squareup.okhttp.internal.DiskLruCache

class PacientsDoctorActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var binding: ActivityPacientsDoctorBinding
    lateinit var bottomNavigationView: BottomNavigationView

    private lateinit var patientArrayList : ArrayList<PatientModel>

    private lateinit var patientAdapter : PatientCardRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityPacientsDoctorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        loadDoctorPatients()

        var btn_create_new_patient = findViewById<Button>(R.id.btn_patient_create_new_patient)

        btn_create_new_patient.setOnClickListener {
            val intent = Intent(applicationContext, CreatePatient::class.java)
            startActivity(intent)
            overridePendingTransition(0,0)
            true
        }

        initBottomNavBar()
    }

    private fun loadDoctorPatients() {
        patientArrayList = ArrayList()
        val firebaseAuth = firebaseAuth.currentUser!!
        val doctorId = firebaseAuth.uid
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                patientArrayList.clear()
                for (ds in snapshot.children){
                    val model = ds.getValue(PatientModel::class.java)!!
                    if(model.doctorId == doctorId && model.role == "pacient"){
                        val model = ds.getValue(PatientModel::class.java)!!
                        println("User model ${model.uid}")
                        patientArrayList.add(model)
                    }
                }
                println("Patients : ${patientArrayList.toString()}")
                patientAdapter = PatientCardRecyclerAdapter(this@PacientsDoctorActivity, patientArrayList)
                binding.recyclerPatientsView.adapter = patientAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
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