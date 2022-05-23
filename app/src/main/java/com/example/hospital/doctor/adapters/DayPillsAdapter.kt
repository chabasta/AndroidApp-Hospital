package com.example.hospital.doctor.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hospital.databinding.*
import com.example.hospital.model.PillModel

class DayPillsAdapter : RecyclerView.Adapter<DayPillsAdapter.AbstractHolder>{

    private val context : Context
    private val modelArrayList: ArrayList<PillModel>
    private lateinit var binding: MedicinePillsEditCardBinding

    constructor(context: Context, modelArrayList: ArrayList<PillModel>) {
        this.context = context
        this.modelArrayList = modelArrayList
    }


    inner class AbstractHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var pillText : TextView = binding.medicinesCardPatientTitle
        var pillCount : TextView = binding.medicinesCardPatientCount

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractHolder {
        binding = MedicinePillsEditCardBinding.inflate(LayoutInflater.from(context), parent, false)

        return AbstractHolder(binding.root)
    }

    override fun onBindViewHolder(holder: AbstractHolder, position: Int) {
        val model = modelArrayList[position]
        val pillText = model.name
        val pillCount = model.pillNumbers

        holder.pillText.text = pillText
        holder.pillCount.text = "$pillCount pills"

    }

    override fun getItemCount(): Int {
        return modelArrayList.size
    }


}
