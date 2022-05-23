package com.example.hospital.patient

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.hospital.R
import com.example.hospital.databinding.ActivityCameraBinding
import com.example.hospital.model.MedicineCard
import com.example.hospital.model.PatientModel
import com.example.hospital.model.RoomModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.zxing.integration.android.IntentIntegrator
import org.json.JSONException
import org.json.JSONObject
import org.w3c.dom.Text
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import kotlin.time.days


private const val CAMERA_REQUEST_CODE = 101

class Camera : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var binding: ActivityCameraBinding
    lateinit var qrScanIntegrator: IntentIntegrator
    lateinit var myCalendar:Calendar

    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "package com.example.hospital.patient"
    private val description = "successfully reserved"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        val view = binding.root

        firebaseAuth = FirebaseAuth.getInstance()

        setContentView(view)
        setOnClickListener()
        setUpPermission()
        setupScanner()
        myCalendar = Calendar.getInstance()


        initBottomNavBar()
    }

    private fun setupScanner() {
        qrScanIntegrator = IntentIntegrator(this)
    }


    private fun setOnClickListener() {

        binding.btnScan.setOnClickListener {
            performAction()
        }
    }

    private fun performAction() {
        // Code to perform action when button is clicked.
        qrScanIntegrator.initiateScan()
    }


    @SuppressLint("NewApi")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            // If QRCode has no data.
            if (result.contents == null) {
                Toast.makeText(this, "Result not found", Toast.LENGTH_LONG).show()
            } else {
                // If QRCode contains data.
                try {
                    // Converting the data to json format
                    val obj = JSONObject(result.contents)

                    // Show values in UI.
                    val roomId = obj.getString("room")
                    var roomUid = ""
                    var roomState = ""
                    var namePatient = ""

                    val reserved_room_result = findViewById<TextView>(R.id.negative_result)
                    val free_room_from = findViewById<TextView>(R.id.camera_from_title)
                    val free_room_edittext_from = findViewById<EditText>(R.id.camera_date_from)
                    val free_room_to = findViewById<TextView>(R.id.camera_to_title)
                    val free_room_edittext_to = findViewById<EditText>(R.id.camera_date_to)
                    val button_reservate = findViewById<Button>(R.id.btn_reservate)

                    var dateFromTimestamp : Long = 0
                    var dateToTimestamp : Long = 0

                    val ref = FirebaseDatabase.getInstance().getReference("Rooms")
                    ref.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            for (ds in snapshot.children){
                                val model = ds.getValue(RoomModel::class.java)!!
                                if(model.roomNumber == roomId){
                                    roomState = model.state
                                    roomUid = model.uid
                                }
                            }

                            binding.room.text = roomId
                            binding.state.text = roomState

                            val date = DatePickerDialog.OnDateSetListener { view, year, month, day ->
                                myCalendar.set(Calendar.YEAR, year)
                                myCalendar.set(Calendar.MONTH, month)
                                myCalendar.set(Calendar.DAY_OF_MONTH, day)
                                val myFormat = "dd.MM.yyyy"
                                val dateFormat = SimpleDateFormat(myFormat, Locale.ENGLISH)
                                free_room_edittext_from.setText(dateFormat.format(myCalendar.getTime()))
                                dateFromTimestamp = myCalendar.timeInMillis
                            }

                            val dateTo = DatePickerDialog.OnDateSetListener { view, year, month, day ->
                                myCalendar.set(Calendar.YEAR, year)
                                myCalendar.set(Calendar.MONTH, month)
                                myCalendar.set(Calendar.DAY_OF_MONTH, day)
                                val myFormat = "dd.MM.yyyy"
                                val dateFormat = SimpleDateFormat(myFormat, Locale.ENGLISH)
                                free_room_edittext_to.setText(dateFormat.format(myCalendar.getTime()))
                                dateToTimestamp = myCalendar.timeInMillis

                            }

                            myCalendar.get(Calendar.HOUR_OF_DAY)

                            when(roomState){
                                "free" -> {

                                    free_room_from.visibility = View.VISIBLE

                                    free_room_edittext_from.apply{
                                        this.visibility = View.VISIBLE
                                        this.setOnClickListener(object : View.OnClickListener {

                                            override fun onClick(view: View?) {
                                                DatePickerDialog(
                                                    this@Camera,
                                                    android.R.style.Theme_Holo_Dialog_NoActionBar_MinWidth,
                                                    date,
                                                    myCalendar.get(Calendar.YEAR),
                                                    myCalendar.get(Calendar.MONTH),
                                                    myCalendar.get(Calendar.DAY_OF_MONTH)
                                                ).show()
                                            }
                                        })
                                    }

                                    free_room_to.visibility = View.VISIBLE

                                    free_room_edittext_to.apply {
                                        this.visibility = View.VISIBLE
                                        this.setOnClickListener(object : View.OnClickListener {

                                            override fun onClick(view: View?) {
                                                DatePickerDialog(
                                                    this@Camera,
                                                    android.R.style.Theme_Holo_Dialog_NoActionBar_MinWidth,
                                                    dateTo,
                                                    myCalendar.get(Calendar.YEAR),
                                                    myCalendar.get(Calendar.MONTH),
                                                    myCalendar.get(Calendar.DAY_OF_MONTH)
                                                ).show()
                                            }
                                        })
                                    }

                                    button_reservate.apply {
                                        this.isEnabled = true
                                        this.visibility = View.VISIBLE
                                    }

                                    reserved_room_result.visibility = View.INVISIBLE
                                }
                                "reserved" -> {

                                    free_room_from.visibility = View.INVISIBLE

                                    free_room_edittext_from.apply{
                                        this.visibility = View.INVISIBLE
                                        this.isFocusableInTouchMode = false
                                    }

                                    free_room_to.visibility = View.INVISIBLE

                                    free_room_edittext_to.apply{
                                        this.visibility = View.INVISIBLE
                                        this.isFocusableInTouchMode = false
                                    }

                                    button_reservate.apply{
                                        this.isEnabled = false
                                        this.visibility = View.INVISIBLE
                                    }

                                    reserved_room_result.visibility = View.VISIBLE
                                }

                            }
                        }
                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }

                    })

                    button_reservate.setOnClickListener {

                        var instance = FirebaseDatabase.getInstance()

                        var rooms = FirebaseDatabase.getInstance().getReference("Rooms")
                        rooms.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                for (ds in snapshot.children) {

                                    val model = ds.getValue(RoomModel::class.java)!!

                                    if (model.roomNumber == roomId) {
                                        println("room with room number ${model.roomNumber} found")
                                        rooms.child(model.roomId).child("pacientId").setValue(firebaseAuth.uid)
                                        rooms.child(model.roomId).child("state").setValue("reserved")
                                        rooms.child(model.roomId).child("fromDate").setValue(dateFromTimestamp)
                                        rooms.child(model.roomId).child("toDate").setValue(dateToTimestamp)
                                        rooms.child(model.roomId).child("newReservation").setValue(true)

                                        var patientId = ds.child("pacientId").getValue(String::class.java)

                                        val days: Long = TimeUnit.MILLISECONDS.toDays(dateToTimestamp - dateFromTimestamp)

                                        instance.getReference("Users")
                                            .child("$patientId")
                                            .get()
                                            .addOnSuccessListener {
                                                println("patient of room found ${it.value}")
                                                var name = it.child("name")
                                                    .getValue(String::class.java) + ' ' + it.child("surname")
                                                    .getValue(String::class.java)
                                                rooms.child(model.roomId).child("pacientName").setValue(name)
                                                for (i in 1..days){
                                                    println("day $i")
                                                    val timestamp = System.currentTimeMillis() + i

                                                    val hashMap: HashMap<String, Any?> = HashMap()
                                                    hashMap["id"] = "$timestamp"
                                                    hashMap["roomId"] = model.roomId
                                                    hashMap["patientId"] = firebaseAuth.uid
                                                    hashMap["text"] = "Day $i"

                                                    instance.getReference("Days")
                                                        .child("$timestamp")
                                                        .setValue(hashMap)
                                                        .addOnSuccessListener {
                                                            println("Success")
                                                        }
                                                        .addOnFailureListener() { e ->
                                                            println("Registration failed due to ${e.message}")
                                                        }
                                                }
                                            }.addOnFailureListener{
                                                println("error $it")
                                            }

                                    }
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")
                            }
                        })

                        //notification implement
                        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


                        val intent = Intent(this, Reservations::class.java)
                        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT )

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {



                            notificationChannel = NotificationChannel(
                                channelId,
                                description,
                                NotificationManager.IMPORTANCE_HIGH
                            )
                            notificationChannel.enableLights(true)
                            notificationChannel.lightColor = Color.GREEN
                            notificationChannel.enableVibration(true)
                            notificationManager.createNotificationChannel(notificationChannel)

                            builder = Notification.Builder(this, channelId)
                                .setContentTitle("Hospital app")
                                .setContentText("successfully reserved")
                                .setSmallIcon(R.drawable.ic_launcher_foreground)
                                .setLargeIcon(BitmapFactory.decodeResource(this.resources , R.drawable.ic_launcher_background))
                                .setContentIntent(pendingIntent)

                        }else{
                            builder = Notification.Builder(this)
                                .setContentTitle("Hospital app")
                                .setContentText("successfully reserved")
                                .setSmallIcon(R.drawable.ic_launcher_foreground)
                                .setLargeIcon(BitmapFactory.decodeResource(this.resources , R.drawable.ic_launcher_background))
                                .setContentIntent(pendingIntent)
                        }
                        notificationManager.notify(1234, builder.build())


                        //redirect
                        startActivity(intent)
                        overridePendingTransition(0, 0)
                    }


                } catch (e: JSONException) {
                    e.printStackTrace()

                    // Data not in the expected format. So, whole object as toast message.
                    Toast.makeText(this, result.contents, Toast.LENGTH_LONG).show()
                }
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun setUpPermission(){
        val permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)

        if (permission != PackageManager.PERMISSION_GRANTED){
            makeRequest()
        }
    }

    fun makeRequest(){
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), CAMERA_REQUEST_CODE)
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            CAMERA_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "You need the camera permission to be able to use this app", Toast.LENGTH_SHORT).show()
                } else{
                    //successful
                }
            }
        }
    }

    fun initBottomNavBar() {
        bottomNavigationView = findViewById(R.id.bottom_nav_patient)

        bottomNavigationView.apply { this.selectedItemId = R.id.camera }

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.dashboard -> {
                    val intent = Intent(applicationContext, Dashboard::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    true
                }
                R.id.reservations -> {
                    val intent = Intent(applicationContext, Reservations::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    true
                }
                R.id.camera -> {
                    true
                }
                else -> {
                    bottomNavigationView.selectedItemId = item.itemId
                }
            }
            false
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


//
//private fun testQrCode() {
//
//    // Show values in UI.
//    val roomId = "25"
//    var roomUid = ""
//    var roomState = ""
//
//    val reserved_room_result = findViewById<TextView>(R.id.negative_result)
//    val free_room_from = findViewById<TextView>(R.id.camera_from_title)
//    val free_room_edittext_from = findViewById<EditText>(R.id.camera_date_from)
//    val free_room_to = findViewById<TextView>(R.id.camera_to_title)
//    val free_room_edittext_to = findViewById<EditText>(R.id.camera_date_to)
//    val button_reservate = findViewById<Button>(R.id.btn_reservate)
//
//    var dateFromTimestamp : Long = 0
//    var dateToTimestamp : Long = 0
//
//    val ref = FirebaseDatabase.getInstance().getReference("Rooms")
//    ref.addValueEventListener(object : ValueEventListener {
//        override fun onDataChange(snapshot: DataSnapshot) {
//            for (ds in snapshot.children){
//                val model = ds.getValue(RoomModel::class.java)!!
//                if(model.roomNumber == roomId){
//                    roomState = model.state
//                    roomUid = model.uid
//                }
//            }
//
//            binding.room.text = roomId
//            binding.state.text = roomState
//
//            val date = DatePickerDialog.OnDateSetListener { view, year, month, day ->
//                view
//                myCalendar.set(Calendar.YEAR, year)
//                myCalendar.set(Calendar.MONTH, month)
//                myCalendar.set(Calendar.DAY_OF_MONTH, day)
//                val myFormat = "dd.MM.yyyy"
//                val dateFormat = SimpleDateFormat(myFormat, Locale.ENGLISH)
//                free_room_edittext_from.setText(dateFormat.format(myCalendar.getTime()))
//
//                dateFromTimestamp = myCalendar.timeInMillis
//            }
//
//            val dateTo = DatePickerDialog.OnDateSetListener { view, year, month, day ->
//                view
//                myCalendar.set(Calendar.YEAR, year)
//                myCalendar.set(Calendar.MONTH, month)
//                myCalendar.set(Calendar.DAY_OF_MONTH, day)
//                val myFormat = "dd.MM.yyyy"
//                val dateFormat = SimpleDateFormat(myFormat, Locale.ENGLISH)
//                free_room_edittext_to.setText(dateFormat.format(myCalendar.getTime()))
//                dateToTimestamp = myCalendar.timeInMillis
//            }
//
//            when(roomState){
//                "free" -> {
//
//                    free_room_from.visibility = View.VISIBLE
//
//                    free_room_edittext_from.apply{
//                        this.visibility = View.VISIBLE
////                                this.isFocusableInTouchMode = true
//                        this.setOnClickListener(object : View.OnClickListener {
//
//                            override fun onClick(view: View?) {
//                                Log.println(Log.INFO, "asdasd", "asdasdasdasd")
//                                DatePickerDialog(
//                                    this@Camera,
//                                    android.R.style.Theme_Holo_Dialog_NoActionBar_MinWidth,
//                                    date,
//                                    myCalendar.get(Calendar.YEAR),
//                                    myCalendar.get(Calendar.MONTH),
//                                    myCalendar.get(Calendar.DAY_OF_MONTH)
//                                ).show()
//                            }
//                        })
//                    }
//
//                    free_room_to.visibility = View.VISIBLE
//
//                    free_room_edittext_to.apply {
//                        this.visibility = View.VISIBLE
//                        this.setOnClickListener(object : View.OnClickListener {
//
//                            override fun onClick(view: View?) {
//                                Log.println(Log.INFO, "asdasd", "asdasdasdasd")
//                                DatePickerDialog(
//                                    this@Camera,
//                                    android.R.style.Theme_Holo_Dialog_NoActionBar_MinWidth,
//                                    dateTo,
//                                    myCalendar.get(Calendar.YEAR),
//                                    myCalendar.get(Calendar.MONTH),
//                                    myCalendar.get(Calendar.DAY_OF_MONTH)
//                                ).show()
//                            }
//                        })
//                    }
//
//                    button_reservate.apply {
//                        this.isEnabled = true
//                        this.visibility = View.VISIBLE
//                    }
//
//                    reserved_room_result.visibility = View.INVISIBLE
//                }
//                "reserved" -> {
//
//                    free_room_from.visibility = View.INVISIBLE
//
//                    free_room_edittext_from.apply{
//                        this.visibility = View.INVISIBLE
//                        this.isFocusableInTouchMode = false
//                    }
//
//                    free_room_to.visibility = View.INVISIBLE
//
//                    free_room_edittext_to.apply{
//                        this.visibility = View.INVISIBLE
//                        this.isFocusableInTouchMode = false
//                    }
//
//                    button_reservate.apply{
//                        this.isEnabled = false
//                        this.visibility = View.INVISIBLE
//                    }
//
//                    reserved_room_result.visibility = View.VISIBLE
//                }
//            }
//        }
//        override fun onCancelled(error: DatabaseError) {
//            TODO("Not yet implemented")
//        }
//
//    })
//
//    button_reservate.setOnClickListener {
//        var pacientName = ""
//        var pacientSurname = ""
//
//
//        var rooms = FirebaseDatabase.getInstance().getReference("Rooms")
//        rooms.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                for (ds in snapshot.children) {
//                    val model = ds.getValue(RoomModel::class.java)!!
//                    if (model.roomNumber == roomId) {
//
//                        rooms.child(model.roomId).child("pacientId").setValue(firebaseAuth.uid)
//                        rooms.child(model.roomId).child("state").setValue("reserved")
//                        rooms.child(model.roomId).child("fromDate").setValue(dateFromTimestamp)
//                        rooms.child(model.roomId).child("toDate").setValue(dateToTimestamp)
//
////                            println("Room $model")
////                            println("Data: $name, $pacientSurname")
//                    }
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//        })
//
//        var child = FirebaseDatabase.getInstance().getReference("Users")
//        child.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                for (ds in snapshot.children) {
//                    val model = ds.getValue(PatientModel::class.java)!!
//                    if (model.uid == firebaseAuth.uid) {
//                        pacientName = model.name
//                        pacientSurname = model.surname
//                    }
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//        })
//
//    }
//
//}
