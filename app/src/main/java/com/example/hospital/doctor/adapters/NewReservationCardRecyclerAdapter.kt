package com.example.hospital.doctor.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hospital.R
import com.example.hospital.model.NewReservationCard

class NewReservationCardRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    private var items: List<NewReservationCard> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NewReservationsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.new_reservation_card_doctor, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is NewReservationsViewHolder -> {
                holder.bind(items.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(newReservationsList: List<NewReservationCard>){
        items = newReservationsList
    }

    class NewReservationsViewHolder constructor(
        itemView: View
    ): RecyclerView.ViewHolder(itemView){

        var room: TextView = itemView.findViewById(R.id.new_reservation_card_doctor_room)
        var date: TextView = itemView.findViewById(R.id.new_reservation_card_doctor_date)
        var patient: TextView = itemView.findViewById(R.id.new_reservation_card_doctor_patient_name)

        fun bind(newReservationCard: NewReservationCard){
            room.text = newReservationCard.room
            date.text = newReservationCard.date
            patient.text = newReservationCard.patient
        }

    }
}