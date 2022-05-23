package com.example.hospital.doctor.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hospital.R
import com.example.hospital.model.NotificationCard

class NotificationCardRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>()

{
    private var items: List<NotificationCard> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NotificationViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.notification_card_doctor, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is NotificationViewHolder -> {
                holder.bind(items.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(notificationsList: List<NotificationCard>){
        items = notificationsList
    }

    class NotificationViewHolder constructor(
        itemView: View
    ): RecyclerView.ViewHolder(itemView){

        var room: TextView = itemView.findViewById(R.id.notifications_card_doctor_room)
        var time: TextView = itemView.findViewById(R.id.notifications_card_doctor_time)
        var message: TextView = itemView.findViewById(R.id.notifications_card_doctor_message)

        fun bind(reservationCard: NotificationCard){
            room.text = reservationCard.room
            time.text = reservationCard.time
            message.text = reservationCard.message
        }

    }
}