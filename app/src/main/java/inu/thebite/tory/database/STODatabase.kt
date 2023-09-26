package inu.thebite.tory.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import inu.thebite.tory.database.TypeConverter.StringListConverter
import inu.thebite.tory.database.TypeConverter.DateListConverter
import inu.thebite.tory.database.TypeConverter.FloatListConverter

@Database(entities = [STOEntity::class], version = 1, exportSchema = false)
@TypeConverters(StringListConverter::class, DateListConverter::class, FloatListConverter::class)
abstract class STODatabase : RoomDatabase() {
    abstract fun stoDao(): STODao
}
