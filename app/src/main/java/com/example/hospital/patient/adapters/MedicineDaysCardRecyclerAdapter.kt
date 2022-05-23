package com.example.hospital.patient.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hospital.R
import com.example.hospital.model.MedicinesDayCard

class MedicineDaysCardRecyclerAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private var items: List<MedicinesDayCard> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MedicinesDayViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.medicines_day_card_patient, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is MedicinesDayViewHolder -> {
                holder.bind(items.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(medicinesDaysList: List<MedicinesDayCard>){
        items = medicinesDaysList
    }

    class MedicinesDayViewHolder constructor(
        itemView: View
    ): RecyclerView.ViewHolder(itemView){

        var day: TextView = itemView.findViewById(R.id.medicines_day_card_patient_day)

        fun bind(medicinesDayCard: MedicinesDayCard){
            day.setText(medicinesDayCard.day)
        }

    }

}