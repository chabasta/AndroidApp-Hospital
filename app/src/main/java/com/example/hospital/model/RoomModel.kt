package com.example.hospital.model

class RoomModel {

    var uid: String = ""
        get() = field
        set(value) { field = value }
    var floor : String = ""
        get() = field
        set(value) { field = value }
    var roomNumber: String = ""
        get() = field
        set(value) { field = value }
    var description: String = ""
        get() = field
        set(value) { field = value }
    var state : String = ""
        get() = field
        set(value) { field = value }
    var roomId : String = ""
        get() = field
        set(value) { field = value }
    var pacientId : String = ""
        get() = field
        set(value) { field = value }
    var fromDate: Long = 0
        get() = field
        set(value) { field = value }
    var toDate: Long = 0
        get() = field
        set(value) { field = value }
    var pacientName : String = ""
        get() = field
        set(value) { field = value }

    constructor()

    constructor(
        uid: String,
        floor: String,
        roomNumber: String,
        description: String,
        state: String,
        roomId: String,
        pacientId: String,
        fromDate: Long,
        toDate: Long,
        pacientName: String,
    ) {
        this.uid = uid
        this.floor = floor
        this.roomNumber = roomNumber
        this.description = description
        this.state = state
        this.roomId = roomId
        this.pacientId = pacientId
        this.fromDate = fromDate
        this.toDate = toDate
        this.pacientName = pacientName
    }


}