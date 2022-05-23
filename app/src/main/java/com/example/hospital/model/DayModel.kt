package com.example.hospital.model

class DayModel {

    var id: String = ""
        get() = field
        set(value) {
            field = value
        }
    var roomId: String = ""
        get() = field
        set(value) {
            field = value
        }
    var patientId: String = ""
        get() = field
        set(value) {
            field = value
        }
    var text: String = ""
        get() = field
        set(value) {
            field = value
        }

    constructor()

    constructor(id: String, roomId: String, text: String, patientId: String) {
        this.id = id
        this.roomId = roomId
        this.text = text
        this.patientId = patientId
    }


}