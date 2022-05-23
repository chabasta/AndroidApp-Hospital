package com.example.hospital.model

data class NewReservationCard(
    var room: String,
    var date: String,
    var patient: String

){
    override fun toString(): String {
        return "NewReservationCard(room='$room', date='$date', patient='$patient')"
    }
}
