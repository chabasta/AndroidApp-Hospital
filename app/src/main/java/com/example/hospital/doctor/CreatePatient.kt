package com.example.hospital.doctor

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.hospital.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class CreatePatient : AppCompatActivity() {

    lateinit var bottomNavigationView: BottomNavigationView

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_patient)

        val name = findViewById<EditText>(R.id.create_patient_name)
        val surname = findViewById<EditText>(R.id.create_patient_surname)
        val email = findViewById<EditText>(R.id.create_patient_email)
        val password = findViewById<EditText>(R.id.create_patient_password)
        val submit = findViewById<Button>(R.id.btn_create_new_patient)

        progressDialog = ProgressDialog(this)
        progressDialog.setCanceledOnTouchOutside(false)

        firebaseAuth = FirebaseAuth.getInstance()

        val doctorId = firebaseAuth.uid

        submit.setOnClickListener{
            firebaseAuth.createUserWithEmailAndPassword(email.text.toString().trim(), password.text.toString().trim())
                .addOnSuccessListener {
                    println("Pacient registered")
                    val uid = firebaseAuth.uid

                    val timestamp = System.currentTimeMillis()

                    val hashMap: HashMap<String, Any?> = HashMap()
                    hashMap["uid"] = uid
                    hashMap["email"] = email.text.toString().trim()
                    hashMap["name"] = name.text.toString().trim()
                    hashMap["surname"] = surname.text.toString().trim()
                    hashMap["role"] = "pacient"
                    hashMap["timestamp"] = timestamp
                    hashMap["doctorId"] = doctorId

                    val ref = FirebaseDatabase.getInstance().getReference("Users")
                    ref.child(uid!!)
                        .setValue(hashMap)
                        .addOnSuccessListener {
                            println("Success")
                            finish()
                        }
                        .addOnFailureListener() { e ->
                            println("Registration failed due to ${e.message}")
                        }
                }
                .addOnFailureListener{ e ->
                    println("Failed registration due to ${e.message}")
                }

        }

        initBottomNavBar()
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
                    val intent = Intent(applicationContext, PacientsDoctorActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0,0)
                }

            }
            false
        }
    }
}