package inu.thebite.tory.database.ChildInfo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("ChildInfo")
data class ChildInfoEntity(
    @PrimaryKey(autoGenerate = true)
    var child_Id: Int = 0,
    @ColumnInfo("centerName")
    var centerName: String,
    @ColumnInfo("className")
    var className: String,
    @ColumnInfo("child_name")
    var childName: String,
    @ColumnInfo("child_birth")
    var child_birth: String,
    @ColumnInfo("child_etc")
    var child_etc: String,
    @ColumnInfo("parent_name")
    var parent_name: String,
    @ColumnInfo("child_startDate")
    var child_startDate: String,
    @ColumnInfo("child_endDate")
    var child_endDate: String,
    @ColumnInfo("child_registerDate")
    var child_registerDate: String

)

