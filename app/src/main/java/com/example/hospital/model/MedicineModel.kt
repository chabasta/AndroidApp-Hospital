package com.example.hospital.model

class MedicineModel{
    var id: String = ""
        get() = field
        set(value) { field = value }
    var about: String = ""
        get() = field
        set(value) { field = value }
    var instruction: String = ""
        get() = field
        set(value) { field = value }
    var name: String = ""
        get() = field
        set(value) { field = value }

    constructor()

    constructor(id: String, about: String, instruction: String, name: String) {
        this.id = id
        this.about = about
        this.instruction = instruction
        this.name = name
    }
}
