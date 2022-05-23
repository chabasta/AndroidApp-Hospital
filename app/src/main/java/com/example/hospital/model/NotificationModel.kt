package com.example.hospital.model

class NotificationModel {

    var id: String = ""
        get() = field
        set(value) {
            field = value
        }
    var text: String = ""
        get() = field
        set(value) {
            field = value
        }
    var roomNumber: String = ""
        get() = field
        set(value) {
            field = value
        }

    var date: Long = 0
        get() = field
        set(value) {
            field = value
        }

    constructor()

    constructor(id: String, text: String, roomNumber: String, date: Long) {
        this.id = id
        this.text = text
        this.roomNumber = roomNumber
        this.date = date
    }
}