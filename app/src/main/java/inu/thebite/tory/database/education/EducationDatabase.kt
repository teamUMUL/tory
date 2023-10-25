package inu.thebite.tory.database.education

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import inu.thebite.tory.database.TypeConverter.StringListConverter


@Database(entities = [EducationEntity::class], version = 2, exportSchema = false)
@TypeConverters(StringListConverter::class)
abstract class EducationDatabase : RoomDatabase() {
    abstract fun educationDao(): EducationDao
}