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
import com.example.hospital.databinding.RoomsCardDoctorBinding
import com.example.hospital.doctor.PatientDayPills
import com.example.hospital.model.RoomModel
import com.example.hospital.patient.Dashboard
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


class RoomCardRecyclerAdapter : RecyclerView.Adapter<RoomCardRecyclerAdapter.RoomHolder> {

    private val context : Context
    private val roomArrayList: ArrayList<RoomModel>
    private lateinit var binding: RoomsCardDoctorBinding

    constructor(context: Context, roomArrayList: ArrayList<RoomModel>) {
        this.context = context
        this.roomArrayList = roomArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomHolder {
        binding = RoomsCardDoctorBinding.inflate(LayoutInflater.from(context), parent, false)

        return RoomHolder(binding.root)
    }

    override fun onBindViewHolder(holder: RoomHolder, position: Int) {
        val model = roomArrayList[position]
        val uid = model.uid
        val floor = model.floor
        val state = model.state
        val roomNumber = model.roomNumber
        val description = model.description
        val roomId = model.roomId
        val pacientId = model.pacientId
        val pacientName = model.pacientName
        val fromDate = model.fromDate
        val toDate = model.toDate

        val simpleDateFormat = SimpleDateFormat("dd.MM")
        val dateString = simpleDateFormat.format(fromDate)

        val simpleDateFormatTo = SimpleDateFormat("dd.MM")
        val dateStringTo = simpleDateFormatTo.format(toDate)

        holder.roomNumber.text = roomNumber
        holder.floor.text = floor
        holder.state.text = state
        holder.fromDate.text = String.format("%s", dateString)
        holder.dueTo.text = String.format("%s", dateStringTo)
        holder.patientName.text = pacientName


        holder.editPills.setOnClickListener{
            val myIntent = Intent(context, PatientDayPills::class.java)
            myIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            myIntent.putExtra("roomsId", roomId)
            context.startActivity(myIntent)
        }
    }

    override fun getItemCount(): Int {
        return roomArrayList.size
    }

    inner class RoomHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var roomNumber : TextView = binding.roomsCardDoctorRoom
        var state : TextView = binding.roomsCardDoctorStateValue
        var floor : TextView = binding.roomsCardDoctorFloorInt
        var fromDate : TextView = binding.roomsCardDoctorFrom
        var dueTo: TextView = binding.roomsCardDoctorDueTo
        var patientName : TextView = binding.roomsCardDoctorPatientName
        var editPills : Button = binding.roomsCardDoctorBtnEdit
    }

}