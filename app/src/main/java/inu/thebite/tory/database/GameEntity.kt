package inu.thebite.tory.database

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.util.Date

@Entity("games")
data class GameEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    @ColumnInfo("gameName")
    val note:String,
    @ColumnInfo("childName")
    val childName:String,
    @ColumnInfo("result_P")
    val result_P:Int,
    @ColumnInfo("result_N")
    val result_N:Int,
    @ColumnInfo("result_F")
    val result_F:Int,
    @ColumnInfo("teacherNote")
    val teacherNote:String,
//    @ColumnInfo("date")
//    val date: Date = Date.from(Instant.now())

)
