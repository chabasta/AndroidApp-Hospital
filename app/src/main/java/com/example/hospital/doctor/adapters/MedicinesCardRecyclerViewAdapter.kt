package com.example.hospital.doctor.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hospital.databinding.MedicineCardDoctorBinding
import com.example.hospital.databinding.RoomsCardDoctorBinding
import com.example.hospital.doctor.MedicineInfo
import com.example.hospital.doctor.PatientDayPills
import com.example.hospital.model.MedicineModel
import com.example.hospital.model.RoomModel
import com.example.hospital.patient.adapters.MedicineCardRecyclerAdapter

class MedicinesCardRecyclerViewAdapter: RecyclerView.Adapter<MedicinesCardRecyclerViewAdapter.MedicineHolder> {


    private val context : Context
    private val medicineArrayList: ArrayList<MedicineModel>
    private lateinit var binding: MedicineCardDoctorBinding

    constructor(context: Context, medicineArrayList: ArrayList<MedicineModel>) {
        this.context = context
        this.medicineArrayList = medicineArrayList
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineHolder {
        binding = MedicineCardDoctorBinding.inflate(LayoutInflater.from(context), parent, false)

        return MedicineHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MedicineHolder, position: Int) {
        val model = medicineArrayList[position]
        val name = model.name
        val about = model.about
        val id = model.id

        holder.name.text = name
        holder.about.text = about

        holder.link.setOnClickListener {
            println("clicked")
            val myIntent = Intent(context, MedicineInfo::class.java)
            myIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            myIntent.putExtra("id", id)
            context.startActivity(myIntent)
        }

    }

    override fun getItemCount(): Int {
        return medicineArrayList.size
    }

    inner class MedicineHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var name : TextView = binding.medicineCardDoctorName
        var about : TextView = binding.medicineCardDoctorDesriptionText
        var description : TextView = binding.medicineCardDoctorDesriptionLink
        var link: TextView = binding.medicineCardDoctorDesriptionLink
    }

}
