package com.example.hospital.patient

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hospital.MainActivity
import com.example.hospital.R
import com.example.hospital.databinding.ActivityDashboardBinding
import com.example.hospital.databinding.ActivityPatientDayPillsBinding
import com.example.hospital.doctor.adapters.PatientDaysAdapter
import com.example.hospital.model.DayModel
import com.example.hospital.patient.adapters.DaysAdapter
import com.example.hospital.patient.adapters.MedicineCardRecyclerAdapter
import com.example.hospital.patient.adapters.MedicineDaysCardRecyclerAdapter
import com.example.hospital.patient.data.DataSourceMedicine
import com.example.hospital.patient.data.DataSourceMedicineDays
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Dashboard : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var firebaseAuth: FirebaseAuth
    lateinit var bottomNavigationView: BottomNavigationView

    private lateinit var toolbar : Toolbar

    private lateinit var days : ArrayList<DayModel>

    private lateinit var patientDaysAdapter : DaysAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        firebaseAuth = FirebaseAuth.getInstance()

        loadPacientDays()

        initBottomNavBar()
    }

    private fun loadPacientDays() {
        days = ArrayList()

        val ref = FirebaseDatabase.getInstance().getReference("Days")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                days.clear()
                for (ds in snapshot.children){
                    val model = ds.getValue(DayModel::class.java)!!
                    if(model.patientId == firebaseAuth.uid){
                        days.add(model)
                    }
                }
                patientDaysAdapter = DaysAdapter(this@Dashboard, days)
                binding.recyclerDaysView.adapter = patientDaysAdapter
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

    //reaction on click toolbar item
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemview = item.itemId

        when(itemview){
            R.id.exit -> {
                FirebaseAuth.getInstance().signOut();
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0,0)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

}