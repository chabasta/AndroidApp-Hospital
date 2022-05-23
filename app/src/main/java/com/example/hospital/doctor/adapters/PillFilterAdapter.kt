package com.example.hospital.doctor.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.hospital.databinding.DayPillsEditCardBinding
import com.example.hospital.databinding.NameMedicineCardBinding
import com.example.hospital.databinding.NewReservationCardDoctorBinding
import com.example.hospital.databinding.RoomsCardDoctorBinding
import com.example.hospital.doctor.PatientDayPills
import com.example.hospital.doctor.PillsEdit
import com.example.hospital.model.DayModel
import com.example.hospital.model.MedicineModel
import com.example.hospital.model.PillModel
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.HashMap

class PillFilterAdapter : RecyclerView.Adapter<PillFilterAdapter.AbstractHolder>{

    private val context : Context
    private val dayId : String
    private val modelArrayList: ArrayList<MedicineModel>
    private lateinit var binding: NameMedicineCardBinding

    constructor(context: Context, modelArrayList: ArrayList<MedicineModel>, dayId : String) {
        this.context = context
        this.dayId = dayId
        this.modelArrayList = modelArrayList
    }


    inner class AbstractHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var pillText : TextView = binding.medicinesCardPatientTitle
        var pillNumber : TextView = binding.medicineCardPillCount
        var btn : Button = binding.medicinesCardPatientBtn

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractHolder {
        binding = NameMedicineCardBinding.inflate(LayoutInflater.from(context), parent, false)

        return AbstractHolder(binding.root)
    }

    override fun onBindViewHolder(holder: AbstractHolder, position: Int) {
        val model = modelArrayList[position]
        val pillName = model.name
        val medicineId = model.id
        val medicineName = model.name

        holder.pillText.text = pillName

        holder.btn.setOnClickListener{
            // add pill with this id to
            holder.pillText.setTextColor(Color.parseColor("#77DC8B"))

            // save pill

            val timestamp = System.currentTimeMillis()

            val hashMap: HashMap<String, Any?> = HashMap()
            hashMap["id"] = "$timestamp"
            hashMap["dayId"] = dayId
            hashMap["medicineId"] = medicineId
            hashMap["name"] = medicineName
            hashMap["pillNumbers"] = holder.pillNumber.text.toString()
            hashMap["completed"] = false

            val ref = FirebaseDatabase.getInstance().getReference("Pills")
            ref.child("$timestamp")
                .setValue(hashMap)
                .addOnSuccessListener {
                    println("Success")
                }
                .addOnFailureListener() { e ->
                    println("Registration failed due to ${e.message}")
                }
        }
    }

    override fun getItemCount(): Int {
        return modelArrayList.size
    }


}