package com.example.hospital.model

data class MedicinesDayCard(
    var day: String,
){
    override fun toString(): String {
        return "MedicinesDayCard(title='$day')"
    }
}
