package inu.thebite.tory.database.ImageCard

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Category")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("categoryId")
    var categoryId: Int = 0,
    @ColumnInfo("categoryName")
    var categoryName: String
)
