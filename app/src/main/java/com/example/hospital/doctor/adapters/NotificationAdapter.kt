package com.example.hospital.doctor.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.hospital.databinding.NewReservationCardDoctorBinding
import com.example.hospital.databinding.NotificationCardDoctorBinding
import com.example.hospital.databinding.RoomsCardDoctorBinding
import com.example.hospital.doctor.PatientDayPills
import com.example.hospital.model.NotificationModel
import com.example.hospital.model.RoomModel
import com.example.hospital.patient.Dashboard
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


class NotificationAdapter : RecyclerView.Adapter<NotificationAdapter.RoomHolder> {

    private val context : Context
    private val notifications: ArrayList<NotificationModel>
    private lateinit var binding: NotificationCardDoctorBinding

    constructor(context: Context, roomArrayList: ArrayList<NotificationModel>) {
        this.context = context
        this.notifications = roomArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomHolder {
        binding = NotificationCardDoctorBinding.inflate(LayoutInflater.from(context), parent, false)

        return RoomHolder(binding.root)
    }

    override fun onBindViewHolder(holder: RoomHolder, position: Int) {
        val model = notifications[position]
        val roomNumber = model.roomNumber
        val message = model.text
        val time = model.date


        holder.roomNumber.text = roomNumber
        holder.message.text = message

    }

    override fun getItemCount(): Int {
        return notifications.size
    }

    inner class RoomHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var roomNumber : TextView = binding.notificationsCardDoctorRoom
        var message : TextView = binding.notificationsCardDoctorMessage
    }

}