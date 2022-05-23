package com.example.hospital.doctor.adapters

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.hospital.databinding.PatientCardBinding
import com.example.hospital.model.PatientModel
import com.google.firebase.database.FirebaseDatabase

class PatientCardRecyclerAdapter : RecyclerView.Adapter<PatientCardRecyclerAdapter.PatientHolder> {

    private val context : Context
    private val patientArrayList: ArrayList<PatientModel>
    private lateinit var binding: PatientCardBinding

    constructor(context: Context, patientArrayList: ArrayList<PatientModel>) {
        this.context = context
        this.patientArrayList = patientArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientHolder {
        binding = PatientCardBinding.inflate(LayoutInflater.from(context), parent, false)

        return PatientHolder(binding.root)
    }

    override fun onBindViewHolder(holder: PatientHolder, position: Int) {
        val model = patientArrayList[position]
        val uid = model.uid
        val name = model.name
        val surname = model.surname
        val roomNumber = model.roomNumber

        holder.patientName.text = name
        holder.patientSurname.text = surname
        holder.patientRoom.text = roomNumber

        holder.deleteBtn.setOnClickListener{
            println("clicked")
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Delete")
                .setMessage("Are sure you want to delete patient?")
                .setPositiveButton("Confirm") { a, d ->
//                    Toast.makeText(context, "Deleting...", Toast.LENGTH_SHORT).show()
                    println("deleting")
                    deletePatient(model, holder)
                }
                .setNegativeButton("Cancel") { a, d ->
                    a.dismiss()
                }
            builder.show()
        }
    }

    private fun deletePatient(model: PatientModel, holder: PatientHolder) {
        println("deleteing from db")
        val uid = model.uid
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(uid)
            .removeValue()
            .addOnSuccessListener {
                Toast.makeText(context, "Deleted...", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{ e ->
                Toast.makeText(context, "Deleting failed due to ${e.message}...", Toast.LENGTH_SHORT).show()
            }
    }

    override fun getItemCount(): Int {
        return patientArrayList.size
    }

    inner class PatientHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var patientName : TextView = binding.patientCardName
        var patientSurname : TextView = binding.patientCardSurname
        var patientRoom : TextView = binding.patientCardRoomInt
        var deleteBtn : ImageButton = binding.patientCardDelete
    }

}