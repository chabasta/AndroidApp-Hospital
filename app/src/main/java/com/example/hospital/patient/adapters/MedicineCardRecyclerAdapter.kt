package com.example.hospital.patient.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hospital.R
import com.example.hospital.model.MedicineCard

class MedicineCardRecyclerAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>()
{

    private var items: List<MedicineCard> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MedicineViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.medicines_card_patient, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is MedicineViewHolder -> {
                holder.bind(items.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(medicinesList: List<MedicineCard>){
        items = medicinesList
    }


    class MedicineViewHolder constructor(
        itemView: View
    ): RecyclerView.ViewHolder(itemView){

        val cardTitle: TextView = itemView.findViewById(R.id.medicines_card_patient_title)
        val cardCount: TextView = itemView.findViewById(R.id.medicines_card_patient_count)
        val checked: CheckBox = itemView.findViewById(R.id.medicines_card_patient_checkbox)

        fun bind(medicineCard: MedicineCard){
            cardTitle.setText(medicineCard.title)
            cardCount.setText(medicineCard.count)
            checked.isChecked = medicineCard.checked
        }

    }
}