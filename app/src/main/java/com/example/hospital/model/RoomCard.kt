package com.example.hospital.model

data class RoomCard(

    var room: String,
    var floor: String,
    var state: String,
    var date: String,
    var name_of_patient: String

){
    override fun toString(): String {
        return "RoomCard(room='$room', floor='$floor', state='$state', date='$date', name_of_patient='$name_of_patient')"
    }
}