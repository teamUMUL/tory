package inu.thebite.tory.database.STO

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity("STO")
data class STOEntity(
    @PrimaryKey(autoGenerate = true)
    var stoId: Int = 0,
    @ColumnInfo("className")
    var className: String,
    @ColumnInfo("childName")
    var childName: String,
    @ColumnInfo("selectedDEV")
    var selectedDEV: String,
    @ColumnInfo("selectedLTO")
    var selectedLTO: String,
    @ColumnInfo("stoName")
    var stoName: String,
    @ColumnInfo("stoDescription")
    var stoDescription: String,
    @ColumnInfo("stoTryNum")
    var stoTryNum: Int,
    @ColumnInfo("stoSuccessStandard")
    var stoSuccessStandard: String,
    @ColumnInfo("stoMethod")
    var stoMethod: String,
    @ColumnInfo("stoSchedule")
    var stoSchedule: String,
    @ColumnInfo("stoMemo")
    var stoMemo: String,
    @ColumnInfo("stoState")
    var stoState: Int,
    @ColumnInfo("gameResult")
    var gameResult: List<String>,
    @ColumnInfo("date")
    var date: List<Date>,
    @ColumnInfo("plusRatio")
    var plusRatio: List<Float>,
    @ColumnInfo("minusRatio")
    var minusRatio: List<Float>
)

