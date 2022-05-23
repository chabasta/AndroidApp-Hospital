package com.example.hospital.doctor.data

import com.example.hospital.model.NewReservationCard

class DataSrouceNewReservations {
    companion object {

        fun createDataSet(): ArrayList<NewReservationCard> {
            val list = ArrayList<NewReservationCard>()
            list.add(
                NewReservationCard(
                    "#13",
                    "19.03.2022",
                    "Mr Oleg"
                )
            )
            list.add(
                NewReservationCard(
                    "#14",
                    "19.03.2022",
                    "Mr Valera"
                )
            )
            list.add(
                NewReservationCard(
                    "#15",
                    "19.03.2022",
                    "Mr Tomas"
                )
            )
            list.add(
                NewReservationCard(
                    "#16",
                    "19.03.2022",
                    "Mr Timofei"
                )
            )
            list.add(
                NewReservationCard(
                    "#11",
                    "19.03.2022",
                    "Mr Kirill"
                )
            )
            return list
        }
    }
}