package inu.thebite.tory.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ChildEntity::class], version = 1)
@TypeConverters(ListConverter::class)
abstract class ChildDatabase : RoomDatabase() {
    abstract fun childDao():ChildDao
}