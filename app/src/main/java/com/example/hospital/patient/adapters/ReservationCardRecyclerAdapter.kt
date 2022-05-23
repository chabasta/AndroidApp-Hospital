package com.example.hospital.patient.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hospital.databinding.ReservationsCardPatientBinding
import com.example.hospital.model.RoomModel
import com.example.hospital.patient.PatientChat
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList


class ReservationCardRecyclerAdapter : RecyclerView.Adapter<ReservationCardRecyclerAdapter.ReservationHolder> {

    private val context : Context
    private val roomArrayList: ArrayList<RoomModel>
    private lateinit var binding: ReservationsCardPatientBinding

    constructor(context: Context, roomArrayList: ArrayList<RoomModel>) {
        this.context = context
        this.roomArrayList = roomArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationHolder {
        binding = ReservationsCardPatientBinding.inflate(LayoutInflater.from(context), parent, false)

        return ReservationHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ReservationHolder, position: Int) {
        val model = roomArrayList[position]
        val floor = model.floor
        val roomNumber = model.roomNumber
        val fromDate = model.fromDate
        val toDate = model.toDate

        val simpleDateFormat = SimpleDateFormat("dd.MM")
        val dateString = simpleDateFormat.format(fromDate)

        val simpleDateFormatTo = SimpleDateFormat("dd.MM")
        val dateStringTo = simpleDateFormatTo.format(toDate)

        holder.roomNumber.text = roomNumber
        holder.floor.text = floor
        holder.fromDate.text = String.format("%s", dateString)
        holder.dueTo.text = String.format("%s", dateStringTo)


        holder.chat.setOnClickListener{
            val myIntent = Intent(context, PatientChat::class.java)
            myIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            myIntent.putExtra("roomNumber", roomNumber)
            context.startActivity(myIntent)
        }
    }

    override fun getItemCount(): Int {
        return roomArrayList.size
    }

    inner class ReservationHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var roomNumber : TextView = binding.reservationsDayCardPatientRoom
        var floor : TextView = binding.reservationsCardPatientFloorInt
        var fromDate : TextView = binding.roomsCardDoctorFrom
        var dueTo: TextView = binding.roomsCardDoctorDueTo
        var chat: ImageView = binding.reservationsCardPatientMessageToDoctor
    }

}