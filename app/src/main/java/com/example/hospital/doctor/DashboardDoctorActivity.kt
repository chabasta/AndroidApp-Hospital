package com.example.hospital.doctor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hospital.MainActivity
import com.example.hospital.R
import com.example.hospital.databinding.ActivityDashboardBinding
import com.example.hospital.databinding.ActivityDashboardDoctorBinding
import com.example.hospital.databinding.ActivityPillsEditBinding
import com.example.hospital.doctor.adapters.*
import com.example.hospital.doctor.data.DataSourceNotifications
import com.example.hospital.doctor.data.DataSrouceNewReservations
import com.example.hospital.model.MedicineModel
import com.example.hospital.model.NotificationModel
import com.example.hospital.model.PillModel
import com.example.hospital.model.RoomModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DashboardDoctorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardDoctorBinding
    private lateinit var firebaseAuth: FirebaseAuth
    lateinit var bottomNavigationView: BottomNavigationView

    private lateinit var notifications : ArrayList<NotificationModel>
    private lateinit var newReservations : ArrayList<RoomModel>

    private lateinit var toolbar: Toolbar

    private lateinit var notificationsAdapter: NotificationAdapter
    private lateinit var newReservationsAdapter: NewReservationsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardDoctorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        // top bar (tool bar)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        loadNotifications()

        loadNewReservations()

        initBottomNavBar()
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

    private fun loadNewReservations() {
        newReservations = ArrayList()
        val ref = FirebaseDatabase.getInstance().getReference("Rooms").orderByChild("newReservation").equalTo(true)
        ref.addValueEventListener(  object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                newReservations.clear()
                for (ds in snapshot.children){
                    val model = ds.getValue(RoomModel::class.java)!!
                    newReservations.add(model)
                }
                newReservationsAdapter = NewReservationsAdapter(this@DashboardDoctorActivity, newReservations)
                binding.recyclerDoctorNewReservationsView.adapter = newReservationsAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun loadNotifications() {
        notifications = ArrayList()
        val ref = FirebaseDatabase.getInstance().getReference("Messages")
        ref.addValueEventListener(  object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                notifications.clear()
                for (ds in snapshot.children){
                    val model = ds.getValue(NotificationModel::class.java)!!
                    notifications.add(model)
                }
                notificationsAdapter = NotificationAdapter(this@DashboardDoctorActivity, notifications)
                binding.recyclerNotificationsView.adapter = notificationsAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })    }


    fun initBottomNavBar(){
        bottomNavigationView = findViewById(R.id.bottom_nav_doctor)

        bottomNavigationView.apply { this.selectedItemId = R.id.dashboard_doctor }

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.dashboard_doctor -> {
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
                else -> {
                    false
                }
            }
        }
    }
}