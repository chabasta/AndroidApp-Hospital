package com.example.hospital.model

class PillModel {

    var id: String = ""
        get() = field
        set(value) {
            field = value
        }
    var dayId: String = ""
        get() = field
        set(value) {
            field = value
        }
    var medicineId: String = ""
        get() = field
        set(value) {
            field = value
        }

    var name: String = ""
        get() = field
        set(value) {
            field = value
        }

    var pillNumbers: String = ""
        get() = field
        set(value) {
            field = value
        }

    var completed: Boolean = false
        get() = field
        set(value) {
            field = value
        }

    constructor()
    constructor(id: String, dayId: String, medicineId: String, name: String, pillNumbers : String, completed : Boolean) {
        this.id = id
        this.dayId = dayId
        this.medicineId = medicineId
        this.name = name
        this.pillNumbers = pillNumbers
        this.completed = completed
    }
}