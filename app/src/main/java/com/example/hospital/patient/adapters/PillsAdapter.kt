package com.example.hospital.patient.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hospital.databinding.*
import com.example.hospital.model.PillModel
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.FirebaseDatabase

class PillsAdapter : RecyclerView.Adapter<PillsAdapter.AbstractHolder>{

    private val context : Context
    private val modelArrayList: ArrayList<PillModel>
    private lateinit var binding: MedicinesCardPatientBinding

    constructor(context: Context, modelArrayList: ArrayList<PillModel>) {
        this.context = context
        this.modelArrayList = modelArrayList
    }


    inner class AbstractHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var pillText : TextView = binding.medicinesCardPatientTitle
        var pillCount : TextView = binding.medicinesCardPatientCount
        var checkbox : CheckBox = binding.medicinesCardPatientCheckbox
        var pillDetails : ImageView = binding.medicinesCardPatientDescription

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractHolder {
        binding = MedicinesCardPatientBinding.inflate(LayoutInflater.from(context), parent, false)

        return AbstractHolder(binding.root)
    }

    override fun onBindViewHolder(holder: AbstractHolder, position: Int) {
        val model = modelArrayList[position]
        val pillText = model.name
        val pillCount = model.pillNumbers
        val completed = model.completed
        val dayId = model.dayId
        val medicineId = model.medicineId
        val pillId = model.id

        holder.pillText.text = pillText
        holder.pillCount.text = "$pillCount pills"
        holder.checkbox.isChecked = completed

        holder.checkbox.setOnCheckedChangeListener{ buttonView, isChecked ->
            val ref = FirebaseDatabase.getInstance().getReference("Pills")
            if (isChecked) {
                println("Checkbox checked")
                ref.child("$pillId")
                    .child("completed")
                    .setValue(true)

            } else {
                println("Checkbox unchecked")
                ref.child("$pillId")
                    .child("completed")
                    .setValue(false)
            }

        }

    }

    override fun getItemCount(): Int {
        return modelArrayList.size
    }


}
