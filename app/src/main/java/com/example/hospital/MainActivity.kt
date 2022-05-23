package com.example.hospital

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.os.PatternMatcher
import android.util.Patterns
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hospital.doctor.DashboardDoctorActivity
import com.example.hospital.patient.Dashboard
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MainActivity : AppCompatActivity() {

    lateinit var login_input: EditText
    lateinit var password_input: EditText
    lateinit var login_button: Button


    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        login_button = findViewById(R.id.login_button)
        login_input = findViewById(R.id.login_username)
        password_input = findViewById(R.id.login_password)

        progressDialog = ProgressDialog(this)
        progressDialog.setCanceledOnTouchOutside(false)

        firebaseAuth = FirebaseAuth.getInstance()

        initImport()

        login_button.setOnClickListener {

            login()

        }
    }

    private fun initImport() {
        val doctorEmail = "doctor@gmail.com"
        val doctorPassword = "123456"

        firebaseAuth.createUserWithEmailAndPassword(doctorEmail, doctorPassword)
            .addOnSuccessListener {
                println("User registered")
                val uid = firebaseAuth.uid

                val timestamp = System.currentTimeMillis()

                val hashMap: HashMap<String, Any?> = HashMap()
                hashMap["uid"] = uid
                hashMap["email"] = doctorEmail
                hashMap["name"] = "doctor"
                hashMap["surname"] = "doctor"
                hashMap["role"] = "doctor"
                hashMap["timestamp"] = timestamp

                val ref = FirebaseDatabase.getInstance().getReference("Users")
                ref.child(uid!!)
                    .setValue(hashMap)
                    .addOnSuccessListener {
                        println("Import has done")
                        finish()
                    }
                    .addOnFailureListener() { e ->
                        println("Import failed due to ${e.message}")
                    }
            }
            .addOnFailureListener{ e ->
                println("Failed registration due to ${e.message}")
            }

    }

    private var email = ""
    private var password = ""

    private fun login() {
        email = login_input.text.toString().trim()
        password = password_input.text.toString().trim()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Invalid format email..", Toast.LENGTH_SHORT).show()
        } else if (password.isEmpty()) {
            Toast.makeText(this, "Password is empty..", Toast.LENGTH_SHORT).show()
        } else {
            progressDialog.setMessage("Logging in..")
            progressDialog.show()

            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    progressDialog.setMessage("Checking user...")

                    val firebaseAuth = firebaseAuth.currentUser!!

                    val ref = FirebaseDatabase.getInstance().getReference("Users")
                    ref.child(firebaseAuth.uid)
                        .addListenerForSingleValueEvent(object : ValueEventListener{
                            override fun onDataChange(snapshot: DataSnapshot) {
                                progressDialog.dismiss()

                                val role = snapshot.child("role").value

                                if (role == "doctor") {
                                    startActivity(Intent(this@MainActivity, DashboardDoctorActivity::class.java))
                                } else if (role == "pacient") {
                                    startActivity(Intent(this@MainActivity, Dashboard::class.java))
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")
                            }

                        })
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Login failed due to ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }

    }


    //unfocuse where touch outside inputs
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v: View? = currentFocus
            if (v is TextInputEditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm: InputMethodManager =
                        getSystemService (Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

}