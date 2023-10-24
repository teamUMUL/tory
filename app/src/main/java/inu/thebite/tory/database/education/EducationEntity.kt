package inu.thebite.tory.database.education

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("Education")
data class EducationEntity(
    @PrimaryKey(autoGenerate = true)
    var educationId: Int = 0,
    @ColumnInfo("stoId")
    var stoId: Long,
    @ColumnInfo("roundNum")
    var roundNum: Int,
    @ColumnInfo("educationResult")
    var educationResult: List<String>
)
