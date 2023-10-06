package inu.thebite.tory.database.LTO

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LTOEntity::class], version = 1, exportSchema = false)
abstract class LTODatabase : RoomDatabase() {
    abstract fun ltoDao(): LTODao
}