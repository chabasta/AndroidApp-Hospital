package com.example.hospital.patient.data

import com.example.hospital.model.MedicinesDayCard

class DataSourceMedicineDays{

    companion object{
        fun createDataSet(): ArrayList<MedicinesDayCard> {
            val list = ArrayList<MedicinesDayCard>()
            list.add(
                MedicinesDayCard(
                    "1 day"
                )
            )
            list.add(
                MedicinesDayCard(
                    "2 day"
                )
            )
            list.add(
                MedicinesDayCard(
                    "3 day"
                )
            )
            list.add(
                MedicinesDayCard(
                    "4 day"
                )
            )
            list.add(
                MedicinesDayCard(
                    "5 day"
                )
            )
            list.add(
                MedicinesDayCard(
                    "6 day"
                )
            )
            list.add(
                MedicinesDayCard(
                    "7 day"
                )
            )
            list.add(
                MedicinesDayCard(
                    "8 day"
                )
            )
            list.add(
                MedicinesDayCard(
                    "9 day"
                )
            )
            list.add(
                MedicinesDayCard(
                    "10 day"
                )
            )
        return list

        }
    }
}
