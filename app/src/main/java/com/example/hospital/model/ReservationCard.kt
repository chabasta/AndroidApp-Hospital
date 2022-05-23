package com.example.hospital.model

data class ReservationCard(

    var room: String,
    var floor: String,
    var price: String,
    var date: String,
    var name_of_doctor: String

){
    override fun toString(): String {
        return "ReservationCard(room='$room', floor='$floor', price='$price', date='$date', name_of_doctor='$name_of_doctor')"
    }
}
