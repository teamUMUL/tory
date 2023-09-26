package inu.thebite.tory.database

import androidx.room.ColumnInfo
import java.time.LocalDate

sealed interface STOEvent{
    object SaveSTO: STOEvent
    data class setClassName(val className: String): STOEvent
    data class setChildName(val childName: String): STOEvent
    data class setSelectedDEV(val selectedDEV: String): STOEvent
    data class setSelectedLTO(val selectedLTO: String): STOEvent
    data class setSTOName(val stoName: String): STOEvent
    data class setSTODescription(val stoDescription: String): STOEvent
    data class setSTOTryNum(val stoTryNum: Int): STOEvent
    data class setSTOSuccessStandard(val stoSuccessStandard: String): STOEvent
    data class setSTOMethod(val stoMethod: String): STOEvent
    data class setSTOSchedule(val stoSchedule: String): STOEvent
    data class setSTOMemo(val stoMemo: String): STOEvent
    data class setSTOState(val stoState: String): STOEvent
    data class setGameResult(val gameResult: List<String>): STOEvent
    data class setDate(val date: List<LocalDate>): STOEvent
    data class setPlusRatio(val plusRatio: List<Float>): STOEvent
    data class setMinusRatio(val minusRatio: List<Float>): STOEvent

}