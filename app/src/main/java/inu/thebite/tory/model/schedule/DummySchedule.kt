package inu.thebite.tory.model.schedule

import inu.thebite.tory.model.sto.StoResponse

data class DummySchedule(
    val date : String,
    var stoList: List<StoResponse>
)
