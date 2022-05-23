package com.example.hospital.model

class PatientModel {

    var uid: String = ""
        get() = field
        set(value) {
            field = value
        }
    var name: String = ""
        get() = field
        set(value) {
            field = value
        }
    var surname: String = ""
        get() = field
        set(value) {
            field = value
        }
    var email: String = ""
        get() = field
        set(value) {
            field = value
        }
    var timestamp: Long = 0
        get() = field
        set(value) {
            field = value
        }
    var roomNumber: String = ""
        get() = field
        set(value) {
            field = value
        }
    var doctorId: String = ""
        get() = field
        set(value) {
            field = value
        }
    var role: String = ""
        get() = field
        set(value) {
            field = value
        }

    constructor()

    constructor(
        uid: String,
        name: String,
        surname: String,
        email: String,
        timestamp: Long,
        roomNumber: String,
        doctorId: String,
        role: String
    ) {
        this.uid = uid
        this.name = name
        this.surname = surname
        this.email = email
        this.timestamp = timestamp
        this.roomNumber = roomNumber
        this.doctorId = doctorId
        this.role = role
    }


}