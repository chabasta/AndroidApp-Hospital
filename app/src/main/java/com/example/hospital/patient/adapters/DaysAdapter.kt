package com.example.hospital.patient.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hospital.databinding.DayPillsEditCardBinding
import com.example.hospital.databinding.NewReservationCardDoctorBinding
import com.example.hospital.databinding.RoomsCardDoctorBinding
import com.example.hospital.doctor.PatientDayPills
import com.example.hospital.doctor.PillsEdit
import com.example.hospital.model.DayModel
import com.example.hospital.patient.DayPillsActivity
import java.text.SimpleDateFormat

class DaysAdapter : RecyclerView.Adapter<DaysAdapter.DayHolder>{

    private val context : Context
    private val dayArrayList: ArrayList<DayModel>
    private lateinit var binding: DayPillsEditCardBinding

    constructor(context: Context, dayArrayList: ArrayList<DayModel>) {
        this.context = context
        this.dayArrayList = dayArrayList
    }


    inner class DayHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var dayText : TextView = binding.dayPillsEditDay
        var imageButton : ImageButton = binding.dayPillsEditMedicines

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayHolder {
        binding = DayPillsEditCardBinding.inflate(LayoutInflater.from(context), parent, false)

        return DayHolder(binding.root)
    }

    override fun onBindViewHolder(holder: DayHolder, position: Int) {
        val model = dayArrayList[position]
        val dayText = model.text
        val dayUid = model.id

        holder.dayText.text = dayText

        holder.imageButton.setOnClickListener{
            val myIntent = Intent(context, DayPillsActivity::class.java)
            myIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            myIntent.putExtra("dayUid", dayUid)
            context.startActivity(myIntent)
        }
    }

    override fun getItemCount(): Int {
        return dayArrayList.size
    }


}