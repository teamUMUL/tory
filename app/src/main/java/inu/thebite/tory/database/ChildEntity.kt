package inu.thebite.tory.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.Date

@Entity("child")
data class ChildEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    @ColumnInfo("childName")
    val childName: String,
    @ColumnInfo("className")
    val className:String,
    @ColumnInfo("result")
    val result: List<String>,
    @ColumnInfo("teacherNote")
    val teacherNote:String,
//    @ColumnInfo("date")
//    val date: LocalDateTime? = LocalDateTime.now()

)
