package com.example.hospital.model

data class MedicineCard(

    var title: String,

    var count: String,

    var checked: Boolean


){
    override fun toString(): String {
        return "MedicineCard(title='$title', count='$count', checked=$checked)"
    }
}
