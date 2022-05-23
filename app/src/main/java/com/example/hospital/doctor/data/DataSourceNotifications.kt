package com.example.hospital.doctor.data

import com.example.hospital.model.NotificationCard

class DataSourceNotifications {

    companion object {

        fun createDataSet(): ArrayList<NotificationCard> {
            val list = ArrayList<NotificationCard>()
            list.add(
                NotificationCard(
                    "#13",
                    "15:47",
                    "I need to ask you some questions, can you please come to my room ?"
                )
            )
            list.add(
                NotificationCard(
                    "#13",
                    "16:47",
                    "I want to see you Mr.Smith"
                )
            )
            list.add(
                NotificationCard(
                    "#13",
                    "17:47",
                    "I have some burgers, do you want to eat?"
                )
            )
            return list
        }
    }
}