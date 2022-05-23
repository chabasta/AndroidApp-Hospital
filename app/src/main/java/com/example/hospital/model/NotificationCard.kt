package com.example.hospital.model

data class NotificationCard(
    var room: String,
    var time: String,
    var message: String

){
    override fun toString(): String {
        return "NotificationCard(room='$room', time='$time', message='$message')"
    }
}
