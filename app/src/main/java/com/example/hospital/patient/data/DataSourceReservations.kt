package com.example.hospital.patient.data

import com.example.hospital.model.ReservationCard

class DataSourceReservations {
    companion object {

        fun createDataSet(): ArrayList<ReservationCard> {
            val list = ArrayList<ReservationCard>()
            list.add(
                ReservationCard(
                    "#1" ,
                     "25",
                    "10$ per night",
                    "18.03 - 28.03",
                    "Dr. Sam Smith"
                )
            )
            list.add(
                ReservationCard(
                    "#2" ,
                    "25",
                    "10$ per night",
                    "18.03 - 28.03",
                    "Dr. Sam Smith"
                )
            )
            list.add(
                ReservationCard(
                    "#3" ,
                    "25",
                    "10$ per night",
                    "18.03 - 28.03",
                    "Dr. Sam Smith"
                )
            )
            list.add(
                ReservationCard(
                    "#4" ,
                    "25",
                    "10$ per night",
                    "18.03 - 28.03",
                    "Dr. Sam Smith"
                )
            )
            list.add(
                ReservationCard(
                    "#5" ,
                    "25",
                    "10$ per night",
                    "18.03 - 28.03",
                    "Dr. Sam Smith"
                )
            )
            list.add(
                ReservationCard(
                    "#6" ,
                    "25",
                    "10$ per night",
                    "18.03 - 28.03",
                    "Dr. Sam Smith"
                )
            )
            list.add(
                ReservationCard(
                    "#7" ,
                    "25",
                    "10$ per night",
                    "18.03 - 28.03",
                    "Dr. Sam Smith"
                )
            )
            list.add(
                ReservationCard(
                    "#8" ,
                    "25",
                    "10$ per night",
                    "18.03 - 28.03",
                    "Dr. Sam Smith"
                )
            )
            list.add(
                ReservationCard(
                    "#9" ,
                    "25",
                    "10$ per night",
                    "18.03 - 28.03",
                    "Dr. Sam Smith"
                )
            )

            return list
        }
    }
}
