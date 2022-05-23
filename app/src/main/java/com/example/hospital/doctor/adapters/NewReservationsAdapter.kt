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
import com.example.hospital.databinding.RoomsCardDoctorBinding
import com.example.hospital.doctor.PatientDayPills
import com.example.hospital.model.RoomModel
import com.example.hospital.patient.Dashboard
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


class NewReservationsAdapter : RecyclerView.Adapter<NewReservationsAdapter.RoomHolder> {

    private val context : Context
    private val roomArrayList: ArrayList<RoomModel>
    private lateinit var binding: NewReservationCardDoctorBinding

    constructor(context: Context, roomArrayList: ArrayList<RoomModel>) {
        this.context = context
        this.roomArrayList = roomArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomHolder {
        binding = NewReservationCardDoctorBinding.inflate(LayoutInflater.from(context), parent, false)

        return RoomHolder(binding.root)
    }

    override fun onBindViewHolder(holder: RoomHolder, position: Int) {
        val model = roomArrayList[position]
        val roomNumber = model.roomNumber
        val pacientName = model.pacientName
        val fromDate = model.fromDate

        val simpleDateFormat = SimpleDateFormat("dd.MM")
        val dateString = simpleDateFormat.format(fromDate)

        holder.roomNumber.text = roomNumber
        holder.fromDate.text = String.format("%s", dateString)
        holder.patientName.text = pacientName

    }

    override fun getItemCount(): Int {
        return roomArrayList.size
    }

    inner class RoomHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var roomNumber : TextView = binding.newReservationCardDoctorRoom
        var fromDate : TextView = binding.newReservationCardDoctorDate
        var patientName : TextView = binding.newReservationCardDoctorPatientName
    }

}